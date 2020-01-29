import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


public class app {

  static class Word {
    ArrayList<Letter> letters = new ArrayList<Letter>();
    boolean revealed = false;

    public Word (String newWord) {
      createWord(newWord);
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
      for (int i = 0; i < letters.size(); i++) {
        letters.get(i).guessLetter(letter);
      }
    }

    public void checkWord() {
      for (int i = 0; i < letters.size(); i++) {
        if (letters.get(i).revealed == false) {
          break;
        } else {
          revealed = true;
        }
      }
    }
  }

  static class Letter {
    char letter;
    boolean revealed = false;

    public Letter(char userInput) {
      letter = userInput;
    }

    public char printLetter() {
      if (revealed == true) {
        return letter;
      } else {
        return '_';
      }
    }

    public void guessLetter(char guess) {
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
    Word newWord = new Word(randomWord);
    System.out.println("Java CLI Hangman");
    System.out.print("Your Word: ");
    newWord.printWord();
  }
}