import java.util.ArrayList;

public class Game {

  // Score recorder
  private int[][] scores;

  // Players
  private ArrayList<Player> players;

  // Constructor
  public Game() {
    players = new ArrayList<Player>();
    CPU.reset();
  }

  // Intro dialogue
  public static void intro() {
    T.out("welcome to");
    T.ellipsis(50, 4);
    T.fg("magenta");
    T.ln();
    T.fig("pig", 50);
    T.ln();
    T.reset();
    help();
  }

  // Player count dialogue
  public void count() {
    T.alert("There " + (players.size() == 1 ? "is" : "are") + " now " + players.size() + " player" + (players.size() == 1 ? "" : "s"));
  }

  // Player addition
  public void addPlayer() {
    players.add(new Player(this));
    count();
  }

  // Validates uniqueness of a name
  public boolean checkName(String name) {
    for (Player player : players) {

      // Don't check players without names
      if (player.getReady()) {
        if (player.getName().equals(name)) return false;
      }
    }
    return true;
  }

  // CPU addition
  public void addCPU() {
    players.add(new CPU(this));
    count();
  }

  // Fix CPU names and counters on deletion
  public void fixCPUs() {

    // Sort out non-CPU players
    ArrayList<CPU> cpus = new ArrayList<CPU>();
    for (Player player : players) {
      if (player instanceof CPU) cpus.add((CPU) player);
    }

    // Abort on no CPUs
    if (cpus.size() < 1) return;

    // Call helper function
    fixCPUhelper(cpus, 0);

    // Decrease static CPU counter
    CPU.decrementAll();
  }

  // Helper for fixCPUs()
  private void fixCPUhelper(ArrayList<CPU> cpus, int index) {

    // Abort on completion
    if (index >= cpus.size()) return;

    // If CPU number is greater than index...
    if (Integer.parseInt(cpus.get(index).getName().substring(4)) > index + 1) {

      // Decrement it.
      cpus.get(index).decrement();
    }

    // Recursion
    fixCPUhelper(cpus, index+1);
  }

  // Help dialogue
  public static void help() {
    T.alert("Commands:\n" +
             "  p - add player\n" +
             "  c - add CPU\n" +
             "  v - view players\n" +
             "  d - delete player\n" +
             "  s - start game\n" +
             "  r - view rules\n" +
             "  h - help\n" +
             "  q - quit");
  }

  // Invalid input dialogue
  public void invalid() {
    T.alert("Invalid command. Type \"h\" for help");
  }

  // Allows user to delete a player
  public void deletePlayer() {

    // No players to remove?
    if (players.size() < 1) {
      T.alert("There are no players yet");
      return;
    } 

    // Dialogue for deleting a singular player
    else if (players.size() == 1) {
      while (true) {

        // Dialogue
        T.alert("There is only one player left");
        T.out("Delete \"");
        T.out(players.get(0).overview());
        T.reset();
        String response = T.in("\"? (y/n)\n> ");

        // Deletion logic
        if (response.equals("y")) {
          T.alert("Player \"" + players.get(0).getName() + "\" removed successfully");
          if (players.get(0) instanceof CPU) {
            players.remove(0);
            fixCPUs();
          } else players.remove(0);
          count();
          return;
        }

        // Abortion
        else if(response.equals("n")) {
          T.alert("No action taken");
          count();
          return;
        }
      }
    }

    // Dialogue for deletion of multiple players
    viewPlayers();
    while (true) {

      // Prompt
      String tmpIndex = T.in("Who do you want to delete? (1-" + players.size() + ") (c to cancel)\n> ");

      // No response
      if (tmpIndex.length() < 1) {
        continue;
      }

      // Cancel
      if (tmpIndex.equals("c")) {
        T.alert("Removal canceled");
        return;
      }

      // Try-catch to ensure input is a number
      try {
        int index = Integer.parseInt(tmpIndex);

        // Invalid Range
        if (index < 1 || index > players.size()) {
          T.alert("Invalid range");
          continue;
        }

        // Deletion Dialogue
        T.alert("Player \"" + players.get(index - 1).getName() + "\" removed successfully");

        // Deletion Logic
        if (players.get(index - 1) instanceof CPU) {
          players.remove(index - 1);
          fixCPUs();
        } else players.remove(index - 1);
        return;
      }
        
      catch (NumberFormatException e) {
        
        // Input is not a number
        T.alert("Not a number");
        continue;
      }
    }
  }

  // Dialogue for viewing players
  public void viewPlayers() {

    T.alert("Players:");
    int i = 1;

    // Display player names
    for (Player player : players) {
      T.fg("yellow");
      T.bold();
      T.out(" ".repeat(3) + i + ". ");
      T.reset();
      T.ln(player.overview());
      i++;
    }
    T.reset();
  }

  // Game setup
  public void prompt() {

    // Prompting the user...
    String command = T.in("> ");

    // Commands are all 1 character in length
    if (command.length() != 1) {
      invalid();
      prompt();
    }

    // Switch on input
    char c = command.charAt(0);
    switch (c) {

      // Add player
      case 'p':
        addPlayer(); prompt();

      // Add CPU
      case 'c':
        addCPU(); prompt();

      // View players
      case 'v':
        viewPlayers(); prompt();

      // Delete player
      case 'd':
        deletePlayer(); prompt();

      // Start game
      case 's':
        start(); return;

      // View rules
      case 'r':
        rules(); prompt();

      // Help dialogue
      case 'h':
        help(); prompt();

      // Quit
      case 'q':
        System.exit(0);

      // Recursion on invalid input
      default:
        invalid(); prompt();
    }
  }

  // Rule dialogue
  public static void rules() {
    T.alert("Welcome to Pig! Rules are as follows:\n" +
         "  1. Roll (r) or hold (h) each turn\n" +
         "  2. Roll as much as you would like\n" +
         "  3. If you roll a 1, your turn is over\n" +
         "  4. Holding counts your rolls towards your score\n" +
         "  5. The first player to reach 100 points wins\n");
    T.alert("Have fun!");
  }

  // Game logic
  public void start() {
    
    T.alert("Starting game...");

    // Setting up the record
    scores = new int[1][players.size()];

    // Turn logic
    while (true) {

      // Initialize current row
      for (int sc : scores[0]) {
        sc = -1;
      }

      // Turn loop
      int count = 0;
      for (Player player : players) {
        
        // Turn
        player.turn();
        scores[0][count++] = player.getScore();

        // Player wins if score >= 100
        if (player.getScore() >= 100) {
          win (player);
          return;
        }

        // Display scores each turn
        viewStandings(null);
      }

      // Add a row
      int tempScores[][] = new int[scores.length + 1][scores[0].length];
      for (int i = 0; i < scores.length; i++) {
        tempScores[i+1] = scores[i];
      }
      scores = tempScores;
    }
  }

  // Win dialogue
  
  public void win(Player player) {
    // Dialogue
    T.alert("And the winner is", false);
    T.ellipsis(100, 10);
    T.ln();
    T.reset();
    player.overview();
    T.fig(player.getName(), 50);
    T.ln();
    T.reset();

    // Standings
    viewStandings(player);
  }

  // Display player standings
  public void viewStandings(Player player) {
    T.alert("Standings:");

    // Sort players by score
    ArrayList<Player> playerList = sortedPlayers(players);

    // Display players
    for (int i = 0; i < playerList.size(); i++) {

      // Number formatting
      T.reset();
      T.fg("yellow");
      T.bold();
      T.out(" ".repeat(3) + (i+1) + ". ");

      // Output on player turn
      if (playerList.get(i) == player) {

        // Player name
        T.out(playerList.get(i).overview());

        // Player score and projected (if game is ongoing)
        T.fg("yellow");
        T.ln(": " + playerList.get(i).getScore() + 
             (playerList.get(i).getScore() < 100 ?
              " (projected: " + playerList.get(i).getProjected() + ")" :
              ""));
      }

      // Default output
      else {

        // Player name (not bold)
        T.bold(false);
        T.out(playerList.get(i).overview());

        // Player score (no projected)
        T.fg("yellow");
        T.ln(": " + playerList.get(i).getScore());
      }
    }
    
  }

  // Sorts players by score
  public ArrayList<Player> sortedPlayers(ArrayList<Player> players) {
    ArrayList<Player> sorted = new ArrayList<Player>();

    // Loop through players
    for (Player player : players) {

      // First player is added automatically
      if (sorted.size() == 0) {
        sorted.add(player);
        continue;
      }

      // Find index for insertion via binary search
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

      // Player is added at index
      sorted.add(l, player);
    }
    return sorted;
  }

  // Return highest score amongst players
  public int maxScore(Player player) {
    
    // Return 0 when there are less than 2 players
    if (players.size() < 2) return 0;
    
    // Get sorted players
    ArrayList<Player> players = sortedPlayers(this.players);

    // Don't return current player's score
    if (players.get(0) == player) {
      return players.get(1).getScore();
    }
    
    return players.get(0).getScore();
  }
}