import java.util.Scanner;

public class DataRunner {
  public static void main(String[] args) {
    UserStory jerry = new UserStory("modelNames.txt", 
                                    "releaseYear.txt", 
                                    "price.txt", 
                                    "maxRes.txt", 
                                    "weightInGrams.txt", 
                                    "normalFocusRange.txt",
                                    "zoom.txt");


    jerry.promptSearch();
  }
}