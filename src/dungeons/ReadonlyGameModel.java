package dungeons;

import java.util.List;
import java.util.Map;

/**
 * The interface needed for a read-only model for the Adventure Maze game.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public interface ReadonlyGameModel {
    
  /**
   * Get the game type based on number of players. 
   * 
   * @return 1 if single player game, 2 if two-players game
   */
  int getNumberOfPlayers();
  
  /**
   * Get the current turn, i.e., the player who will make a move or shoot arrow.
   *
   * @return the {@link Players} whose turn it is
   */
  Players getTurn();
  
  /**
   * Get the game status before starting, while the game is running, and after the 
   * ends.
   * 
   * @return game status
   */
  String getGameStatus();
    
  /**
   * Introduce the player.
   * 
   * @return player introduction
   */
  String introducePlayer();
  
  /**
   * Get the current player position, amount of treasures available, 
   * and possible player moves with direction and the neighbor present.
   * 
   * @return description of location and moves
   */
  String getAvailableMoves();
    
  /**
   * Get the player status.
   * 
   * @return player description with their treasure collection
   */
  String getPlayerStatus();
  
  /**
   * Get the game result of whether the player has reached the end or 
   * if the game is ongoing.
   * 
   * @return game result
   */
  String getGameResult();
  
  /**
   * A simple boolean flag to check if game is over.
   * 
   * @return true if game is over either by player winning/losing
   */
  boolean isGameOver();
  
  /**
   * Get the winner of the game. 
   * In a two player game, the one who kills all the monsters first wins the game.
   * 
   * @return winning player or null if player has not reached end yet or has been killed
   */
  Players getWinner();
  
  /**
   * Get the player one name.
   * 
   * @return player one
   */
  Players getPlayerOne();
  
  /**
   * Get the player two name, for a two-player game.
   * 
   * @return player two
   * @throws IllegalStateException if it's a single player game.
   */
  Players getPlayerTwo() throws IllegalStateException;
  
  /**
   * Get the player one location ID.
   * 
   * @return player one location id
   */
  String getPlayerOneLocation();
  
  /**
   * Get the player two location ID, for a two-player game.
   * 
   * @return player two location id
   * @throws IllegalStateException if it's a single player game.
   */
  String getPlayerTwoLocation() throws IllegalStateException;
  
  /**
   * Get the number of arrows a player has.
   * 
   * @param playerId player identifier
   * @return number of arrows
   */
  String getPlayerArrows(String playerId);
  
  /**
   * Get a map of treasures and their count with the player. 
   * 
   * @param playerId player identifier
   * @return treasure counts
   */
  Map<Treasure, Integer> getPlayerTreasures(String playerId);
  
  /**
   * Get the 2-D list of the locations in the dungeon. 
   * 
   * @return dungeon
   */
  List<List<Location>> getDungeon();
  
  /**
   * Get the visited locations in the dungeon.
   * 
   * @return visited location list with location ID
   */
  List<String> getVisitedLocations();
}
