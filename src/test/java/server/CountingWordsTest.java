package server;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CountingWordsTest {

    public static int countingWords(Path file) throws IOException {
        Scanner scanner = new Scanner(file);

        int words = 0;
        while (scanner.hasNext()) {
            scanner.next();
            words++;
        }
        return words;
    }

    public static int countingSentences(Path file) throws IOException {
        String regex = "[A-Za-z]+\\W";
        String checkForSentence;
        Scanner scanner = new Scanner(file);

        int sentences = 0;
        while (scanner.hasNext()) {
            checkForSentence = scanner.next();
            if (checkForSentence.matches(regex)){
                sentences++;
            }
        }
        return sentences;
    }

    public static int countingSyllables(Path file) throws IOException {
        Scanner scanner = new Scanner(file);
        String evaluateWord;

        int roundedDown = 0;
        int syllables = 0;
        while (scanner.hasNext()) {
            evaluateWord = "" + scanner.next();
            if (evaluateWord.length() <= 5) {
                syllables++;
            } else {
                roundedDown = (int) Math.floor(evaluateWord.length() / 2);
                syllables += roundedDown;
            }
        }
        return syllables;
    }

    @Test
    void countingWordsSmallFile() throws IOException {
        Path file = Paths.get("/Users/amycohen/codefellows/401/lab-amy/13-uploading-files/upload-dir/textCheck");

        int actual = countingWords(file);
        int exptected = 13;

        assertEquals(exptected, actual);
    }

    @Test
    void countingSentencesSmallFile() throws IOException {
        Path file = Paths.get("/Users/amycohen/codefellows/401/lab-amy/13-uploading-files/upload-dir/textCheck");

        int actual = countingSentences(file);
        int exptected = 1;

        assertEquals(exptected, actual);
    }

    @Test
    void countingSyllablesSmallFile() throws IOException {
        Path file = Paths.get("/Users/amycohen/codefellows/401/lab-amy/13-uploading-files/upload-dir/textCheck");

        int actual = countingSyllables(file);
        int exptected = 33;

        assertEquals(exptected, actual);
    }


}