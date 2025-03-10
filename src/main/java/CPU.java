// CPU version of Player
public class CPU extends Player {

  // PIVs
  private static int numCPUs = 0;
  private int lvl;

  // Constructor
  public CPU (Game game) {
    super(game);
  }

  // Prompt user for CPU lvl
  public void configure() {
    T.alert("Adding CPU...");
    this.name = "CPU " + (++numCPUs);

    // Validate input
    while (true) {
      try {
        String tempLvl = T.in("Set difficulty for the CPU (1-10):\n> ");
        this.lvl = Integer.parseInt(tempLvl);

        // Invalid range
        if (this.lvl < 1 || this.lvl > 10) {
          T.alert("Invalid range");
          continue;
        }
        break;
      } catch (NumberFormatException e) {

        // Not a number
        T.alert("Not a number");
      }
    }
    
  T.alert("CPU \"" + this.name + "\" (lvl " + this.lvl + ") added successfully");
  }

  // CPU does turn by itself
  public boolean turnInput(int currScore, int score) {

    String name = overview();
    
    // Clear overview() formatting
    T.reset();
    T.alert(name + " is thinking", false);
    T.ellipsis(100, 5);

    // Decision logic
    int threshold = 15 + ((lvl - 1) / 2) * 2;
    int diff = getScore() - game.maxScore(this);
    threshold += diff / 10;
    boolean roll = currScore < threshold;

    // Rolling
    if (roll && score < 100) {
      T.ln(" Roll!");
      T.reset();
      return true;
    }

    // Holding
    T.ln(" Hold!");
    T.reset();
    return false;
  }

  // Decrease current CPU number
  public void decrement() {
    name = "CPU " + (Integer.parseInt(name.substring(4)) - 1);
  }

  // Decrease CPU counter
  public static void decrementAll() {
    numCPUs--;
  }

  // Reset CPU counter
  public static void reset() {
    numCPUs = 0;
  }

  // Modified overview() with new formatting and CPU lvl
  public String overview() {
    T.fg("blue");
    return this.name + " (lvl " + this.lvl + ")";
  }
}