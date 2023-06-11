package dungeons;

import java.util.List;
import java.util.Map;

/**
 * A location is any spot in the dungeon where the player can 
 * explore and be connected to other locations. A location can 
 * be classified as tunnel (which has exactly 2 entrances) or 
 * a cave (which has 1, 3 or 4 entrances).
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public interface Location {
  
  /**
   * Get location identifier.
   * 
   * @return location identifier
   */
  String getLocationId();
  
  /**
   * Get location identifier number.
   * 
   * @return location identifier number
   */
  int getLocationNum();
  
  /**
   * Get the neighbors and their directions.
   * 
   * @return details of current location and the connected neighbors
   */
  String getLocationDetails();
  
  /**
   * Get the current row and column number of the location in 
   * the dungeon grid. 
   * 
   * @return row x column number
   */
  int[] getPosition();
  
  /**
   * Add a location neighbor, along with the direction they reside in 
   * to the neighbor list for the location. 
   * 
   * @param neighborDirection direction of the neighbor
   * @param neighbor location of the neighbor
   * @throws IllegalArgumentException if the neighbor is duplicate of 
   *                                  current location or if the 
   *                                  direction has been assigned already 
   *                                  or if the neighbor has been assigned 
   *                                  at some other direction before
   * @throws IllegalStateException if a maximum of 4 neighbors have been 
   *                               assigned to the location
   */
  void addNeighbor(Direction neighborDirection, Location neighbor)
      throws IllegalArgumentException, 
      IllegalStateException;
  
  /**
   * Get the neighbors and the direction in which the player 
   * must move to reach the location.
   * 
   * @return Dictionary of directions and the locations present 
   *         in those directions
   */
  Map<Direction, Location> getNeighbors();
  
  /**
   * Add treasures to the location while assembling the dungeon. 
   * Treasures can only be added to caves.
   * 
   * @param allotedTreasure Treasures alloted while random 
   *                        distribution to the location
   * @throws IllegalStateException If the treasures are assigned 
   *                               to a tunnel
   */
  void distributeTreasures(Map<Treasure, Integer> allotedTreasure) 
      throws IllegalStateException;
  
  /**
   * Get the current list of treasures present in the location. 
   * 
   * @param desiredTreasure treasures the player wants to pick up
   * @return Map of treasures and their count present in the location
   * @throws IllegalArgumentException if requested treasure is not
   *                                  present in the location
   */
  Map<Treasure, Integer> collectTreasures(List<Treasure> desiredTreasure) 
      throws IllegalArgumentException;
  
  /**
   * Get the available treasures in the location. 
   * 
   * @return String of available treasure names
   */
  String getPresentTreasures();
  
  /**
   * Add player to the location. 
   * 
   * @param p Player
   * @throws IllegalArgumentException if the player is null
   */
  void addPlayer(Players p) throws IllegalArgumentException;
  
  /**
   * Remove existing player from the location. 
   * 
   * @param p player to be moved from the location
   * @throws IllegalStateException if no player is present
   * @throws IllegalArgumentException if requested player is not present
   */
  void removePlayer(Players p) throws IllegalStateException, IllegalArgumentException;
  
  /**
   * Get a list of the player present in the location. 
   * Must return an empty list if player is not present.
   * 
   * @return List of the player present in the location
   */
  List<Players> getPlayersPresent();
  
  /**
   * Add arrows to the location while setting up the dungeon.
   * 
   * @param arrowsAssigned number of arrows assigned to the location
   */
  void addArrows(int arrowsAssigned);
  
  /**
   * Get the number of arrows present in cave/tunnel.
   * 
   * @return number of arrows in the location
   */
  int getArrowsInLocation();
  
  /**
   * Collect all the arrows present in the location. 
   * 
   * @return number of arrows picked up
   */
  int collectArrows();
  
  /**
   * Add monster to a cave. Monsters cannot be present in a tunnel.
   * 
   * @param oty Monster added to the location
   * @throws IllegalArgumentException if the monster object is null or if 
   *                                  the cave is pre-occupied by another monster.
   * @throws IllegalStateException if monster is added to a tunnel
   */
  void addMonster(Monsters oty) throws IllegalArgumentException, IllegalStateException;
  
  /**
   * Get the monster present in the cave. Monsters cannot be present 
   * in a tunnel.
   * 
   * @return Monster present in the cave
   */
  Monsters getMonster();
  
  /**
   * Remove the monster from the cave when it is dead.
   * 
   * @param oty monster to be removed
   * @throws IllegalArgumentException if that monster is not present
   */
  void removeMonster(Monsters oty) throws IllegalArgumentException;
}
