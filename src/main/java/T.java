import java.util.HashMap;
import java.util.Scanner;
import com.github.lalyos.jfiglet.FigletFont;

// Class with terminal utilities
public class T {
  public static Scanner scanner;

  // Color -> ANSI code lookup
  public static final HashMap<String, Integer> colors;
  static {
    scanner = new Scanner(System.in);
      colors = new HashMap<String, Integer>();
    
      colors.put("black", 30);
      colors.put("red", 31);
      colors.put("green", 32);
      colors.put("yellow", 33);
      colors.put("blue", 34);
      colors.put("magenta", 35);
      colors.put("cyan", 36);
      colors.put("white", 37);

      colors.put("bright_black", 90);   
      colors.put("bright_red", 91);     
      colors.put("bright_green", 92);   
      colors.put("bright_yellow", 93);  
      colors.put("bright_blue", 94);    
      colors.put("bright_magenta", 95); 
      colors.put("bright_cyan", 96);    
      colors.put("bright_white", 97);
  }

  // set foreground color
  public static void fg (String color) {
    System.out.print("\u001b[" + colors.get(color) + "m");
  }

  // set background color
  public static void bg (String color) {
    System.out.print("\u001b[48;5;" + colors.get(color) + "m");
  }

  // reset ANSI stuff
  public static void reset () {
    System.out.print("\u001b[0m");
  }

  // reset fg
  public static void resetFg () {
    System.out.print("\u001b[39m");
  }

  // reset bg
  public static void resetBg () {
    System.out.print("\u001b[49m");
  }

  // make text bold
  public static void bold (boolean on) {
    if (on) System.out.print("\u001b[1m");
    else System.out.print("\u001b[22m");
  }

  // simpler bold
  public static void bold () {
    bold(true);
  }

  // underline
  public static void under (boolean on) {
    if (on) System.out.print("\u001b[4m");
    else System.out.print("\u001b[24m");
  }
  public static void under () {
    under(true);
  }

  // Output
  public static void out(String text) {
    System.out.print(text);
  }
  public static void ln(String text) {
    out(text);
    System.out.println();
  }
  public static void ln() {
    System.out.println();
  }

  // Input
  public static String in() {
    String result = scanner.nextLine();
    return result.trim();
  }
  public static String in(String text) {
    out(text);
    return in();
  }

  // Alert dialogue
  public static void alert(String text) {
    alert(text, true);
  }
  public static void alert(String text, boolean reset) {
    reset();
    fg("yellow");
    bold();
    out("! " + text + (reset ? "\n" : ""));
    if (reset) reset();
  }

  // Animated ellipsis
  public static void ellipsis(int delay, int dots) {
    for (int i = 0; i < dots; i++) {
      out(".");
      try {
        Thread.sleep(delay);
      } catch (Exception e) {
        alert("Error");
      }
    }
  }

  // Ascii art via FigletFont
  public static void fig(String text, int delay) {

    // Required try-catch
    try {
      String output = FigletFont.convertOneLine(text);

      // Split lines and output gradually
      String[] lines = output.split("\n");
      for (String line : lines) {
        Thread.sleep(delay);
        ln(line);
      }

    } catch (Exception e) {

      // Something went wrong
      alert("FigletFont error");
    }
  }
}