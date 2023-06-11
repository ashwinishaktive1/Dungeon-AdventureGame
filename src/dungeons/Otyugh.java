package dungeons;

/**
 * Otyughs are extremely smelly creatures that lead solitary lives 
 * in the deep, dark places of the world like our dungeon. They are 
 * adapted to eat whatever organic material that they can find, 
 * but love it when fresh meat happens into the cave in which they dwell.
 * It takes 2 hits to kill an Otyugh. 
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class Otyugh implements Monsters {
  
  private String otyughId;
  private int health;
  private boolean monsterInCave;
  
  /**
   * Construct an otyugh for the dungeon. 
   * 
   * @param id otyugh/monster identifier
   */
  public Otyugh(int id) {
    if (id < 0) {
      throw new IllegalArgumentException("Otyugh identifier must "
          + "be a number greater than 0.");
    }
    this.otyughId = "Otyugh " + id;
    this.setInitialHealth();
    this.monsterInCave = false;
  }
  
  /**
   * Set the initial health to 100. Each hit deducts 50 points from 
   * the health.
   */
  private void setInitialHealth() {
    this.health = 100;
  }

  @Override
  public String getMonsterId() {
    return this.otyughId;
  }

  @Override
  public void hit() {
    this.health -= 50;
  }

  @Override
  public String getStatus() {
    if (this.health == 100) {
      return this.otyughId + " is standing strong.";
    } else if (this.health == 50) {
      return this.otyughId + " is weak, hit again and it dies!";
    }
    return this.otyughId + " is dead!!";
  }

  @Override
  public void setMonsterResidingStatus() {
    this.monsterInCave = true;
  }

  @Override
  public boolean getMonsterResidingStatus() {
    return this.monsterInCave;
  }
}
