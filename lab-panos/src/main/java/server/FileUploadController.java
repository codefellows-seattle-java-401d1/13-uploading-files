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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        ArrayList<String> returnList = new ArrayList<>();
        List<Path> fileReturn = storageService.loadAll();
        for(Path p : fileReturn){
            returnList.add(p.getFileName().toString());
        }
        model.addAttribute("files", returnList);
        return "uploadForm";
    }

    @GetMapping("/files")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@RequestParam String filename) {
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
        try {
            storageService.store(file);
            InputStream inputSteam = file.getInputStream();
            Scanner scanner = new Scanner(inputSteam);

            int words = 0;
            int sentence = 0;
            int syllables = 0;
            Pattern p = Pattern.compile(".\\.|\\?|!$");
            while (scanner.hasNext()) {
                String token = scanner.next();
                if(token.length()>5){
                    syllables += Math.floor(token.length()/2);
                }else{
                    syllables += 1;
                }
                Matcher m = p.matcher(token);
                if(m.find()){
                    sentence++;
                }
                words++;
            }
            if(sentence==0){
                sentence=1;
            }
            double score = .39*(words/sentence)+11.8*(syllables/words)-15.59;
            model.addAttribute("words", words);
            model.addAttribute("score",score);
            return "word-count";
        } catch (IOException e) {

        }
        return "redirect:/";
    }
}
