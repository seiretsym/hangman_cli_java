import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


public class app {

  static class Word {
    ArrayList<Letter> letters = new ArrayList<Letter>();
    String guesses = "";
    int remainingGuesses = 10;
    String fullWord;

    public Word (String newWord) {
      createWord(newWord);
      fullWord = newWord;
    }

    public void printWord() {
      String word = "";
      for (int i = 0; i < letters.size(); i++) {
        word += letters.get(i).printLetter() + " ";
      }
      System.out.println(word);
    }

    public void createWord(String word) {
      for (int i = 0; i < word.length(); i++) {
        Letter temp = new Letter(word.charAt(i));
        letters.add(temp);
      }
    }

    public void guessLetter(char letter) {
      if (fullWord.indexOf(letter) == -1) {
        remainingGuesses -= 1;
      }
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

    public Letter(char userInput) {
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

    public boolean guessLetter(char guess) {
      if (guess == letter && revealed == false) {
        revealed = true;
        return true;
      }
      return false;
    }
  }

  public static void main(String[] args) {
    init();
  }

  // method to read words from a txt file and return an arraylist
  static ArrayList<String> readTxtFile(String filename) {
    ArrayList<String> allWords = new ArrayList<String>();

    try {
      File txtFile = new File(filename);
      Scanner words = new Scanner(txtFile);
      while (words.hasNextLine()) {
        allWords.add(words.nextLine());
      }
      words.close();
    } catch (FileNotFoundException error) {
      System.out.println("Error: ");
      error.printStackTrace();
    }

    return allWords;
  }

  // method to get a random word from an ArrayList
  static String getRandomWord(ArrayList<String> words) {
    int rng = (int) (Math.random() * words.size());
    return words.get(rng).toLowerCase();
  }

  static void init() {
    ArrayList<String> words = readTxtFile("words.txt");
    String randomWord = getRandomWord(words);
    Word newWord = new Word(randomWord);
    System.out.println("Java CLI Hangman");

    printWord(newWord);
  }

  static void printWord(Word word) {
    if (word.remainingGuesses > 0) {
      System.out.println("\nRemaining Guesses: " + word.remainingGuesses);
      System.out.print("\nYour Word: ");
      word.printWord();
      guess(word);
    } else {
      gameOver(word, false);
    }
  }

  static void checkWord(Word word) {
    if (word.checkWord()) {
      gameOver(word, true);
    } else {
      printWord(word);
    }
  }

  static void gameOver(Word word, boolean win) {
    if (win) {
      System.out.println("\nYou guessed the word: " + word.fullWord);
    } else {
      System.out.println("\nYou ran out of guesses!");
      System.out.println("The word was: " + word.fullWord);
    }
    reInit();
  }

  static void reInit() {
    Scanner userInput = new Scanner(System.in);
    System.out.print("\nWould you like to play again? [y/n] ");
    String answer = userInput.nextLine().toLowerCase();
    if (answer.charAt(0) == 'y') {
      init();
    } else if (answer.charAt(0) == 'n') {
      System.out.println("Thank you for playing!");
    } else {
      System.out.println("Invalid choice!");
      reInit();
    }
  }

  static void guess(Word word) {
    Scanner userInput = new Scanner(System.in);
    System.out.print("Letters Used: ");
    printGuesses(word);
    System.out.print("Your Guess: ");
    String guess = userInput.nextLine().toLowerCase();
    if (guess.length() != 1) {
      System.out.println("Please input a single character.");
      printWord(word);
    } else {
      char letter = guess.charAt(0);
      if (!guess.matches("[a-z]")) {
        System.out.println("Please guess a letter in the alphabet!");
        printWord(word);
      } else if (word.guesses.indexOf(letter) != -1) {
        System.out.println("You've already guessed that letter!");
        printWord(word);
      } else {
        word.guesses += letter;
        word.guessLetter(letter);
        checkWord(word);
      }
    }
  }

  static void printGuesses(Word word) {
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