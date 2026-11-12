public class Cameras {

  // Stores the name of the camera model.
  private String modelNames;

  // Stores the year the camera was released.
  private int releaseYear;

  // Stores the price of the camera in dollars.
  private int price;

  // Stores the weight of the camera, including batteries, in grams.
  private int weight;

  // Stores the maximum resolution of the camera.
  private int maxRes;

  // Stores the wide zoom value of the camera.
  private int zoom;

  // Stores the normal focus range of the camera.
  private int focusRange;


  /**
   * No-argument constructor.
   * Creates a camera object with default values.
   * Calls the parameterized constructor using this().
   */
  public Cameras() {
    this("no name", 0, 0, 0, 0, 0, 0);
  }


  /**
   * Parameterized constructor.
   * Initializes a camera object using the values provided.
   */
  public Cameras(String modelNames, int releaseYear,
                 int price, int maxRes,
                 int weight, int focusRange,
                 int zoom) {

    // Assigns the provided model name to the instance variable.
    this.modelNames = modelNames;

    // Assigns the camera's release year.
    this.releaseYear = releaseYear;

    // Assigns the camera's price.
    this.price = price;

    // Assigns the camera's maximum resolution.
    this.maxRes = maxRes;

    // Assigns the camera's weight.
    this.weight = weight;

    // Assigns the camera's normal focus range.
    this.focusRange = focusRange;

    // Assigns the camera's zoom value.
    this.zoom = zoom;
  }


  // Getter Methods

  /**
   * Gets the camera's model name.
   */
  public String getName() {
    return modelNames;
  }

  /**
   * Gets the camera's release year.
   */
  public int getYear() {
    return releaseYear;
  }

  /**
   * Gets the camera's price.
   */
  public int getPrice() {
    return price;
  }

  /**
   * Gets the camera's weight.
   */
  public int getWeight() {
    return weight;
  }

  /**
   * Gets the camera's maximum resolution.
   */
  public int getMaxRes() {
    return maxRes;
  }

  /**
   * Gets the camera's zoom value.
   */
  public int getZoom() {
    return zoom;
  }

  /**
   * Gets the camera's normal focus range.
   */
  public int getFocus() {
    return focusRange;
  }


  /**
   * Creates a formatted String containing all the camera's information.
   * This method overrides the default toString() method so that
   * printing a Cameras object displays its specifications.
   */
  public String toString() {
    return "Name: " + modelNames + "\n"
         + "Year Release: " + releaseYear + "\n"
         + "Price: $" + price + "\n"
         + "Weight Including Batteries: " + weight + "g" + "\n"
         + "Maximum Resolution: " + maxRes + "\n"
         + "Zoom Wide: " + zoom + "\n"
         + "Normal Focus Range: " + focusRange;
  }
}
