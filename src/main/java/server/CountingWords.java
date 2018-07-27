package server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class CountingWords {


    public static int countingWords(InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream);

        int words = 0;
        while (scanner.hasNext()) {
            scanner.next();
            words++;
        }
        return words;
    }

    public static int countingSentences(InputStream inputStream) throws IOException {
        String regex = "[A-Za-z]+\\W";
        String checkForSentence;
        Scanner scanner = new Scanner(inputStream);

        int sentences = 0;
        while (scanner.hasNext()) {
            checkForSentence = scanner.next();
            if (checkForSentence.matches(regex)){
            sentences++;
            }
        }
        return sentences;
    }

    public static int countingSyllables(InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream);
        String evaluateWord;

        int roundedDown = 0;
        int syllables = 0;
        while (scanner.hasNext()) {
            evaluateWord = ""+scanner.next();
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
