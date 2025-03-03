import static utils.Console.*;
  
public class Player {
  private static final int characterLimit = 16;
  private Dice dice;
  protected String name;
  protected boolean ready;
  protected Game game;
  protected int score;
  protected int currScore;
  public Player(Game game) {
    currScore = 0;
    this.game = game;
    ready = false;
    dice = new Dice(6);
    score = 0;
    this.configure();
  }
  public int getProjected() {
    return score + currScore;
  }
  public String getName() {
    return name;
  }
  public boolean getReady() {
    return ready;
  }
  public void configure() {
    alert("Adding player...");
    while (true) {
      String tmpName = in("Enter your name:\n> ");
      if (tmpName.length() < 1) {
        continue;
      } else if (tmpName.length() > characterLimit) {
        alert("Name exceeds character limit (" + characterLimit + ")");
        continue;
      } else if (!game.checkName(tmpName)) {
        alert("Name already taken");
        continue;
      } else if (tmpName.contains("CPU ")) {
        alert("You are not a CPU");
        continue;
      } else {
        this.name = tmpName;
        break;
      }
    }
    alert("Player \"" + this.name + "\" added successfully");
    ready = true;
  }
  public void turn() {
    alert(getName() + "'s turn");
    currScore = 0;
    while (true) {
      boolean input = turnInput(currScore, score);
      if (input) {
        int roll = dice.rollVisual();
        if (roll == 1) {
          alert("Womp womp. No points for you");
          return;
        }
        else { 
          currScore += roll;
          alert("Streak: " + currScore + ", Projected: " + (currScore + score));
        }
      } else {
        score += currScore;
        currScore = 0;
        alert(getName() + "'s turn is over");
        return;
      }
    }
  }
  public boolean turnInput(int currScore, int score) {
    while (true) {
      alert("(r)oll, (h)old, or (v)iew standings?");
      out(overview());
      reset();
      String input = in(" > ");
      if (input.equals("r")) {
        return true;
      } else if (input.equals("h")) {
        return false;
      } else if (input.equals("v")) {
        game.viewStandings(this);
        continue;
      } else {
        alert("Invalid command");
        continue;
      }
    }
  }
  public int getScore() {
    return score;
  }
  public String overview() {
    fg("green");
    return this.name;
  }
  public void roll() {
    dice.rollVisual();
  }
}