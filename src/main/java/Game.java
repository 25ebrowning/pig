import java.util.ArrayList;
import static utils.Console.*;
import com.github.lalyos.jfiglet.FigletFont;

public class Game {
  public static void intro() {
    ln("welcome to...\n");
    try {
      String output = FigletFont.convertOneLine("pig");
      String[] lines = output.split("\n");
      for (String line : lines) {
        ln(line);
      }
    } catch (Exception e) {
      ln("Error displaying ASCII art: " + e.getMessage());
    }
  }
  private ArrayList<Player> players;
  public Game() {
    players = new ArrayList<Player>();
    CPU.reset();
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
    int tracker = 1;
    for (CPU cpu : cpus) {
      if (Integer.parseInt(cpu.getName().substring(4)) > tracker) {
        cpu.decrement();
      }
      tracker++;
    }
    CPU.decrementAll();
  }
  public void help() {
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
        break;
      case "r":
        break;
      case "h":
        help(); break;
      case "q":
        System.exit(0); break;
      default:
        invalid(); break;
    }
  }
}