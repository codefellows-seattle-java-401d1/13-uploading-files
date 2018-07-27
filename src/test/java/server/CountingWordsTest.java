package server;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CountingWordsTest {

    public static int words;
    public static int sentences;
    public static int syllables;

    public static int countingWords(Path file) throws IOException {
        Scanner scanner = new Scanner(file);

        words = 0;
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

        sentences = 0;
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

        syllables = 0;
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
        int expected = 13;

        assertEquals(expected, actual);
    }

    @Test
    void countingSentencesSmallFile() throws IOException {
        Path file = Paths.get("/Users/amycohen/codefellows/401/lab-amy/13-uploading-files/upload-dir/textCheck");

        int actual = countingSentences(file);
        int expected = 1;

        assertEquals(expected, actual);
    }

    @Test
    void countingSyllablesSmallFile() throws IOException {
        Path file = Paths.get("/Users/amycohen/codefellows/401/lab-amy/13-uploading-files/upload-dir/textCheck");

        int actual = countingSyllables(file);
        int expected = 33;

        assertEquals(expected, actual);
    }

    @Test
    void sentenceParsingVerification() throws IOException {
        Path file = Paths.get("/Users/amycohen/codefellows/401/lab-amy/13-uploading-files/upload-dir/sentence_structure_test");

        int actual = countingSentences(file);
        int expected = 3;

        assertEquals(expected, actual);
    }

    @Test
    void countingWordsLargeFile() throws IOException {
        Path file = Paths.get("/Users/amycohen/codefellows/401/lab-amy/13-uploading-files/upload-dir/war-and-peace.txt");

        int actual = countingWords(file);
        int expected = 566_310;

        assertEquals(expected, actual);
    }

    @Test
    void countingSentencesLargeFile() throws IOException {
        Path file = Paths.get("/Users/amycohen/codefellows/401/lab-amy/13-uploading-files/upload-dir/war-and-peace.txt");

        int actual = countingSentences(file);
        int expected = 60_449;

        assertEquals(expected, actual);
    }

    @Test
    void countingSyllablesLargeFile() throws IOException {
        Path file = Paths.get("/Users/amycohen/codefellows/401/lab-amy/13-uploading-files/upload-dir/war-and-peace.txt");

        int actual = countingSyllables(file);
        int expected = 1_039_622;

        assertEquals(expected, actual);
    }

    @Test
    void readingLevelMiddleSchool() throws IOException {
        Path file = Paths.get("/Users/amycohen/codefellows/401/lab-amy/13-uploading-files/upload-dir/Aesops-Fables.txt");
        words = countingWords(file);
        sentences = countingSentences(file);
        syllables = countingSyllables(file);

        float wordsAndSentences = (float) (.39*((float)words/sentences));
        float syllablesAndWords = (float) (11.8*((float) syllables/words)-15.59);
        float readingLevelCalculations = wordsAndSentences + syllablesAndWords;

        //I wasn't sure how to get the reading level for this so I just ran it and pulled the number that came back.
        float actual = readingLevelCalculations;
        float expected = (float) 8.045696;

        assertEquals(expected, actual);
    }

    @Test
    void readingLevelElementary() throws IOException {
        Path file = Paths.get("/Users/amycohen/codefellows/401/lab-amy/13-uploading-files/upload-dir/bible.txt");
        words = countingWords(file);
        sentences = countingSentences(file);
        syllables = countingSyllables(file);

        float wordsAndSentences = (float) (.39*((float)words/sentences));
        float syllablesAndWords = (float) (11.8*((float) syllables/words)-15.59);
        float readingLevelCalculations = wordsAndSentences + syllablesAndWords;

        //I wasn't sure how to get the reading level for this so I just ran it and pulled the number that came back.
        float actual = readingLevelCalculations;
        float expected = (float)  5.0846415;

        assertEquals(expected, actual);
    }

    @Test
    void readingLevelUniversity() throws IOException {
        Path file = Paths.get("/Users/amycohen/codefellows/401/lab-amy/13-uploading-files/upload-dir/einstein.txt");
        words = countingWords(file);
        sentences = countingSentences(file);
        syllables = countingSyllables(file);

        float wordsAndSentences = (float) (.39*((float)words/sentences));
        float syllablesAndWords = (float) (11.8*((float) syllables/words)-15.59);
        float readingLevelCalculations = wordsAndSentences + syllablesAndWords;

        //I wasn't sure how to get the reading level for this so I just ran it and pulled the number that came back.
        float actual = readingLevelCalculations;
        float expected = (float)  13.344372;

        assertEquals(expected, actual);
    }


}