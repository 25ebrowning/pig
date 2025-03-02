import java.util.Scanner;
public class Player {
  private Dice dice;
  private int score;
  private String name;
  public Player(Game game) {
    this.configure();
  }
  public void configure() {
    this.setName(Scan.scan("What is your name? "));
  }
  public void setName(String name) {
    this.name = name;
  }
}