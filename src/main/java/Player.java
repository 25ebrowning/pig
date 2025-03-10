// Player class
public class Player {

  // PIVs
  private static final int characterLimit = 16;
  private Dice dice;

  // Variables accessible to children
  protected String name;
  protected boolean ready; // true after configure()
  protected Game game;
  protected int score;
  protected int currScore;

  // Constructor
  public Player(Game game) {
    currScore = 0;
    this.game = game;
    ready = false;
    dice = new Dice(6);
    score = 0;

    // Prompt user for name
    this.configure();
  }

  // Projected score
  public int getProjected() {
    return score + currScore;
  }

  // Name getter
  public String getName() {
    return name;
  }

  // Ready flag getter
  public boolean getReady() {
    return ready;
  }

  // Prompt user for configuration
  public void configure() {
    T.alert("Adding player...");

    // Prompt for name
    while (true) {
      String tmpName = T.in("Enter your name:\n> ");

      // Names can't be empty
      if (tmpName.length() < 1) {
        continue;
      }

      // Names must stay within character limit
      else if (tmpName.length() > characterLimit) {
        T.alert("Name exceeds character limit (" + characterLimit + ")");
        continue;
      }

      // Names must be unique
      else if (!game.checkName(tmpName)) {
        T.alert("Name already taken");
        continue;
      }

      // Names cannot contain CPU
      else if (tmpName.contains("CPU ")) {
        T.alert("You are not a CPU");
        continue;
      }

      // All checks passed
      this.name = tmpName;
      break;
    }

    // Dialogue
    T.alert("Player \"" + this.name + "\" added successfully");

    // Set flag
    ready = true;
  }

  // Turn logic
  public void turn() {
    currScore = 0;
    
    // Dialogue
    T.alert(getName() + "'s turn");

    // Turn loop
    while (true) {

      // Roll or hold?
      boolean input = turnInput(currScore, score);

      // Rolling
      if (input) {
        int roll = dice.rollVisual();

        // Turn over on a 1
        if (roll == 1) {
          T.alert("Womp womp. No points for you");
          return;
        }

        // Add to score otherwise
        else { 
          currScore += roll;

          // Dialogue
          T.alert("Streak: " + currScore + ", Projected: " + (currScore + score));
        }
      }

      // Holding
      else {
        
        // Add to total score
        score += currScore;
        currScore = 0;
        T.alert(getName() + "'s turn is over");
        return;
      }
    }
  }

  // Prompt user for input
  public boolean turnInput(int currScore, int score) {

    // Prompt loop
    while (true) {

      // Dialogue
      T.alert("(r)oll, (h)old, or (v)iew standings?");
      T.out(overview());
      T.reset();
      String input = T.in(" > ");

      // Roll and return
      if (input.equals("r")) {
        return true;
      }

      // Hold and return
      else if (input.equals("h")) {
        return false;
      } 

      // View standings
      else if (input.equals("v")) {
        game.viewStandings(this);
        continue;
      } 

      // Invalid input
      else {
        T.alert("Invalid command");
        continue;
      }
    }
  }

  // Score getter
  public int getScore() {
    return score;
  }

  // Overview with formatting
  public String overview() {
    T.fg("green");
    return this.name;
  }
}