package dungeons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A driver class that demonstrates how to use the Game model.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class DriverModel {
  
  /**
   * Main function getting input values for game specification, designed 
   * to represent all dungeon properties. 
   * 
   * @param args command-line arguments including size of the dungeon, 
   *                                    its interconnectivity, whether it is 
   *                                    wrapping or not, and the percentage 
   *                                    of the caves that have treasure.
   */
  public static void main(String[] args) {
    // Initializing a Random Number Generator object common for all objects.
    RandomNumberGenerator random = new RandomNumberGeneratorDev();
    
    if (args.length != 7) {
      throw new IllegalArgumentException("Incorrect command-line arguments.");
    }
    int numOfPlayers = Integer.parseInt(args[0]);
    int row = Integer.parseInt(args[1]);
    int col = Integer.parseInt(args[2]);
    int interconnectivity = Integer.parseInt(args[3]);
    boolean wrapped;
    if (args[4].equals("true")) {
      wrapped = true;
    } else {
      wrapped = false;
    }
    
    double treasure = Double.parseDouble(args[5]);
    int difficulty = Integer.parseInt(args[6]);
    
    // Initializing an adventure game with a wrapped dungeon.
    Game game = new AdventureGame(numOfPlayers, row, col, interconnectivity, wrapped, 
        treasure, difficulty, random);
    
    System.out.println("\n!!!!!!!!-------------------------WELCOME TO THE DUNGEON"
        + "--------------------------!!!!!!!!\n");
    
    System.out.println("The dungeon specifications are: \n\n"
        + "Number of rows: " + row + "\n"
        + "Number of columns: " + col + "\n"
        + "Interconnectivity: " + interconnectivity + "\n"
        + "Is the dungeon wrapped? " + wrapped + "\n"
        + "Proportion of caves with treasure: " + treasure  + "\n\n"); 
    
    String introduction = game.getGameStatus();
    
    System.out.print(introduction);
    
    System.out.println("\n-------------------------------------"
        + "------------------------------------\n");
    
    game.setupPlayerOne(7);
    
    String playerIntroduction = game.introducePlayer();
    
    System.out.print(playerIntroduction);
    
    System.out.println("\n-------------------------------------"
        + "------------------------------------\n");
    
    game.startGame();
    
    String status = game.getGameStatus();
    
    System.out.print(status + "\n\n");
    
    String playerPosition = game.getAvailableMoves();
    
    System.out.print(playerPosition);
        
    List<Direction> allDirections = new ArrayList<>();
    allDirections.addAll(Arrays.asList(Direction.NORTH, Direction.SOUTH, 
        Direction.WEST, Direction.EAST));
    
    List<Treasure> allTreasures = new ArrayList<>();
    allTreasures.addAll(Arrays.asList(Treasure.DIAMONDS, Treasure.RUBIES, 
        Treasure.SAPPHIRE));
     
    boolean flag = true;
    
    while (flag) {
      System.out.println("\n\n------------------------------\n");
      List<Direction> availableDirections = new ArrayList<>();
      
      String[] availableMoves = game.getAvailableMoves().split("\n");
      List<String> directions = new ArrayList<>();
      
      for (int i = 3; i < availableMoves.length; i++) {
        directions.add(availableMoves[i].split(":")[0]);
      }
      
      for (String dirString : directions) {
        for (Direction dir : allDirections) {
          if (dir.toString().equals(dirString)) {
            availableDirections.add(dir);
          }
        }
      }
      Integer randomDirection = random.getRandomNumber(0, availableDirections.size());
      
      try {
        game.move(availableDirections.get(randomDirection));        
        System.out.print(game.getAvailableMoves());
        int numberOfTreasures = random.getRandomNumber(1, 4);
        List<Integer> typesOfTreasures = random.getUniqueRandomNumbersList(
            numberOfTreasures, 0, 3);
        List<Treasure> desired = new ArrayList<>();
        
        for (int i = 0; i < typesOfTreasures.size(); i++) {
          Treasure picked = allTreasures.get(typesOfTreasures.get(i));
          desired.add(picked);
        }
        
        try {
          System.out.print("\n\n" + game.pickUpTreasures(desired));
        } catch (IllegalArgumentException e) {
          // do nothing
        }
      } catch (IllegalStateException s) {
        System.out.print(game.getGameResult());
        
        flag = false;
      }
    }
  }
}
