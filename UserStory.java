
import java.util.Scanner;

public class UserStory {
  // Stores all camera objects created from the data files.
  private Cameras[] allCameras;

  // Stores the names of all camera companies.
  private String[] allCompanies;

  // Stores the calculated ranking score for each camera.
  private double[] rankings;


  // Initializes the camera data, company names, and camera rankings.
  public UserStory(String modelNamesFileName, String releaseYearFileName,
                   String priceFileName, String maxResFileName,
                   String weightInGramsFileName, String normalFocusRangeFileName,
                   String zoomFileName) {

    // Creates an array of Cameras objects using information from the files.
    allCameras = createCameras(modelNamesFileName, releaseYearFileName,
                               priceFileName, maxResFileName,
                               weightInGramsFileName, normalFocusRangeFileName,
                               zoomFileName);


    // Creates an array containing the names of all camera companies.
    allCompanies = new String[] {
      "Agfa", "Canon", "Casio", "Contax", "Epson", "Fujifilm",
      "HP", "JVC", "Kodak", "Kyocera", "Leica", "Nikon",
      "Olympus", "Panasonic", "Pentax", "Ricoh", "Samsung", "Sanyo",
      "Sigma", "Sony", "Toshiba"
    };

    // Calculates and stores a ranking score for every camera.
    rankings = createRankingArray();
  }


  /**
   * Reads camera information from the data files and creates
   * an array of Cameras objects.
   */
  public Cameras[] createCameras(String modelNamesFileName, String releaseYearFileName,
                                 String priceFileName, String maxResFileName,
                                 String weightInGramsFileName, String normalFocusRangeFileName,
                                 String zoomFileName) {

    // Reads the information from each file and stores it in an array.
    String[] modelNames = FileReader.toStringArray(modelNamesFileName);
    int[] releaseYear = FileReader.toIntArray(releaseYearFileName);
    int[] price = FileReader.toIntArray(priceFileName);
    int[] maxRes = FileReader.toIntArray(maxResFileName);
    int[] weight = FileReader.toIntArray(weightInGramsFileName);
    int[] focusRange = FileReader.toIntArray(normalFocusRangeFileName);
    int[] zoom = FileReader.toIntArray(zoomFileName);

    
    // Creates an array with one position for each camera model.
    Cameras[] returnArray = new Cameras[modelNames.length];

    // Goes through each position and creates a camera using the matching data.
    for (int i = 0; i < returnArray.length; i++) {
      returnArray[i] = new Cameras(modelNames[i], releaseYear[i], price[i],
                                   maxRes[i], weight[i], focusRange[i], zoom[i]);
    }

    // Returns the completed array of camera objects.
    return returnArray;
  }


  /**
   * Calculates a ranking score for every camera using its normalized
   * price, maximum resolution, and weight.
   *
   * Lower ranking scores represent better cameras according to this formula.
   */
  public double[] createRankingArray() {

    // Creates an array to store the ranking scores.
    double[] ranks = new double[allCameras.length];

    // Calculates the ranking score for each camera.
    for (int i = 0; i < allCameras.length; i++) {
      
      // Normalizes each camera's information so the values can be compared.
      double normalPrice = normalizedPrice(allCameras[i].getPrice());
      double normalMaxRes = normalizedMaxRes(allCameras[i].getMaxRes());
      double normalWeight = normalizedWeight(allCameras[i].getWeight());

      // Combines the normalized values using their assigned importance.
      // Price is worth 50%, resolution is worth 35%, and weight is worth 15%.
      ranks[i] = (normalPrice * 0.55)
               + (normalMaxRes * 0.35)
               + (normalWeight * 0.10);
    }

    // Returns the array containing all ranking scores.
    return ranks;
  }

  
  /**
   * Normalizes a camera's price to a value between 0 and 1.
   * A lower price produces a lower normalized value.
   */
  public double normalizedPrice(int value) {
    
    // Defines the minimum and maximum prices in the dataset.
    int minPrice = 14;
    int maxPrice = 7999;

    // Converts the price into a normalized decimal value.
    return (double)(value - minPrice) / (maxPrice - minPrice);
  }


  /**
   * Normalizes a camera's maximum resolution to a value between 0 and 1.
   * A higher resolution produces a lower normalized value.
   */
  public double normalizedMaxRes(int value) {
    
    // Defines the minimum and maximum resolutions in the dataset.
    int minMaxRes = 0;
    int maxMaxRes = 5616;

    // Reverses the scale so higher resolutions receive lower values.
    return (double)(maxMaxRes - value) / (maxMaxRes - minMaxRes);
  }


   /**
   * Normalizes a camera's weight to a value between 0 and 1.
   * A lighter camera produces a lower normalized value.
   */
  public double normalizedWeight(int value) {
    
    // Defines the minimum and maximum camera weights in the dataset.
    int minWeight = 100;
    int maxWeight = 1860;

    // Converts the weight into a normalized decimal value.
    return (double)(value - minWeight) / (maxWeight - minWeight);
  }

  
   /**
   * Gets a camera from the array using its index.
   */
  public Cameras getCameraByIndex(int index) {
    return allCameras[index];
  }

  /**
   * Gets the total number of cameras stored in the array.
   */
  public int getNumberOfCameras() {
    return allCameras.length;
  }

  /**
   * Gets the array containing all camera company names.
   */
  public String[] getAllCompanies() {
    return allCompanies;
  }

  /**
   * Displays the search menu and allows the user to choose
   * how they want to search for camera information.
   */
  public void promptSearch() {

    // Creates a Scanner to receive input from the user.
    Scanner input = new Scanner(System.in);
    
    // Controls whether the program continues running.
    boolean searching = true;

    // Repeats the menu until the user decides to stop.
    while (searching) {
      System.out.println("How do you want to search?");
      System.out.println("1. By Company");
      System.out.println("2. All Companies");
      System.out.print("> ");

      // Gets the user's menu selection.
      int choice = input.nextInt();
      input.nextLine();

      // Searches for cameras from a specific company.
      if (choice == 1) {
        findCameraByCompany(input);
        searching = promptToContinue(input);
      
      // Displays the number of cameras made by each company.
      } else if (choice == 2) {
        printAllCompanies();
        searching = promptToContinue(input);
      
      // Displays an error if the user enters an invalid option.
      } else {
        System.out.println("Not an option. Try again.");
      }
    }

    // Displays a goodbye message and closes the Scanner.
    System.out.println("Goodbye!");
    input.close();
  }

  /**
   * Asks the user whether they want to continue using the program.
   */
  public boolean promptToContinue(Scanner input) {
    System.out.println("Would you like to continue? (Y / N)");
    System.out.print("> ");

    // Gets the user's response.
    String choice = input.nextLine();

    // Continues the program if the user enters Y.
    if (choice.equalsIgnoreCase("Y")) {
      return true;
    
    // Ends the program if the user enters N.
    } else if (choice.equalsIgnoreCase("N")) {
      return false;
    
    // Asks again if the user enters something other than Y or N.
    } else {
      System.out.println("Not an option. Try again.");
      return promptToContinue(input);
    }
  }

  /**
   * Asks the user for a camera company and displays
   * the number of cameras, average price, and best camera
   * made by that company.
   */
  public void findCameraByCompany(Scanner input) {
    
    // Displays all available company names.
    System.out.println("Enter the camera company from the following:");
    System.out.println(printCompanies());
    System.out.print("> ");

    // Gets the company name entered by the user.
    String userCompany = input.nextLine();

    // Counts how many cameras belong to the selected company.
    int cameraCount = countCamerasByCompany(userCompany);

    System.out.println();
    System.out.println("There are " + cameraCount + " cameras made by " + userCompany + ".");

    // Only calculates additional information if the company has cameras.
    if (cameraCount > 0) {

      // Calculates and displays the average camera price.
      int averagePrice = averagePriceByCompany(userCompany);
      System.out.println("The average price is $" + averagePrice + ".");

      // Finds the camera with the lowest ranking score.
      Cameras bestCamera = findBestCameraByCompany(userCompany);

      // Displays the best camera if one was found.
      if (bestCamera != null) {
        System.out.println("\nThe best " + userCompany + " camera is:");
        System.out.println(bestCamera);
      }

    // Displays an error if the company has no matching cameras.
    } else {
      System.out.println("Company not found.");
    }
  }

  /**
   * Counts the number of cameras made by a specific company.
   * Uses startsWith() to match the company name at the beginning
   * of each camera model name.
   */
  public int countCamerasByCompany(String company) {
    int count = 0;

    // Checks every camera in the array.
    for (int i = 0; i < allCameras.length; i++) {

      // Converts both strings to lowercase so the comparison
      // is not affected by uppercase or lowercase letters.
      if (allCameras[i].getName().toLowerCase().startsWith(company.toLowerCase())) {
        count++;
      }
    }

    // Returns the total number of matching cameras.
    return count;
  }

  /**
   * Calculates the average price of cameras made by a specific company.
   */
  public int averagePriceByCompany(String company) {
    // Stores the combined prices of all matching cameras.
    int totalPrice = 0;

    // Counts how many matching cameras are found.
    int count = 0;

    // Goes through every camera in the array.
    for (int i = 0; i < allCameras.length; i++) {

      // Checks whether the camera name starts with the company name.
      if (allCameras[i].getName().toLowerCase().startsWith(company.toLowerCase())) {

        // Adds the camera's price to the total.
        totalPrice += allCameras[i].getPrice();

        // Increases the number of matching cameras.
        count++;
      }
    }

    // Prevents division by zero if no matching cameras exist.
    if (count == 0) {
      return 0;
    }

    // Divides the total price by the number of cameras to get the average.
    return totalPrice / count;
  }

  /**
   * Finds the camera with the lowest ranking score from a specific company.
   */
  public Cameras findBestCameraByCompany(String company) {

    // Starts with a very high ranking score so a lower score can replace it.
    double lowestRank = 1000000;

    // Stores the index of the camera with the lowest ranking.
    int bestIndex = -1;

    // Checks every camera in the array.
    for (int i = 0; i < allCameras.length; i++) {

      // Only considers cameras made by the selected company.
      if (allCameras[i].getName().toLowerCase().startsWith(company.toLowerCase())) {

        // Checks whether the current camera has a better (lower) ranking.
        if (rankings[i] < lowestRank) {

          // Updates the lowest ranking score.
          lowestRank = rankings[i];

          // Saves the index of the camera with that ranking.
          bestIndex = i;
        }
      }
    }

    // Returns null if no camera from the selected company was found.
    if (bestIndex == -1) {
      return null;
    }

    // Returns the camera with the lowest ranking score.
    return allCameras[bestIndex];
  }

  /**
   * Creates an array containing the number of cameras
   * made by each company in the provided array.
   */
  public int[] getCountsForEachCompany(String[] companies) {

    // Creates an integer array with one position for each company.
    int[] countArray = new int[companies.length];

    // Counts the cameras for each company.
    for (int i = 0; i < companies.length; i++) {
      countArray[i] = countCamerasByCompany(companies[i]);
    }

    // Returns the array of camera counts.
    return countArray;
  }

  /**
   * Displays every company and the number of cameras
   * associated with that company.
   */
  public void printAllCompanies() {

    // Gets the number of cameras belonging to each company.
    int[] countCompany = getCountsForEachCompany(allCompanies);

    System.out.println("\nCamera Companies:");

    // Prints each company alongside its camera count.
    for (int i = 0; i < allCompanies.length; i++) {
      System.out.println(allCompanies[i] + ": " + countCompany[i] + " cameras");
    }
  }

  /**
   * Creates a String containing all company names,
   * with each company displayed on a separate line.
   */
  public String printCompanies() {
    // Stores the combined company names.
    String result = "";

    // Adds each company name and a newline to the String.
    for (int i = 0; i < allCompanies.length; i++) {
      result += allCompanies[i] + "\n";
    }

    // Returns the completed String.
    return result;
  }

  /**
   * Creates a String summarizing the camera information,
   * including the total number of cameras and the count
   * of cameras made by each company.
   */
  public String toString() {
    // Stores the information that will be returned.
    String result = "";

    // Adds the title and total number of cameras.
    result += "Camera Information\n";
    result += "Number of Cameras: " + getNumberOfCameras() + "\n";
    result += "\nNumber of Cameras for Each Company:\n";

    // Gets the number of cameras belonging to each company.
    int[] countCompany = getCountsForEachCompany(allCompanies);

    // Adds each company and its camera count to the result.
    for (int i = 0; i < allCompanies.length; i++) {
      result += allCompanies[i] + ": " + countCompany[i] + "\n";
    }

    // Returns the complete camera information.
    return result;
  }
}