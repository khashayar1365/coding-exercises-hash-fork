import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Dictionary {
    private final Set<String> dictionary;
    private final Distance distance;

    // Constructor with default distance function and filter
    Dictionary(String dictionaryPath) throws IOException {
        this.dictionary = loadDictionary(dictionaryPath);
        this.distance = new Distance(this.dictionary);
    }

    // Constructor with customizable distance function and max distance filter
    Dictionary(String dictionaryPath,
               Function<String, Function<String, Integer>> distance,
               int maxDistance) throws IOException {
        this.dictionary = loadDictionary(dictionaryPath);
        this.distance = new Distance(distance, this.dictionary, maxDistance);
    }

    // Load dictionary from file, converting each word to lowercase
    private Set<String> loadDictionary(String dictionaryPath) throws IOException {
        Set<String> dictionary = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Trim whitespace and convert to lowercase before adding to the dictionary set
                dictionary.add(line.trim().toLowerCase());
            }
        }
        return dictionary;
    }

    // Check if the word is in the dictionary, case-insensitive
    boolean contains(String word) {
        // Convert to lowercase before checking
        return dictionary.contains(word.toLowerCase());
    }

    // Get a list of suggested words based on the provided distance function
    List<String> getSuggestions(String word) {
        return distance.filter(word);
    }
}
