import java.util.ArrayList;
public class Game {
  private ArrayList<Player> players;
  public Game() {
    players = new ArrayList<Player>();
    CPU.reset();
  }
  public void addPlayer() {
    players.add(new Player(this));
    
  }
  public void addCPU() {
    players.add(new CPU(this));
  }
}