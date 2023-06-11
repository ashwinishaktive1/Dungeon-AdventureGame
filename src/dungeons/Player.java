package dungeons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The player implements the features of the general Players interface to 
 * traverse through the dungeon maze. The player collects treasures while 
 * traveling from the start to end. The player is also equipped to 
 * provide self and current location description.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class Player implements Players {
  
  private String playerId;
  private Map<Treasure, Integer> treasuresCollected;
  private Location currentLocation;
  private int arrowsInHand;
  private boolean alive;
  
  /**
   * Construct a player with the given id. The player now has an empty treasure 
   * drum and is open to be placed on any location.
   * 
   * @param id player identifying number
   */
  public Player(int id) {
    this.playerId = "Player " + id;
    this.treasuresCollected = new HashMap<>();
    this.setupTreasures();
    this.currentLocation = null;
    this.arrowsInHand = 3;
    this.alive = true;
  }
  
  private void setupTreasures() {
    this.treasuresCollected.put(Treasure.DIAMONDS, 0);
    this.treasuresCollected.put(Treasure.RUBIES, 0);
    this.treasuresCollected.put(Treasure.SAPPHIRE, 0);
  }
  
  @Override
  public void pickTreasures(List<Treasure> treasuresDesired) {
    if (!this.alive) {
      throw new IllegalStateException("Player is dead, treasures "
          + "cannot be collected.");
    }
    Map<Treasure, Integer> availableTreasure = new HashMap<>();
    try {
      availableTreasure = this.currentLocation.collectTreasures(treasuresDesired);
    } catch (IllegalArgumentException e) {
      // do nothing
    }
    if (availableTreasure.size() > 0) {
      for (Map.Entry<Treasure, Integer> each : availableTreasure.entrySet()) {
        this.treasuresCollected.merge(each.getKey(), each.getValue(), Integer::sum);
      }
    }
  }

  @Override
  public String getPlayerDescription() {
    Set<Integer> values = new HashSet<Integer>(this.treasuresCollected.values());
    if (!this.alive) {
      return "Oops.. " + this.playerId + " is dead!!";
    }
    if (this.treasuresCollected.size() == 0 
        || (values.size() == 1 && values.contains(0))) {
      return this.playerId + " has not collected any treasure yet.\n" 
          + "The player is equipped with " + this.arrowsInHand + " arrows.";
    }
    return this.playerId + " has lifetime collection of: " + this.treasuresCollected 
        + "\n" + this.playerId + " is equipped with " + this.arrowsInHand + " arrows.";
  }

  @Override
  public void setCurrentLocation(Location newLocation) {
    if (!this.alive) {
      throw new IllegalStateException("Player is dead, "
          + "cannot be moved.");
    }
    this.currentLocation = newLocation;
  }

  @Override
  public String getCurrentLocationDetails() {
    Location playerLoc = this.currentLocation;
        
    return this.playerId + " is currently residing in " 
        + playerLoc.getLocationId() 
        + "." + this.checkForSmell() + "\n"
        + this.currentLocation.getPresentTreasures() + "\n"
        + "You find " + this.currentLocation.getArrowsInLocation() 
        + " arrows here.\n"
        + "The player can move to the following neighbors: \n" 
        + this.currentLocation.getLocationDetails();
  }

  @Override
  public String getPlayerId() {
    return this.playerId;
  }

  @Override
  public Location getCurrentLocation() {
    return this.currentLocation;
  }

  @Override
  public int getArrowsInHand() {
    return this.arrowsInHand;
  }

  @Override
  public void pickupArrows() 
      throws IllegalStateException {
    if (!this.alive) {
      throw new IllegalStateException("Player is dead, arrows "
          + "cannot be collected.");
    }
    int arrowsInLocation;
    arrowsInLocation = this.currentLocation.collectArrows();
    if (arrowsInLocation < 1) {
      throw new IllegalStateException("The caves must have greater than "
          + "0 arrows to pick up.");
    }
    this.arrowsInHand += arrowsInLocation;
  }

  @Override
  public void useArrow() throws IllegalStateException {
    if (!this.alive) {
      throw new IllegalStateException("Player is dead, cannot attack.");
    }
    if (this.arrowsInHand  == 0) {
      throw new IllegalStateException("The player does not have any arrows to use.");
    }
    this.arrowsInHand -= 1;
  }

  @Override
  public void killPlayer() throws IllegalStateException {
    if (!this.alive) {
      throw new IllegalStateException("Player is already dead.");
    }
    this.alive = false;
  }

  @Override
  public boolean isAlive() {
    return this.alive;
  }
  
  /**
   * Get the pungent smell level due to monsters from the current location.
   * 
   * @param currentPosition player location
   * @return pungent smell level
   */
  private String checkForSmell() {
    String smell;
    List<Monsters> levelOne = new ArrayList<>();
    List<Monsters> levelTwo = new ArrayList<>();
    
    List<Location> visited = new ArrayList<>();
    if (this.currentLocation.getMonster() == null) {
      visited.add(this.currentLocation);
      // Check for level one neighbors i.e. neighbors one position away
      for (Location firstNeighbor : this.currentLocation.getNeighbors().values()) {
        if (visited.contains(firstNeighbor)) {
          continue;
        } else {
          visited.add(firstNeighbor);
          if (firstNeighbor.getMonster() == null) {
            // Check for level two neighbors i.e. neighbors two position away
            for (Location secondNeighbor : firstNeighbor.getNeighbors().values()) {
              if (visited.contains(secondNeighbor)) {
                continue;
              } else {
                visited.add(secondNeighbor);
                if (secondNeighbor.getMonster() == null) {
                  continue;
                } else {
                  levelTwo.add(secondNeighbor.getMonster());
                }
              }
            }
          } else {
            levelOne.add(firstNeighbor.getMonster());
          }
        }
      }
      smell = this.estimateSmell(levelOne, levelTwo);
    } else {
      smell = "\nArrghhh..Monster in cave!!!";
    }
    return smell;
  }
  
  private String estimateSmell(List<Monsters> close, List<Monsters> next) {
    if (close.size() > 0) {
      return "\nThere's something smelling terrible nearby..";
    } else if (next.size() == 1) {
      return "\nIt's slightly smelly here..";
    } else if (next.size() > 1) {
      return "\nThere's something smelling terrible nearby..";
    } else {
      return "";
    }
  }

  @Override
  public Map<Treasure, Integer> getTreasuresInBag() {
    return this.treasuresCollected;
  }
}
