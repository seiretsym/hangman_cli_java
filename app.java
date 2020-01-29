import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class app {

  // create class constructor for storing words
  static class Word {
    // create an arraylist of letter classes
    ArrayList<Letter> letters = new ArrayList<Letter>();
    // used for storing guesses
    String guesses = "";
    // counter for remaining guesses
    int remainingGuesses = 10;
    // store the full word
    String fullWord;

    // method to run when creating a new Word class
    public Word (String newWord) {
      createWord(newWord);
      fullWord = newWord;
    }

    // method to print the word
    public void printWord() {
      String word = "";
      for (int i = 0; i < letters.size(); i++) {
        word += letters.get(i).printLetter() + " ";
      }
      System.out.println(word);
    }

    // method to populate the letters list with Letter classes
    public void createWord(String word) {
      for (int i = 0; i < word.length(); i++) {
        Letter temp = new Letter(word.charAt(i));
        letters.add(temp);
      }
    }

    // method for guessing the characters stored in letters list
    public void guessLetter(char letter) {
      // check the fullWord for the letter
      if (fullWord.indexOf(letter) == -1) {
        // and reduce the remainingGuesses counter by 1 if it's not found
        remainingGuesses -= 1;
      }
      // run the method guessLetter in each Letter stored in letters
      for (int i = 0; i < letters.size(); i++) {
        letters.get(i).guessLetter(letter);
      }
    }

    // method for checking if the word has been completely guessed
    public boolean checkWord() {
      for (int i = 0; i < letters.size(); i++) {
        if (letters.get(i).revealed == false) {
          // return false if any letters aren't revealed
          return false;
        }
      }
      // return true if all letters are revealed
      return true;
    }
  }

  // class constructor for individual letters in a word
  static class Letter {
    // used for storing the character
    char letter;
    // boolean for determining whether this letter has been revealed
    boolean revealed = false;

    // when a new Letter is declared...
    public Letter(char userInput) {
      // set the letter
      letter = userInput;
      // if the character is a space, ', or -, set revealed to true because they don't count but are valid in the word
      if (letter == ' ' || letter == '\'' || letter == '-') {
        revealed = true;
      }
    }

    // method used to return the letter or _ depending on whether the letter has been revealed/guessed
    public char printLetter() {
      if (revealed == true) {
        return letter;
      } else {
        return '_';
      }
    }

    // method used to return true or false based on whether this letter has been revealed
    public boolean guessLetter(char guess) {
      if (guess == letter && revealed == false) {
        revealed = true;
        return true;
      }
      return false;
    }
  }

  public static void main(String[] args) {
    // initialize the game
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

  // method used to initialize the game
  static void init() {
    // first, generate a list of words by reading from words.txt
    ArrayList<String> words = readTxtFile("words.txt");
    // then randomly pick a word from the list
    String randomWord = getRandomWord(words);
    // generate a new Word class with the randomWord
    Word newWord = new Word(randomWord);
    System.out.println("Java CLI Hangman");
    // runs the printWord method to begin the game
    printWord(newWord);
  }

  // method to print the current state of the game
  static void printWord(Word word) {
    // check if user has any guesses remaining
    if (word.remainingGuesses > 0) {
      // if user has remaining guesses to use...
      // print the remaining guesses
      System.out.println("\nRemaining Guesses: " + word.remainingGuesses);
      // print the word with revealed/unrevealed characters
      System.out.print("\nYour Word: ");
      word.printWord();
      // run the guess method to allow user to guess
      guess(word);
    } else {
      // no more guesses? game over!
      gameOver(word, false);
    }
  }

  // method to check the word class
  static void checkWord(Word word) {
    // word.checkWord() returns true if all letters are revealed
    if (word.checkWord()) {
      // end the game, positively
      gameOver(word, true);
    } else {
      // run printWord to continue the game
      printWord(word);
    }
  }

  // method to determine game loss/win
  static void gameOver(Word word, boolean win) {
    // if win is true, then end the game positively
    if (win) {
      System.out.println("\nYou guessed the word: " + word.fullWord);
    } else {
      // otherwise, end the game negatively
      System.out.println("\nYou ran out of guesses!");
      System.out.println("The word was: " + word.fullWord);
    }
    // run the reInit method for looping
    reInit();
  }

  // method that prompts the user to continue playing or quit
  static void reInit() {
    // read userInput via Scanner
    Scanner userInput = new Scanner(System.in);
    // ask the user to play again
    System.out.print("\nWould you like to play again? [y/n] ");
    // convert userInput to lowercase and store in answer
    String answer = userInput.nextLine().toLowerCase();

    // compare answer to the below conditions
    if (answer.charAt(0) == 'y') {
      // answer yes, so play again by running init
      init();
    } else if (answer.charAt(0) == 'n') { // answer no, so end the game
      System.out.println("Thank you for playing!");
    } else { // any other options are invalid. let the user know.
      System.out.println("Invalid choice!");
      // loop this method
      reInit();
    }
  }

  // method used to prompt user to guess a letter and handle user's guess
  static void guess(Word word) {
    Scanner userInput = new Scanner(System.in);
    // let the user know which letters have already been used
    System.out.print("Letters Used: ");
    printGuesses(word);

    // prompt user for input
    System.out.print("Your Guess: ");
    // convert user input to lowercase
    String guess = userInput.nextLine().toLowerCase();

    // validate guess because only a single character input is valid
    if (guess.length() != 1) {
      // if user input is more than 1 character, let the user know
      System.out.println("Please input a single character.");
      // and loop this method
      printWord(word);
    } else {
      // convert guess to a char
      char letter = guess.charAt(0);
      // then validate letter: if letter is not in the alphabet...
      if (!guess.matches("[a-z]")) {
        // let the user know and loop this method
        System.out.println("Please guess a letter in the alphabet!");
        printWord(word);
      } else if (word.guesses.indexOf(letter) != -1) { // if the letter has already been guessed
        // let the user know and loop this method
        System.out.println("You've already guessed that letter!");
        printWord(word);
      } else { // everything checks out, so...
        // add the letter to guesses property in word
        word.guesses += letter;
        // run the guessLetter method in word
        word.guessLetter(letter);
        // run the checkWord method to determine current state of game
        checkWord(word);
      }
    }
  }

  // method to print all the characters the user has guessed
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