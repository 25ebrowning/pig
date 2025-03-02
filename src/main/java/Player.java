import java.util.Scanner;
public class Player {
  private Dice dice;
  private int score;
  private String name;
  public Player(Game game) {
    this.configure();
  }
  public void configure() {
    T.fg("green");
    T.bold();
    T.out("What is your name? ");
    T.reset();
    this.name = T.in();
  }
  public void setName(String name) {
    this.name = name;
  }
}