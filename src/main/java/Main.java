public class Main{
  public static void main(String[] args) {

    // Display intro
    Game.intro();

    
    while(true) {
      Game game = new Game();
      game.prompt();
    }
  }
}
