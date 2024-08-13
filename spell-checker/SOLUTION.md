# Spell Checker Explanation

This document explains the solution provided for the spell checker.

## How to Run the Program

Compile and run the `Main.java` file with two arguments: the path to the dictionary and the path to the file to check. Use the following commands:

```text
javac spell-checker/Main.java
java spell-checker/Main <path-to-dictionary> <path-to-file-to-check>
```

## Requirements Covered by This Solution

- TThe program outputs a list of incorrectly spelled words.
  - It prints the misspelled word using:
  - ```text
    System.out.println("Misspelled word: " + word);
    ```
- For each misspelled word, the program outputs a list of suggested words.
  - It prints the suggested words using:
  - ```text
    System.out.println("Suggestions: " + dictionary.getSuggestions(cleanedWord));
    ``` 
- The program includes the line and column number of the misspelled word.
  - It prints the line and column number using:
  - ```text
    System.out.println("Line: " + lineNumber + ", Column: " + columnNumber);
    ``` 
- The program prints the misspelled word along with some surrounding context.
  - It prints the misspelled word and surrounding context using:
  - ```text
    System.out.println("Context: " + getContext(line, word));
    ``` 
- The program handles proper nouns (e.g., person or place names) to some extent.
    
  - A complete solution for identifying nouns is not provided. 
    This solution assumes that nouns or the first word of a sentence start with a capital letter.
  - This approach may fail if the first word of a sentence is a noun not in the dictionary.
  - Depending on specific needs, a more tailored dictionary of common nouns could improve results.

## Ways to Improve the Solution

- To enhance spell checking and handle nouns better, consider using tokenizer libraries 
    and enriching the dictionary with attributes (e.g., noun/verb tags). 
    Tokenizers can help determine the probability of a word being a verb or noun,
    allowing for more accurate suggestions.

## Other Similar Problems

- Autocomplete functionality shares similarities with spell checking 
    but often requires different approaches. For example, an autocomplete function 
    might look for words in the dictionary with similar or identical prefixes. 
    It might relax distance constraints to include longer words in the suggestion list.


