package dungeons;

/**
 * The dungeon caves may be occupied by monsters that are 
 * ready to kill the players. A player can hit the monster 
 * using weapons.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public interface Monsters {
  
  /**
   * Get the monster identifier.
   * 
   * @return monster identifier
   */
  String getMonsterId();
  
  /**
   * Set true when the monster is assigned to a location.
   */
  void setMonsterResidingStatus();
  
  /**
   * Get the current residing status of the monster, i.e. whether 
   * it has been assigned to a location.
   * 
   * @return true if monster is residing in a cave else false
   */
  boolean getMonsterResidingStatus();
    
  /**
   * Hit the monster with weapon, causing the monster to loose health.
   */
  void hit();
  
  /**
   * Get the current status of monster. This tells if the monster is alive, 
   * injured, or dead.
   * 
   * @return status of the monster
   */
  String getStatus();
}
