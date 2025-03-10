import java.util.ArrayList;
import static utils.Console.*;


public class Game {
  private boolean isOver;
  private int[][] scores;
  private ArrayList<Player> players;
  public Game() {
    isOver = false;
    players = new ArrayList<Player>();
    CPU.reset();
  }
  public static void intro() {
    out("welcome to");
    ellipsis(50, 4);
    fg("magenta");
    ln();
    fig("pig", 50);
    ln();
    reset();
    help();
  }
  public void count() {
    alert("There " + (players.size() == 1 ? "is" : "are") + " now " + players.size() + " player" + (players.size() == 1 ? "" : "s"));
  }
  public void addPlayer() {
    players.add(new Player(this));
    count();
  }
  public boolean checkName(String name) {
    for (Player player : players) {
      if (player.getReady()) {
        if (player.getName().equals(name)) return false;
      }
    }
    return true;
  }
  public void addCPU() {
    players.add(new CPU(this));
    count();
  }
  public void fixCPUs() {
    ArrayList<CPU> cpus = new ArrayList<CPU>();
    for (Player player : players) {
      if (player instanceof CPU) cpus.add((CPU) player);
    }
    if (cpus.size() < 1) return;
    fixCPUhelper(cpus, 0);
    CPU.decrementAll();
  }
  private void fixCPUhelper(ArrayList<CPU> cpus, int index) {
    if (index >= cpus.size()) return;
    if (Integer.parseInt(cpus.get(index).getName().substring(4)) > index + 1) {
      cpus.get(index).decrement();
    }
    fixCPUhelper(cpus, index+1);
  }
  public static void help() {
    alert("Commands:\n" +
             "  p - add player\n" +
             "  c - add CPU\n" +
             "  v - view players\n" +
             "  d - delete player\n" +
             "  s - start game\n" +
             "  r - view rules\n" +
             "  h - help\n" +
             "  q - quit");
  }
  public void invalid() {
    alert("Invalid command. Type \"h\" for help");
  }
  public void deletePlayer() {
    if (players.size() < 1) {
      alert("There are no players yet");
      return;
    } else if (players.size() == 1) {
      while (true) {
        alert("There is only one player left");
        out("Delete \"");
        out(players.get(0).overview());
        reset();
        String response = in("\"? (y/n)\n> ");
        switch (response) {
          case "y":
            alert("Player \"" + players.get(0).getName() + "\" removed successfully");
            if (players.get(0) instanceof CPU) {
              players.remove(0);
              fixCPUs();
            } else players.remove(0);
            count();
            return;
          case "n":
            alert("No action taken");
            count();
            return;
          default:
            continue;
        }
      }
    }
    viewPlayers();
    while (true) {
      String tmpIndex = in("Who do you want to delete? (1-" + players.size() + ") (c to cancel)\n> ");
      if (tmpIndex.length() < 1) {
        continue;
      }
      if (tmpIndex.equals("c")) {
        alert("Removal canceled");
        return;
      }
      try {
        int index = Integer.parseInt(tmpIndex);
        if (index < 1 || index > players.size()) {
          alert("Invalid range");
          continue;
        }
        alert("Player \"" + players.get(index - 1).getName() + "\" removed successfully");
        if (players.get(index - 1) instanceof CPU) {
          players.remove(index - 1);
          fixCPUs();
        } else players.remove(index - 1);
        return;
      } catch (NumberFormatException e) {
        alert("Not a number");
        continue;
      }
    }
  }
  public void viewPlayers() {
    alert("Players:");
    int i = 1;
    for (Player player : players) {
      fg("yellow");
      bold();
      out(" ".repeat(3) + i + ". ");
      reset();
      ln(player.overview());
      i++;
    }
    reset();
  }
  public void prompt() {
    String command = in("> ");
    switch (command) {
      case "p":
        addPlayer(); break;
      case "c":
        addCPU(); break;
      case "v":
        viewPlayers(); break;
      case "d":
        deletePlayer(); break;
      case "s":
        start(); break;
      case "r":
        rules(); break;
      case "h":
        help(); break;
      case "q":
        System.exit(0); break;
      default:
        invalid(); break;
    }
  }
  public static void rules() {
    alert("Welcome to Pig! Rules are as follows:\n" +
         "  1. Roll (r) or hold (h) each turn\n" +
         "  2. Roll as much as you would like\n" +
         "  3. If you roll a 1, your turn is over\n" +
         "  4. Holding counts your rolls towards your score\n" +
         "  5. The first player to reach 100 points wins\n");
    alert("Have fun!");
  }
  public void start() {
    alert("Starting game...");
    scores = new int[1][players.size()];

    while (true) {
      for (int score : scores[0]) {
        score = -1;
      }
      int count = 0;
      for (Player player : players) {
        player.turn();
        scores[0][count++] = player.getScore();
        if (player.getScore() >= 100) {
          win (player);
          return;
        }
        viewStandings(null);
      }
      int tempScores[][] = new int[scores.length + 1][scores[0].length];
      for (int i = 0; i < scores.length; i++) {
        tempScores[i+1] = scores[i];
      }
      scores = tempScores;
    }
  }
  public void win(Player player) {
    alert("And the winner is", false);
    ellipsis(100, 10);
    ln();
    reset();
    player.overview();
    fig(player.getName(), 50);
    ln();
    reset();
    viewStandings(player);
    isOver = true;
  }
  public void viewStandings(Player player) {
    alert("Standings:");
    ArrayList<Player> playerList = sortedPlayers(players);
    for (int i = 0; i < playerList.size(); i++) {
      reset();
      fg("yellow");
      bold();
      out(" ".repeat(3) + (i+1) + ". ");
      if (playerList.get(i) == player) {
        out(playerList.get(i).overview());
        fg("yellow");
        ln(": " + playerList.get(i).getScore() + " (projected: " + playerList.get(i).getProjected() + ")");
      } else {
        bold(false);
        out(playerList.get(i).overview());
        fg("yellow");
        ln(": " + playerList.get(i).getScore());
      }
    }
    
  }
  public ArrayList<Player> sortedPlayers(ArrayList<Player> players) {
    ArrayList<Player> sorted = new ArrayList<Player>();
    for (Player player : players) {
      if (sorted.size() == 0) {
        sorted.add(player);
        continue;
      }
      int l = 0;
      int r = sorted.size() - 1;
      int t = player.getScore();
      while (l <= r) {
        int m = l + (r - l) / 2;
        if (sorted.get(m).getScore() > t) {
          l = m + 1;
        } else {
          r = m - 1;
        }
      }
      sorted.add(l, player);
    }
    return sorted;
  }
  public int maxScore(Player player) {
    ArrayList<Player> players = sortedPlayers(this.players);
    if (players.get(0) == player) {
      return players.get(1).getScore();
    }
    return players.get(0).getScore();
  }
  public boolean getIsOver() {
    return isOver;
  }
}