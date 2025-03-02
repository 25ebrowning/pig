import java.util.Scanner;

public class Scan {
  public static String scan(String prompt) {
    Scanner scanner = new Scanner(System.in);
    System.out.print(prompt);
    String result = scanner.nextLine();
    scanner.close();
    return result;
  }
}