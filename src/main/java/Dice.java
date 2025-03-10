import java.util.Random;

// Dice class
public class Dice {

  // PIVs
  private Random random;
  private int sides;

  // Constructor
  public Dice (int sides) {
    this.sides = sides;
    this.random = new Random();
  }

  // Return random int from 1-sides, inclusive
  public int roll() {
    return this.random.nextInt(this.sides) + 1;
  }

  // Animated roll()
  public int rollVisual() {
    int result = roll();

    // Dialogue
    T.reset();
    T.out("Rolling");
    T.ellipsis(100, 5);

    // Coloring based on result
    switch (result) {
      case 1:
        T.fg("red"); break;
      case 2:
      case 3:
        T.fg("yellow"); break;
      case 4:
      case 5:
        T.fg("blue"); break;
      case 6:
        T.fg("green"); break;
    }

    // Display result
    T.bold();
    T.ln(" " + result);
    T.reset();
    
    return result;
  }
}