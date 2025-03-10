import java.util.Random;
import static utils.Console.*;
public class Dice {
  private Random random;
  private int sides;
  public Dice (int sides) {
    this.sides = sides;
    this.random = new Random();
  }
  public int roll() {
    return this.random.nextInt(this.sides) + 1;
  }
  public int rollVisual() {
    int result = roll();
    reset();
    out("Rolling");
    ellipsis(100, 5);
    switch (result) {
      case 1:
        fg("red"); break;
      case 2:
      case 3:
        fg("yellow"); break;
      case 4:
      case 5:
        fg("blue"); break;
      case 6:
        fg("green"); break;
    }
    bold();
    ln(" " + result);
    reset();
    return result;
  }
}