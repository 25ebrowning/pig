import static utils.Console.*;
  
public class Player {
  private static final int characterLimit = 16;
  private Dice dice;
  protected String name;
  protected boolean ready;
  protected Game game;
  public Player(Game game) {
    this.game = game;
    ready = false;
    dice = new Dice(6);
    this.configure();
  }
  public String getName() {
    return name;
  }
  public boolean getReady() {
    return ready;
  }
  public void configure() {
    notify("Adding player...");
    while (true) {
      String tmpName = in("Enter your name:\n> ");
      if (tmpName.length() < 1) {
        continue;
      } else if (tmpName.length() > characterLimit) {
        notify("Name exceeds character limit (" + characterLimit + ")");
        continue;
      } else if (!game.checkName(tmpName)) {
        notify("Name already taken");
        continue;
      } else if (tmpName.contains("CPU ")) {
        notify("You are not a CPU");
        continue;
      } else {
        this.name = tmpName;
        break;
      }
    }
    notify("Player \"" + this.name + "\" added successfully");
    ready = true;
  }
  public String overview() {
    fg("green");
    return this.name;
  }
}