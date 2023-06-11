package dungeons;

import java.util.List;

/**
 * A dungeon is a network of tunnels and caves that are interconnected so 
 * that player can explore the entire world by traveling from cave to 
 * cave through the tunnels that connect them.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public interface Dungeons {
  
  /**
   * Assign a start and end cave to the maze. The positions are randomly 
   * assigned and have a minimum distance of 5 between the caves.
   */
  void assignStartAndEnd();
  
  /**
   * Get the start cave of the maze. Player is moved to this cave 
   * automatically when the game starts.
   * 
   * @return start cave location object
   */
  Location getStart();
  
  /**
   * Get the end cave of the maze. Player must reach this cave to win the 
   * game.
   * 
   * @return end cave location object
   */
  Location getEnd();
  
  /**
   * Get a 2-D matrix of all location objects. The objects comprise 
   * of caves and tunnels.
   * 
   * @return rows and columns of location objects
   */
  List<List<Location>> getAllLocations();
  
  /**
   * A 2-D matrix of lists of neighbors for each coordinate location. 
   * 
   * @return list of neighbors for each location in the dungeon
   */
  List<List<List<Location>>> getAllAdjacency();
  
  /**
   * Get all caves in the dungeon.
   * 
   * @return list of caves
   */
  List<Location> getCaves();
  
  /**
   * Get all tunnels in the dungeon.
   * 
   * @return list of tunnels
   */
  List<Location> getTunnels();
  
  /**
   * Print the 2-D matrix of location id's (numbers) in the dungeon.
   * 
   * @return 2-D matrix of location id's.
   */
  String printDungeonLayout();
}
