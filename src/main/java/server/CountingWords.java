package server;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
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
            evaluateWord = ""+scanner.next();
            System.out.println("" + evaluateWord);
            System.out.println("" + evaluateWord.length());
            if (evaluateWord.length() <= 5){
            syllables++;
            } else {
                roundedDown = (int) Math.floor(evaluateWord.length()/2);
                syllables += roundedDown;
            }
        }
            return syllables;
    }


    /*
    Score	School level	Notes
100.00-90.00	5th grade	Very easy to read. Easily understood by an average 11-year-old student.
90.0–80.0	6th grade	Easy to read. Conversational English for consumers.
80.0–70.0	7th grade	Fairly easy to read.
70.0–60.0	8th & 9th grade	Plain English. Easily understood by 13- to 15-year-old students.
60.0–50.0	10th to 12th grade	Fairly difficult to read.
50.0–30.0	College	Difficult to read.
30.0–0.0	College graduate	Very difficult to read. Best understood by university graduates.
     */
}
