import java.util.Set;
import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Distance {

    private final Function<String, Function<String, Integer>> distance;
    private final Set<String> dictionary;
    private final int maxDistance;

    // Constructor with customizable distance function and maximum distance
    Distance(Function<String, Function<String, Integer>> distance, Set<String> dictionary, int maxDistance) {
        this.distance = distance;
        this.dictionary = dictionary;
        this.maxDistance = maxDistance;
    }

    // Default constructor using Levenshtein distance with a default max distance
    Distance(Set<String> dictionary) {
        // Use Levenshtein distance by default
        this.distance = (a) -> ((b) -> levenshteinDistance(a, b));
        this.dictionary = dictionary;
        // Default max distance to 5
        this.maxDistance = 5;
    }

    // Calculate the distance between two strings
    Integer distance(String a, String b) {
        return this.distance.apply(a).apply(b);
    }

    // Get a list of suggestions by escalating distance if no suggestions are found within the initial threshold
    List<String> filter(String word) {
        List<String> suggestions = new ArrayList<>();

        for (int currentDistance = 1; currentDistance <= maxDistance; currentDistance++) {
            int finalCurrentDistance = currentDistance;
            suggestions = dictionary.stream()
                    .filter(dictWord -> distance(word, dictWord) <= finalCurrentDistance)
                    .collect(Collectors.toList());

            if (!suggestions.isEmpty()) {
                break;
            }
        }
        // We could reduce the list size to include only a limited number of suggestions, such as 5.
        return suggestions;
    }

    // Calculate the Levenshtein distance between two strings
    int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        // Initialize the base cases
        for (int j = 0; j <= b.length(); j++) {
            dp[0][j] = j; // Cost of inserting characters into 'a'
        }
        for (int i = 1; i <= a.length(); i++) {
            dp[i][0] = i; // Cost of removing characters from 'b'
        }

        // Fill in the rest of the table
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1; // Cost of substitution
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1,   // Cost of deletion
                                dp[i][j - 1] + 1),   // Cost of insertion
                        dp[i - 1][j - 1] + cost      // Cost of substitution
                );
            }
        }

        // Return the Levenshtein distance between 'a' and 'b'
        return dp[a.length()][b.length()];
    }
}
