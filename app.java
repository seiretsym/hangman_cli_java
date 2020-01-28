import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class app {
  public static void main(String[] args) {
    ArrayList<String> words = readTxtFile("words.txt");
    System.out.println(words);
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
}