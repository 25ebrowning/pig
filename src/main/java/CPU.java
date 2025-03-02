public class CPU extends Player {
  private static int numCPUs = 0;
  public CPU (Game game) {
    super(game);
  }
  public static void reset() {
    numCPUs = 0;
  }
}