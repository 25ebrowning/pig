
public class Print {
  // ANSI color codes
  public static final String RESET = "\u001B[0m";
  public static final String BLACK = "\u001B[30m";
  public static final String RED = "\u001B[31m";
  public static final String GREEN = "\u001B[32m";
  public static final String YELLOW = "\u001B[33m";
  public static final String BLUE = "\u001B[34m";
  public static final String PURPLE = "\u001B[35m";
  public static final String CYAN = "\u001B[36m";
  public static final String WHITE = "\u001B[37m";

  /**
   * Print text in the specified color
   * @param text The text to print
   * @param color The color to use (one of the color constants)
   */
  public static void color(String text, String color) {
    System.out.print(color + text + RESET);
  }
  
  /**
   * Print text in the specified color with a newline
   * @param text The text to print
   * @param color The color to use (one of the color constants)
   */
  public static void colorln(String text, String color) {
    System.out.println(color + text + RESET);
  }
  
  /**
   * Print colored text based on color name
   * @param color The name of the color (red, green, blue, etc.)
   */
  public static void color(String color) {
    switch(color.toLowerCase()) {
      case "black":
        System.out.print(BLACK);
        break;
      case "red":
        System.out.print(RED);
        break;
      case "green":
        System.out.print(GREEN);
        break;
      case "yellow":
        System.out.print(YELLOW);
        break;
      case "blue":
        System.out.print(BLUE);
        break;
      case "purple":
        System.out.print(PURPLE);
        break;
      case "cyan":
        System.out.print(CYAN);
        break;
      case "white":
        System.out.print(WHITE);
        break;
      default:
        System.out.print(RESET);
        break;
    }
  }
  
  /**
   * Reset console color to default
   */
  public static void reset() {
    System.out.print(RESET);
  }
}
