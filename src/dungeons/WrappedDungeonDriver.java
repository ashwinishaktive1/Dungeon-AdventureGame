package dungeons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DriverModel Class to run the program with a wrapped dungeon.
 * The run shows the player's location and description at each step and 
 * shows the player starting at the start and reaching the end.
 */
public class WrappedDungeonDriver {

  public WrappedDungeonDriver() {
  }
  
  /**
   * Main function where pre-set inputs are given and outputs are printed 
   * to have a full view of the dungeon creation.
   * 
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    // Initializing a Random Number Generator object common for all objects.
    RandomNumberGenerator random = new RandomNumberGeneratorDev();
    
    int numOfPlayers = 1;
    int row = 6;
    int col = 6;
    int interconnectivity = 8;
    boolean wrapped = true;
    double treasure = 41.3;
    int difficulty = 4;
    
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
