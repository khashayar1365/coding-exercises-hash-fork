import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("SpellChecker needs exactly two arguments for the files <dictionary.txt> <file-to-check.txt>");
            return;
        }

        // Path to dictionary file
        String dictionaryPath = args[0];
        // Path to file to check
        String fileToCheckPath = args[1];

        // Create a SpellChecker instance
        SpellChecker spellChecker;
        try {
            spellChecker = new SpellChecker(dictionaryPath);
        } catch (IOException e) {
            System.out.println("Error initializing SpellChecker: " + e.getMessage());
            return;
        }

        // Check spelling
        try {
            spellChecker.checkSpelling(fileToCheckPath);
        } catch (IOException e) {
            System.out.println("Error while checking spelling: " + e.getMessage());
        }
    }
}
