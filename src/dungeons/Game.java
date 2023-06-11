package dungeons;

import java.util.List;

/**
 * This interface represents a setup for maze-based adventure game, giving baseline 
 * game functionalities including setting up the game, getting current status, 
 * moving the players, etc. This can be implemented by a variety of games. 
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public interface Game extends ReadonlyGameModel {
  
  /**
   * Setup player one within the dungeon.
   * 
   * @param id player identifier
   */
  void setupPlayerOne(int id);
  
  /**
   * Setup player two within the dungeon.
   * 
   * @param id player identifier
   */
  void setupPlayerTwo(int id);
    
  /**
   * Start the game. The start and end caves are assigned, player is sent to 
   * the start cave.
   * 
   * @throws IllegalStateException if the player is not yet added to the start
   */
  void startGame() throws IllegalStateException;
  
  /**
   * Pick up desired treasure.
   * 
   * @param desiredTreasure list of treasure names
   * @return details on the treasure picked
   */
  String pickUpTreasures(List<Treasure> desiredTreasure);
  
  /**
   * Pick up arrows present in cave.
   */
  void pickUpArrows();
  
  /**
   * Slay an Otyugh by specifying a direction and distance in which 
   * to shoot the crooked arrow. Arrows travel freely down tunnels 
   * (even crooked ones) but only travel in a straight line through a cave.
   * Distances must be exact or a successful shot.
   * It takes 2 hits to kill an Otyugh.
   * 
   * @param direction direction to shoot
   * @param distance distance to reach, number of caves (but not tunnels)
   *                 that an arrow travels
   * @return success/failure of the shot
   * @throws IllegalArgumentException if given direction is null or distance 
   *                                  is less than 1
   * @throws IllegalStateException if player is not ready
   */
  String shootArrow(Direction direction, int distance) 
      throws IllegalArgumentException, IllegalStateException;
  
  /**
   * Move the player to the required direction. The player moves to the 
   * location in the direction through the entrance.
   * 
   * @param towards direction to move
   * @return result of fight between monster and player
   * @throws IllegalStateException if the game is over
   * @throws IllegalArgumentException if there's no neighbor in said direction
   *                                  or if player is already dead
   */
  String move(Direction towards) throws IllegalStateException, 
      IllegalArgumentException;
}
