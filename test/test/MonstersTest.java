package test;

import static org.junit.Assert.assertEquals;

import dungeons.Monsters;
import dungeons.Otyugh;
import org.junit.Before;
import org.junit.Test;

/**
 * A Junit test class for testing the Monsters interface.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class MonstersTest {
  
  Monsters terror;
  
  /**
   * Setting up the monster.
   */
  @Before
  public void setup() {
    terror = new Otyugh(25);
  }
  
  /**
   * Testing construction of monster and getting monster identifier.
   */
  @Test
  public void testOtyughConstructor() {
    terror = new Otyugh(82993);
    assertEquals("Otyugh 82993", terror.getMonsterId());
  }
  
  /**
   * Testing the monster health when the game starts. 
   */
  @Test
  public void testGetStatus() {
    String expected = "Otyugh 25 is standing strong.";
    assertEquals(expected, terror.getStatus());
  }
  
  /**
   * Testing if the Otyugh health deteriorates upon hitting.
   */
  @Test
  public void testHit() {
    terror.hit();
    String expected = "Otyugh 25 is weak, hit again and it dies!";
    assertEquals(expected, terror.getStatus());
    
    terror.hit();
    expected = "Otyugh 25 is dead!!";
    assertEquals(expected, terror.getStatus());
  }
}
