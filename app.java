import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


public class app {

  static class Word {
    ArrayList<Letter> letters = new ArrayList<Letter>();
    String guesses = "";

    public Word (final String newWord) {
      createWord(newWord);
    }

    public void printWord() {
      String word = "";
      for (int i = 0; i < letters.size(); i++) {
        word += letters.get(i).printLetter() + " ";
      }
      System.out.println(word);
    }

    public void createWord(final String word) {
      for (int i = 0; i < word.length(); i++) {
        final Letter temp = new Letter(word.charAt(i));
        letters.add(temp);
      }
    }

    public void guessLetter(final char letter) {
      for (int i = 0; i < letters.size(); i++) {
        letters.get(i).guessLetter(letter);
      }
    }

    public boolean checkWord() {
      for (int i = 0; i < letters.size(); i++) {
        if (letters.get(i).revealed == false) {
          return false;
        }
      }
      return true;
    }
  }

  static class Letter {
    char letter;
    boolean revealed = false;

    public Letter(final char userInput) {
      letter = userInput;
      if (letter == ' ' || letter == '\'' || letter == '-') {
        revealed = true;
      }
    }

    public char printLetter() {
      if (revealed == true) {
        return letter;
      } else {
        return '_';
      }
    }

    public void guessLetter(final char guess) {
      if (guess == letter && revealed == false) {
        revealed = true;
      }
    }
  }

  public static void main(final String[] args) {
    init();
  }

  // method to read words from a txt file and return an arraylist
  static ArrayList<String> readTxtFile(final String filename) {
    final ArrayList<String> allWords = new ArrayList<String>();

    try {
      final File txtFile = new File(filename);
      final Scanner words = new Scanner(txtFile);
      while (words.hasNextLine()) {
        allWords.add(words.nextLine());
      }
      words.close();
    } catch (final FileNotFoundException error) {
      System.out.println("Error: ");
      error.printStackTrace();
    }

    return allWords;
  }

  // method to get a random word from an ArrayList
  static String getRandomWord(final ArrayList<String> words) {
    final int rng = (int) (Math.random() * words.size());
    return words.get(rng).toLowerCase();
  }

  static void init() {
    final ArrayList<String> words = readTxtFile("words.txt");
    final String randomWord = getRandomWord(words);
    final Word newWord = new Word(randomWord);
    System.out.println("Java CLI Hangman");

    printWord(newWord);
  }

  static void printWord(final Word word) {
    System.out.print("\nYour Word: ");
    word.printWord();
    guess(word);
  }

  static void checkWord(final Word word) {
    if (word.checkWord()) {
      gameOver(word, true);
    } else {
      printWord(word);
    }
  }

  static void gameOver(final Word word, final boolean win) {
    if (win) {
      System.out.print("You guessed the word: ");
      word.printWord();
    }
  }

  static void guess(final Word word) {
    final Scanner userInput = new Scanner(System.in);
    System.out.print("Letters Used: ");
    printGuesses(word);
    System.out.print("Your Guess: ");
    String guess = userInput.nextLine().toLowerCase();
    if (guess.length() > 1) {
      System.out.println("Please input a single character.");
      printWord(word);
    } else {
      final char letter = guess.charAt(0);
      if (word.guesses.indexOf(letter) != -1) {
        System.out.println("You've already guessed that letter!");
        printWord(word);
      } else {
        word.guesses += letter;
        word.guessLetter(letter);
        checkWord(word);
      }
    }
  }

  static void printGuesses(final Word word) {
    String guesses = "";
    for (int i = 0; i < word.guesses.length(); i++) {
      if (i != word.guesses.length() - 1) {
        guesses += word.guesses.charAt(i) + ", ";
      } else {
        guesses += word.guesses.charAt(i);
      }
    }
    System.out.println(guesses);
  }
}