import static utils.Console.*;
public class CPU extends Player {
  private static int numCPUs = 0;
  private int lvl;
  public CPU (Game game) {
    super(game);
  }
  public void configure() {
    notify("Adding CPU...");
    this.name = "CPU " + (++numCPUs);
    while (true) {
      try {
        String tempLvl = in("Set difficulty for the CPU (1-10):\n> ");
        this.lvl = Integer.parseInt(tempLvl);
        if (this.lvl < 1 || this.lvl > 10) {
          notify("Invalid range");
          continue;
        }
        break;
      } catch (NumberFormatException e) {
        notify("Not a number");
      }
    }
    
    notify("CPU \"" + this.name + "\" (lvl " + this.lvl + ") added successfully");
  }
  public static void reset() {
    numCPUs = 0;
  }
  public String overview() {
    fg("blue");
    return this.name + " (lvl " + this.lvl + ")";
  }
}