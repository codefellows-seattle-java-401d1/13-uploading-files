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

    //lecture video shows "/upload"
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
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            Model model) {
        //lecture video has RedirectAttributes redirectAttributes as a parameter instead of Model model

        try {
            storageService.store(file);
            //lecture video has redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!")
            CountingWords.countingWords(file, model);
            CountingWords.countingSentences(file, model);
            CountingWords.countingSyllables(file, model);

            /*
            ===== commenting this out to see if I can move it to another function ====
            InputStream inputSteam = file.getInputStream();
            Scanner scanner = new Scanner(inputSteam);

            int words = 0;
            while (scanner.hasNext()) {
                scanner.next();
                words++;
            }
            model.addAttribute("words", words);
            return "word-count";
            */

        } catch (IOException e) {

        }
        return "redirect:/";
        //lecture video shows "redirect:/upload"
    }
}
