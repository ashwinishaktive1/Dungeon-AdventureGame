package dungeons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is an abstract class representing the common features 
 * amongst the various types of locations. It contains the common 
 * methods between the extending child Location classes.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public abstract class AbstractLocation implements Location {
  
  protected int row;
  protected int col;
  private List<Players> dungeonPlayer;
  private Map<Direction, Location> neighbors;
  private int arrowsInLocation;
  
  /**
   * Construct an abstract location.
   * 
   * @param id location identifier
   * @param r 2D grid position row number
   * @param c 2D grid position column number
   */
  public AbstractLocation(int id, int r, int c) {
    if (id < 0) {
      throw new IllegalArgumentException("Location identifier must be "
          + "greater than 0.");
    }
    if (r < 0) {
      throw new IllegalArgumentException("The row number must be "
          + "greater than or equal to 0.");
    }
    if (c < 0) {
      throw new IllegalArgumentException("The column number must be "
          + "greater than or equal to 0.");
    }
    
    this.row = r;
    this.col = c;
    this.dungeonPlayer = new ArrayList<>();
    this.neighbors = new HashMap<>();
    this.arrowsInLocation = 0;
  }

  @Override
  public int[] getPosition() {
    int[] position = {this.row, this.col};
    return position;
  }
  
  @Override
  public String getLocationDetails() {
    Map<Direction, Location> neighborsMap = this.neighbors;
    String neighborsDetails = "";
    List<Direction> neighDirections = new ArrayList<>(neighborsMap.keySet());
    for (Direction dir : neighDirections) {
      String direction = dir.name();
      String neighborLoc = neighborsMap.get(dir).getLocationId();
      neighborsDetails += direction + ": " + neighborLoc + "\n";
    }
    return neighborsDetails.trim();
  } 
  
  @Override
  public void addNeighbor(Direction neighborDirection, 
      Location neighbor) throws IllegalArgumentException, 
      IllegalStateException {
    if (this.neighbors.size() == 4) {
      throw new IllegalStateException("No more neighbors can be "
          + "added to the location. A location can have 4 neighbors at "
          + "maximum.");
    }
    if (this.equals(neighbor)) {
      throw new IllegalArgumentException("Location cannot be own's "
          + "neighbor.");
    }
    if (this.neighbors.containsKey(neighborDirection)) {
      throw new IllegalArgumentException("Other neighbor already "
          + "present in given direction.");
    }
    if (this.neighbors.containsValue(neighbor)) {
      throw new IllegalArgumentException("Neighbor already "
          + "added.");
    }
    this.neighbors.put(neighborDirection, neighbor);
  }
  
  @Override
  public Map<Direction, Location> getNeighbors() {
    return this.neighbors;
  }

  @Override
  public List<Players> getPlayersPresent() {
    return this.dungeonPlayer;
  }
  
  @Override
  public void addPlayer(Players p) throws IllegalArgumentException {
    if (p == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    this.dungeonPlayer.add(p);
    p.setCurrentLocation(this);
  }
  
  @Override
  public void removePlayer(Players p) throws IllegalStateException, IllegalArgumentException {
    if (this.dungeonPlayer.size() == 0) {
      throw new IllegalStateException("No player is present in the location.");
    }
    if (p == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (!this.dungeonPlayer.contains(p)) {
      throw new IllegalArgumentException(p.getPlayerId() + " not present in the location."); 
    }
    
    this.dungeonPlayer.remove(p);    
  }
  
  @Override
  public void addArrows(int arrowsAssigned) {
    this.arrowsInLocation += arrowsAssigned;
  }

  @Override
  public int collectArrows() {
    int arrows;
    arrows = this.arrowsInLocation;
    this.arrowsInLocation = 0;
    return arrows;
  }

  @Override
  public int getArrowsInLocation() {
    return this.arrowsInLocation;
  }
}
