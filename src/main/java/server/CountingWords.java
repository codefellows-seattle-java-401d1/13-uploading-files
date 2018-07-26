package server;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class CountingWords {

    public static int countingWords(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputSteam = file.getInputStream();
        Scanner scanner = new Scanner(inputSteam);

        int words = 0;
        while (scanner.hasNext()) {
            scanner.next();
            words++;
        }
        return words;
    }

    public static int countingSentences(@RequestParam("file") MultipartFile file) throws IOException {
//        String regex = "(?!\\s+)\\W";
        String regex = "[A-Za-z]+\\W";
        String checkForSentence;
        InputStream inputSteam = file.getInputStream();
        Scanner scanner = new Scanner(inputSteam);

        int sentences = 0;
        while (scanner.hasNext()) {
            checkForSentence = scanner.next();
            if (checkForSentence.matches(regex)){
            sentences++;
            }
        }
        return sentences;
    }

    public static int countingSyllables(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputSteam = file.getInputStream();
        Scanner scanner = new Scanner(inputSteam);
        String evaluateWord;

        int roundedDown = 0;
        int syllables = 0;
        while (scanner.hasNext()) {
            evaluateWord = scanner.next();
            if (evaluateWord.length() <= 5){
            syllables++;
            } else {
                roundedDown = (int) Math.floor(evaluateWord.length()/2);
                syllables += roundedDown;
            }
        }
            return syllables;
    }
}
