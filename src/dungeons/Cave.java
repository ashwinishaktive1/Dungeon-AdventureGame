package dungeons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A cave extends the AbstractLocation class. A cave can house treasures and 
 * has 1, 3, or 4 neighboring connections/entrances. 
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class Cave extends AbstractLocation {
  
  private String locationId;
  private Map<Treasure, Integer> treasureInCave;
  private Monsters monsterInCave;
  
  /**
   * Construct a cave.
   * 
   * @param id location identifier
   * @param r 2D grid position row number
   * @param c 2D grid position column number
   */
  public Cave(int id, int r, int c) {
    super(id, r, c);
    this.locationId = "Cave " + id;
    this.treasureInCave = new HashMap<>();
    this.setupTreasures();
    this.monsterInCave = null;
  }

  @Override
  public String getLocationId() {
    return this.locationId;
  }
  
  @Override
  public void distributeTreasures(Map<Treasure, Integer> allotedTreasure) {
    this.treasureInCave = allotedTreasure;
  }

  @Override
  public Map<Treasure, Integer> collectTreasures(List<Treasure> desiredTreasure) 
      throws IllegalArgumentException {
    Set<Integer> values = new HashSet<Integer>(this.treasureInCave.values());
    if (values.size() == 1 && values.contains(0)) {
      throw new IllegalArgumentException("No treasures present in cave."); 
    }
    
    Map<Treasure, Integer> collection = new HashMap<>();
    for (Map.Entry<Treasure, Integer> treasure : this.treasureInCave.entrySet()) {
      if (desiredTreasure.contains(treasure.getKey())) {
        collection.put(treasure.getKey(), treasure.getValue());
      }
    }
    
    for (Treasure treasure : desiredTreasure) {
      this.treasureInCave.remove(treasure);
    }

    return collection;
  }

  @Override
  public String getPresentTreasures() {
    Set<Integer> values = new HashSet<Integer>(this.treasureInCave.values());
    if (values.size() == 1 && values.contains(0)) {
      return "The cave does not have any treasures!";
    }
    return this.treasureInCave + " are present in the cave!!";
  }

  @Override
  public int getLocationNum() {
    return Integer.parseInt(this.getLocationId().substring(5));
  } 
  
  /**
   * Create an empty treasure Map with the treasures and a 0 count.
   */
  private void setupTreasures() {
    this.treasureInCave.put(Treasure.DIAMONDS, 0);
    this.treasureInCave.put(Treasure.RUBIES, 0);
    this.treasureInCave.put(Treasure.SAPPHIRE, 0);
  }

  @Override
  public void addMonster(Monsters oty) throws IllegalArgumentException {
    if (oty == null) {
      throw new IllegalArgumentException("Monster must be of valid type."); 
    }
    if (this.monsterInCave != null) {
      throw new IllegalArgumentException("Cave pre-occupied by a monster, no more "
          + "monsters can be added."); 
    }
    if (oty.getMonsterResidingStatus()) {
      throw new IllegalArgumentException("Monster cannot be moved from one "
          + "location to another.");
    }
    this.monsterInCave = oty;
    oty.setMonsterResidingStatus();
  }

  @Override
  public Monsters getMonster() {
    return this.monsterInCave;
  }

  @Override
  public void removeMonster(Monsters oty) throws IllegalArgumentException {
    if (this.monsterInCave != oty) {
      throw new IllegalArgumentException("This monster is not present in cave.");
    }
    this.monsterInCave = null;
  }
}
