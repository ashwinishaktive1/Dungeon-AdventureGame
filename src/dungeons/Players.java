package dungeons;

import java.util.List;
import java.util.Map;

/**
 * Players are equipped to explore the interconnected maze and 
 * collect treasures wherever possible. The player may further be 
 * implemented for other kinds of games.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public interface Players {
  
  /**
   * Collect the treasures present in their same location.
   * 
   * @param treasuresDesired the treasure player wants to collect
   * @throws IllegalStateException if player is dead
   */
  void pickTreasures(List<Treasure> treasuresDesired) throws IllegalStateException;
  
  /**
   * Get the player identifier.
   * 
   * @return player id
   */
  String getPlayerId();
  
  /**
   * Provide a description of the player that 
   * includes a description of what treasure the player has collected.
   * 
   * @return player's current status
   */
  String getPlayerDescription();
  
  /**
   * Get the current location object. Used for debugging and location 
   * analysis.
   * 
   * @return current location object
   */
  Location getCurrentLocation();
  
  /**
   * Move the player to a new location, that location will be 
   * set as the current location.
   * 
   * @param newLocation the new position player has moved to
   * @throws IllegalStateException if player is dead
   */
  void setCurrentLocation(Location newLocation) throws IllegalStateException;
  
  /**
   * Provide a description of the player's location that 
   * at the minimum includes a description of treasure in the 
   * room and the possible moves (north, east, south, west) 
   * that the player can make from their current location.
   * 
   * @return player and their current location details
   */
  String getCurrentLocationDetails();
  
  /**
   * Get a map of treasures and their count with the player. 
   * 
   * @return treasure counts
   */
  Map<Treasure, Integer> getTreasuresInBag();
  
  /**
   * Get the number of crooked arrows with the player.
   * 
   * @return number of arrows in hand
   */
  int getArrowsInHand();
  
  /**
   * Collect arrows from the location and add it to the arrow back-pack.
   * 
   * @throws IllegalStateException if no arrows present in the cave
   *                               or if player is dead
   */
  void pickupArrows() throws IllegalStateException;
  
  /**
   * Use one arrow from the player back-pack.
   * 
   * @throws IllegalStateException if player does not have any arrow
   *                               or if player is dead
   */
  void useArrow() throws IllegalStateException;
  
  /**
   * Kill the player. 
   * 
   * @throws IllegalStateException if the player is already dead
   */
  void killPlayer() throws IllegalStateException;
  
  /**
   * Check if player is alive.
   * @return true if player is alive
   */
  boolean isAlive();
}
