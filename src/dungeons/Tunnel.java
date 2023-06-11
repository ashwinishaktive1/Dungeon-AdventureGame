package dungeons;

import java.util.List;
import java.util.Map;

/**
 * A tunnel extends the AbstractLocation class. A tunnel cannot house any 
 * treasure and must have only 2 neighboring connections/entrances.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class Tunnel extends AbstractLocation {
  
  private String locationId;
  
  /**
   * Construct a tunnel.
   * 
   * @param id location identifier
   * @param r 2D grid position row number
   * @param c 2D grid position column number
   */
  public Tunnel(int id, int r, int c) {
    super(id, r, c);
    this.locationId = "Tunnel " + id;
  }
  
  @Override
  public String getLocationId() {
    return this.locationId;
  }
  
  @Override
  public void distributeTreasures(Map<Treasure, Integer> allotedTreasure) 
      throws IllegalStateException {
    throw new IllegalStateException("Treasures cannot be assigned "
        + "to a tunnel.");
  }

  @Override
  public Map<Treasure, Integer> collectTreasures(List<Treasure> desiredTreasure) 
      throws IllegalArgumentException {
    throw new IllegalArgumentException("No treasures in the tunnel.");
  }

  @Override
  public String getPresentTreasures() {
    return "The tunnel does not have any treasure to collect!";
  }

  @Override
  public int getLocationNum() {
    return Integer.parseInt(this.getLocationId().substring(7));
  }

  @Override
  public void addMonster(Monsters oty) throws IllegalStateException {
    throw new IllegalStateException("Monsters cannot live in "
        + "a tunnel.");
  }

  @Override
  public Monsters getMonster() {
    return null;
  }

  @Override
  public void removeMonster(Monsters oty) throws IllegalArgumentException {
    throw new IllegalArgumentException("Monsters are not living in "
        + "the tunnel.");
  }
}
