import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


public class app {

  static class Letter {
    char letter;
    boolean revealed;

    public Letter(char userInput) {
      letter = userInput;
    }

    public void printLetter() {
      if (revealed == true) {
        System.out.print(letter);
      } else {
        System.out.print("_");
      }
    }

    public void guessLetter(char guess) {
      if (guess == letter) {
        revealed = true;
      }
    }
  }

  public static void main(final String[] args) {
    final ArrayList<String> words = readTxtFile("words.txt");
    System.out.println(words);
    final String randomWord = getRandomWord(words);
    System.out.println(randomWord);
    for (int i = 0; i < randomWord.length(); i++) {
      Letter temp = new Letter(randomWord.charAt(i));
      temp.printLetter();
    }
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

  // public class Word {
  //   char letter;
  //   boolean revealed;
  
  //   public Word(char userInput) {
  //     letter = userInput;
  //   }
  
  //   public void checkLetter (char guess) {
  //     if (guess == letter) {
  //       revealed = true;
  //     }
  //   }
  
  //   public void printLetter() {
  //     if (revealed == true) {
  //       System.out.print(letter);
  //     } else {
  //       System.out.print("_");
  //     }
  //   }
  // }
}