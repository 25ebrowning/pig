


public class Main{
  public static void main(String[] args) {
    Game.intro();
    while(true) {
      Game game = new Game();
      while(!game.getIsOver()) {
        game.prompt();
      }
    }
  }
}
