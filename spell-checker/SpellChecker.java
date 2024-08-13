import java.io.*;
import java.util.Arrays;
import java.util.function.Function;

public class SpellChecker {

    private final Dictionary dictionary;
    // Make context radius customizable
    private int contextLength = 10;

    // Using default distance function and filter for the dictionary
    public SpellChecker(String dictionaryPath) throws IOException {
        dictionary = new Dictionary(dictionaryPath);
    }

    // Adding ability to customize distance function and filter function
    public SpellChecker(String dictionaryPath,
                        Function<String, Function<String, Integer>> distance,
                        int macDistance) throws IOException {
        dictionary = new Dictionary(dictionaryPath, distance, macDistance);
    }

    public void setContextLength(int contextLength) {
        this.contextLength = contextLength;
    }

    public void checkSpelling(String fileToCheckPath) throws IOException {
        // Read line by line and check spelling on each line by calling checkLine method
        boolean isItStartOfTheSentence = true;
        try (BufferedReader br = new BufferedReader(new FileReader(fileToCheckPath))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                isItStartOfTheSentence = checkLine(line, lineNumber, isItStartOfTheSentence);
            }
        }
    }

    private boolean checkLine(String line, int lineNumber, boolean isItStartOfTheSentence) {
        /*
        Split by spaces and periods.
        We need to treat periods as separate words to identify sentence boundaries.
        This approach may cause issues with words containing periods,
        such as "P.H.D.".
        However, this is important for distinguishing between the first word of
        a sentence and nouns, as handling nouns was explicitly mentioned
        in the requirements.
        */
        String[] words = Arrays.stream(line.split("[\\s.]+"))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
        int columnNumber = 0;

        for (String word : words) {
            columnNumber++;
            // Remove non-alphabetic characters and apostrophes
            String cleanedWord = word.replaceAll("[^a-zA-Z']", "");

            if (!cleanedWord.isEmpty() && !dictionary.contains(cleanedWord)
                    /*
                    Handle nouns that start with a capital letter
                    Note: This check is based on the assumption that
                    nouns and the first word of a sentence
                    always start with a capital letter.
                    This approach may not handle cases where the first word
                    of a sentence is a noun, which may not be caught by this check.
                     */
                    && (Character.isLowerCase(cleanedWord.charAt(0)) || isItStartOfTheSentence)
            ) {
                System.out.println("Misspelled word: " + word);
                System.out.println("Line: " + lineNumber + ", Column: " + columnNumber);
                System.out.println("Context: " + getContext(line, word));
                System.out.println("Suggestions: " + dictionary.getSuggestions(cleanedWord));
                System.out.println();
            }
            // Set true if the word is a period, indicating end of a sentence
            // Then the next word will indicate the start of a new sentence.
            isItStartOfTheSentence = word.equals(".");
        }
        return isItStartOfTheSentence;
    }

    private String getContext(String line, String misspelledWord) {
        // Choose some characters before misspelledWord if there is any
        int startIndex = Math.max(0, line.indexOf(misspelledWord) - this.contextLength);
        // Choose some characters after misspelledWord if there is any
        int endIndex = Math.min(line.length(), line.indexOf(misspelledWord) + misspelledWord.length() + this.contextLength);
        return line.substring(startIndex, endIndex);
    }
}
