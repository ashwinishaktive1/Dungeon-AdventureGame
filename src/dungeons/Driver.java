package dungeons;

import java.io.InputStreamReader;

/**
 * Run a Adventure game interactively on the console.
 */
public class Driver {
  
  /**
   * Main function getting input values for game specification, designed 
   * to represent all dungeon properties. 
   * 
   * @param args command-line arguments including size of the dungeon, 
   *                                    its interconnectivity, whether it is 
   *                                    wrapping or not, the percentage 
   *                                    of the caves that have treasure, and 
   *                                    number of monsters in the dungeon.
   */
  public static void main(String[] args) {
    // Initializing a Random Number Generator object common for all objects.
    RandomNumberGenerator random = new RandomNumberGeneratorDev();
    if ("--gui".equals(args[0])) {
      Game model = new AdventureGame(random);
      GameView view = new GameViewImpl(model);
      new AdventureGameViewController(view, model, random).playGame();
    } else if ("--text".equals(args[0])) {
      if (args.length < 8) {
        throw new IllegalArgumentException("Incorrect command-line arguments.");
      }
      int numOfPlayers = Integer.parseInt(args[1]);
      int row = Integer.parseInt(args[2]);
      int col = Integer.parseInt(args[3]);
      int interconnectivity = Integer.parseInt(args[4]);
      boolean wrapped;
      if (args[5].equals("true")) {
        wrapped = true;
      } else {
        wrapped = false;
      }
      
      double treasure = Double.parseDouble(args[6]);
      int difficulty = Integer.parseInt(args[7]);
      
      // Initializing an adventure game with a wrapped dungeon.
      Game game = new AdventureGame(numOfPlayers, row, col, interconnectivity, wrapped, 
          treasure, difficulty, random);
      
      String gameType = "";
      if (numOfPlayers == 1) {
        gameType = "It's a single player game!";
      } else {
        gameType = "It's a two-player game!";
      }
      System.out.println(gameType + "\n\nThe dungeon specifications are: \n\n"
          + "Number of rows: " + row + "\n"
          + "Number of columns: " + col + "\n"
          + "Interconnectivity: " + interconnectivity + "\n"
          + "Is the dungeon wrapped? " + wrapped + "\n"
          + "Proportion of caves with treasure: " + treasure 
          + "Number of monsters in dungeon: " + difficulty + "\n\n"); 
      
      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;
      new AdventureGameConsoleController(input, output, game).playGame();
    } 
  }
}
