import static utils.Console.*;
public class CPU extends Player {
  private static int numCPUs = 0;
  private int lvl;
  public CPU (Game game) {
    super(game);
  }
  public void configure() {
    alert("Adding CPU...");
    this.name = "CPU " + (++numCPUs);
    while (true) {
      try {
        String tempLvl = in("Set difficulty for the CPU (1-10):\n> ");
        this.lvl = Integer.parseInt(tempLvl);
        if (this.lvl < 1 || this.lvl > 10) {
          alert("Invalid range");
          continue;
        }
        break;
      } catch (NumberFormatException e) {
        alert("Not a number");
      }
    }
    
    alert("CPU \"" + this.name + "\" (lvl " + this.lvl + ") added successfully");
  }
  public boolean turnInput(int currScore, int score) {
    String name = overview();
    reset();
    alert(name + " is thinking", false);
    ellipsis(100, 5);
    int threshold = 15 + ((lvl - 1) / 2) * 2;
    int diff = getScore() - game.maxScore(this);
    threshold += diff / 10;
    boolean roll = currScore < threshold;
    if (roll) {
      ln(" Roll!");
      reset();
      return true;
    }
    ln(" Hold!");
    reset();
    return false;
  }
  public void decrement() {
    name = "CPU " + (Integer.parseInt(name.substring(4)) - 1);
  }
  public static void decrementAll() {
    numCPUs--;
  }
  public static void reset() {
    numCPUs = 0;
  }
  public String overview() {
    fg("blue");
    return this.name + " (lvl " + this.lvl + ")";
  }
}