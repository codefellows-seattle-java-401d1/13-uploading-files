package server;

import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class CountingWords {

    public static int countingWords (@RequestParam("file") MultipartFile file, Model model) throws IOException {
        InputStream inputSteam = file.getInputStream();
        Scanner scanner = new Scanner(inputSteam);

        int words = 0;
        while (scanner.hasNext()) {
            scanner.next();
            words++;
        }
        model.addAttribute("words", words);
        return words;
    }

    public static int countingSentences (@RequestParam("file") MultipartFile file, Model model) throws IOException {
        String regex = "(?!\\s+)\\W";
        InputStream inputSteam = file.getInputStream();
        Scanner scanner = new Scanner(inputSteam);

        int sentences = 0;
        while (scanner.hasNext()) {
            if (scanner.next().matches(regex)){
            sentences++;
            }
        }
        model.addAttribute("sentences", sentences);
        return sentences;
    }

    public static int countingSyllables(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        InputStream inputSteam = file.getInputStream();
        Scanner scanner = new Scanner(inputSteam);
        String evaluateWord = scanner.next();

        int roundedDown = 0;
        int syllables = 0;
        while (scanner.hasNext()) {
            if (evaluateWord.length() <= 5){
            syllables++;
            } else {
                roundedDown = (int) Math.floor(evaluateWord.length()/2);
                syllables += roundedDown;
            }
        }
            model.addAttribute("syllables", syllables);
            return syllables;
    }
}
