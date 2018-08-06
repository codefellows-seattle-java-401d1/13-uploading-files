package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import server.storage.FileSystemStorageService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    private final FileSystemStorageService storageService;

    @Autowired
    public FileUploadController(FileSystemStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Resource file = storageService.loadAsResource(filename);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   Model model) {
        try {
            storageService.store(file);
            InputStream inputSteam = file.getInputStream();
            Scanner scanner = new Scanner(inputSteam);
            System.out.println("This is where we get to on line 58!");
            int words = 0;
            int letters = 0;
            int sentences = 0;
            while (scanner.hasNext()) {
               String currentWord = scanner.next();
               for (int i = 0; i < currentWord.length(); i++) {
                   letters++;
               }

               if (currentWord.endsWith(".") || currentWord.endsWith("!") || currentWord.endsWith("?")) {
                   sentences++;
               }
               words++;
            }

            double syllables = letters / 3;
            double fleschNum = .39 * (words / sentences) + 11.8 * (syllables / words) - 15.59;
            System.out.println("*******************");
            System.out.println("*******************");
            System.out.println("This is where we do our calculations.");
            System.out.println(fleschNum);
            System.out.println("*******************");
            System.out.println("*******************");

            // Get the reading level based on flesch scale
            String readingLevel = "";

            if (fleschNum > 12) {
                readingLevel = "Very Difficult; college";
            } else if (fleschNum >= 10 && fleschNum <= 12) {
                readingLevel = "Difficult; High school grad / some college";
            } else if (fleschNum >= 8 && fleschNum <= 10) {
                readingLevel = "Fairly difficult; some High School";
            } else if (fleschNum >= 7 && fleschNum <= 8) {
                readingLevel = "Standard; 7th or 8th Grade";
            } else if (fleschNum >= 6 && fleschNum <= 7) {
                readingLevel = "Fairly easy; 6th Grade";
            } else if (fleschNum >= 5 && fleschNum <= 6) {
                readingLevel = "Easy; 5th Grade";
            } else {
                readingLevel = "Very easy; 4th Grade";
            }


            model.addAttribute("words", words);
            model.addAttribute("letters", letters);
            model.addAttribute("sentences", sentences);
            model.addAttribute("syllables", syllables);
            model.addAttribute("flesch", fleschNum);
            model.addAttribute("readingLevel", readingLevel);

            return "word-count";
        } catch (IOException e) {

        }
        return "redirect:/";
    }
}