package dungeons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DriverModel Class to run the program with a wrapped dungeon.
 * The run shows the player visiting every location in the dungeon.
 */
public class UnwrappedDungeonDriver {

  public UnwrappedDungeonDriver() {
  }
  
  /**
   * Main function where pre-set inputs are given and outputs are printed 
   * to have a full view of the dungeon creation.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    // Initializing a fixed seed Random Number Generator object common for all objects.
    // Visualized the map to reach end from start with the player visiting every 
    // location in the dungeon.
    RandomNumberGenerator random = new RandomNumberGeneratorTest(0, 8, 2, 3, 5, 6);
    
    int numOfPlayers = 1;
    int row = 5;
    int col = 5;
    int interconnectivity = 4;
    boolean wrapped = false;
    double treasure = 30;
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
    
    game.setupPlayerOne(777);
    
    String playerIntroduction = game.introducePlayer();
    
    System.out.print(playerIntroduction);
    
    System.out.println("\n-------------------------------------"
        + "------------------------------------\n");
    
    game.startGame();
    
    String status = game.getGameStatus();
    
    System.out.print(status + "\n\n");
    
    String playerPosition = game.getAvailableMoves();
    
    System.out.print(playerPosition);
    
    List<Treasure> allTreasures = new ArrayList<>();
    allTreasures.addAll(Arrays.asList(Treasure.DIAMONDS, Treasure.RUBIES, 
        Treasure.SAPPHIRE));
    
    // Initializing a Random Number Generator object for picking treasures.
    RandomNumberGenerator randomTreasure = new RandomNumberGeneratorDev();
    
    List<Direction> fixedMoves = new ArrayList<>();
    fixedMoves.addAll(Arrays.asList(Direction.WEST, Direction.SOUTH, 
        Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.EAST, 
        Direction.NORTH, Direction.NORTH, Direction.NORTH, Direction.NORTH, 
        Direction.EAST, Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, 
        Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.NORTH, 
        Direction.NORTH, Direction.NORTH, Direction.EAST, Direction.SOUTH, 
        Direction.SOUTH, Direction.SOUTH, Direction.SOUTH, Direction.NORTH,
        Direction.WEST, Direction.SOUTH));
    
    for (Direction move : fixedMoves) {
      System.out.println("\n\n------------------------------\n");
      game.move(move);
      System.out.print(game.getAvailableMoves());
      helperTreasure(allTreasures, randomTreasure, game);
    }
    
    System.out.println("\n\n------------------------------\n");
    System.out.print(game.getGameResult());
  }
  
  /**
   * Helper method to pick up random treasures from the location if 
   * available.
   * 
   * @param allTreasures all types of treasures
   * @param random Random Number Generator object
   * @param game Adventure Game object
   */
  private static void helperTreasure(List<Treasure> allTreasures, 
      RandomNumberGenerator random, Game game) {
    int numberOfTreasures = random.getRandomNumber(1, 4);
    List<Integer> typesOfTreasures = random.getUniqueRandomNumbersList(
        numberOfTreasures, 0, 3);
    List<Treasure> desired = new ArrayList<>();
    
    for (int i = 0; i < typesOfTreasures.size(); i++) {
      Treasure picked = allTreasures.get(typesOfTreasures.get(i));
      desired.add(picked);
    }
    
    try {
      System.out.print("\n" + game.pickUpTreasures(desired));
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }
}
