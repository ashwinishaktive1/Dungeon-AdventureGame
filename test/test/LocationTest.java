package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import dungeons.Cave;
import dungeons.Direction;
import dungeons.Location;
import dungeons.Monsters;
import dungeons.Otyugh;
import dungeons.Player;
import dungeons.Players;
import dungeons.Treasure;
import dungeons.Tunnel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test case for Location. 
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class LocationTest {
  Location common;
  Location caveOne;
  Location tunnelOne;
  Monsters oty;
  
  /**
   * Setting up test cases of caves and tunnels.
   */
  @Before
  public void setup() {
    caveOne = new Cave(10, 5, 6);
    tunnelOne = new Tunnel(3, 2, 3);
    oty = new Otyugh(23);
  }
  
  /**
   * Test getting location id for caves and tunnels.
   */
  @Test
  public void testGetLocationId() {
    assertEquals("Cave 10", caveOne.getLocationId());
    assertEquals("Tunnel 3", tunnelOne.getLocationId());
  }
  
  /**
   * Testing the constructor for location.
   */
  @Test
  public void testLocationConstructor() {
    common = new Cave(1, 1, 1);
    int[] expected = {1, 1};
    assertArrayEquals(expected, common.getPosition());
    
    common = new Tunnel(1, 3, 2);
    expected = new int[] {3, 2};
    assertArrayEquals(expected, common.getPosition());
  }
  
  /**
   * Testing creation of a location with wrong id should 
   * throw argument exception.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testLocationWrongId() {
    common = new Cave(-1, 1, 1);
  }
  
  /**
   * Testing creation of a location with wrong row should 
   * throw argument exception.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testLocationOutOfBoundRow() {
    common = new Cave(3, -1, 1);
  }
  
  /**
   * Testing creation of a location with wrong column should 
   * throw argument exception.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testLocationOutOfBoundCol() {
    common = new Cave(6, 1, -1);
  }
  
  /**
   * Testing if the positions are correctly assigned 
   * to the locations. 
   */
  @Test
  public void testGetPosition() {
    int[] expected = {5, 6};
    assertArrayEquals(expected, caveOne.getPosition());
    
    expected = new int[] {2, 3};
    assertArrayEquals(expected, tunnelOne.getPosition());
  }
  
  /**
   * Testing if null player can be added to the cave.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddNullPlayerCave() {
    caveOne.addPlayer(null);
  }
  
  /**
   * Testing if null player can be added to the tunnel.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddNullPlayerTunnel() {
    tunnelOne.addPlayer(null);
  }
  
  /**
   * Testing if player is correctly added to the location.
   */
  @Test
  public void testAddAndGetPlayer() {
    Players p = new Player(3);
    caveOne.addPlayer(p);
    
    List<Players> expected = new ArrayList<>();
    expected.add(p);
    
    assertTrue(expected.equals(caveOne.getPlayersPresent()));
    
    tunnelOne.addPlayer(p);
    
    assertTrue(expected.equals(tunnelOne.getPlayersPresent()));
  }
  
  /**
   * Test if removing player from an empty location throws state exception.
   */
  @Test (expected = IllegalStateException.class)
  public void testRemovePlayerEmptyLocation() {
    Players p = new Player(3);
    caveOne.removePlayer(p);
  }
  
  /**
   * Testing removal of player from a location, useful while 
   * making the move.
   */
  @Test
  public void testRemovePlayer() {
    Players p = new Player(3);
    caveOne.addPlayer(p);
    
    List<Players> expected = new ArrayList<>();
    expected.add(p);
    
    assertTrue(expected.equals(caveOne.getPlayersPresent()));
    
    caveOne.removePlayer(p);
    expected.remove(p);
    
    assertTrue(expected.equals(caveOne.getPlayersPresent()));
  }
  
  /**
   * Testing with addition and removal of multiple players from the location.
   */
  @Test
  public void testMultiplePlayerMoveThroughLocation() {
    Players p = new Player(3);
    Players p2 = new Player(7);
    caveOne.addPlayer(p);
    caveOne.addPlayer(p2);
    
    List<Players> expected = new ArrayList<>();
    expected.add(p);
    expected.add(p2);
    
    assertTrue(expected.equals(caveOne.getPlayersPresent()));
    
    caveOne.removePlayer(p2);
    expected.remove(p2);
    
    assertTrue(expected.equals(caveOne.getPlayersPresent()));
    
    List<Players> tunnelExpected = new ArrayList<>();
    tunnelExpected.add(p2);
    tunnelOne.addPlayer(p2);
    assertTrue(tunnelExpected.equals(tunnelOne.getPlayersPresent()));
  }
  
  /**
   * Test distribution of treasures to tunnels should 
   * throw an exception.
   */
  @Test (expected = IllegalStateException.class)
  public void testDistributeTreasuresTunnel() {
    Map<Treasure, Integer> test = new HashMap<>();
    tunnelOne.distributeTreasures(test);
  }
  
  /**
   * Test collection of treasures from tunnels should 
   * throw an exception.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testCollectTreasuresTunnel() {
    List<Treasure> test = new ArrayList<>();
    test.add(Treasure.SAPPHIRE);
    tunnelOne.collectTreasures(test);
  }
  
  /**
   * Test distribution of treasures to caves and collection 
   * of treasures.
   */
  @Test 
  public void testDistributeAndCollectTreasuresCave() {
    Map<Treasure, Integer> test = new HashMap<>();
    test.put(Treasure.DIAMONDS, 2);
    test.put(Treasure.RUBIES, 1);
    test.put(Treasure.SAPPHIRE, 1);
    caveOne.distributeTreasures(test);
    
    String presentExpected = "{SAPPHIRE=1, DIAMONDS=2, RUBIES=1} are present in the cave!!";
    assertEquals(presentExpected, caveOne.getPresentTreasures());
    
    List<Treasure> testTreasure = new ArrayList<>();
    testTreasure.add(Treasure.SAPPHIRE);
    
    Map<Treasure, Integer> expected = new HashMap<>();
    expected.put(Treasure.SAPPHIRE, 1);
    assertEquals(expected, caveOne.collectTreasures(testTreasure));
    
    presentExpected = "{DIAMONDS=2, RUBIES=1} are present in the cave!!";
    assertEquals(presentExpected, caveOne.getPresentTreasures());
  }
  
  /**
   * Test collection of treasures from empty cave should 
   * throw an exception.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testCollectTreasuresEmptyCave() {
    List<Treasure> test = new ArrayList<>();
    test.add(Treasure.SAPPHIRE);
    caveOne.collectTreasures(test);
  }
  
  /**
   * Test adding a different location in same direction.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddExistingDirection() {
    Location testCaveOne = new Cave(2, 3, 2);
    Location testCaveTwo = new Cave(6, 3, 2);
    caveOne.addNeighbor(Direction.NORTH, testCaveOne);
    caveOne.addNeighbor(Direction.NORTH, testCaveTwo);
  }
  
  /**
   * Test adding same location in multiple directions.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddExistingNeighbor() {
    Location testCaveOne = new Cave(2, 3, 2);
    caveOne.addNeighbor(Direction.NORTH, testCaveOne);
    caveOne.addNeighbor(Direction.EAST, testCaveOne);
  }
  
  /**
   * Test adding own self as neighbor.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddOwnAsNeighbor() {
    caveOne.addNeighbor(Direction.NORTH, caveOne);
  }
  
  /**
   * Test if more than 4 neighbors can be added to any 
   * location.
   */
  @Test (expected = IllegalStateException.class)
  public void testAddMoreThanMaxNeighbors() {
    Location testCaveOne = new Cave(2, 4, 6);
    Location testCaveTwo = new Cave(5, 5, 5);
    Location testCaveThree = new Cave(9, 5, 7);
    Location testCaveFour = new Cave(11, 6, 6);
    
    caveOne.addNeighbor(Direction.NORTH, testCaveOne);
    caveOne.addNeighbor(Direction.WEST, testCaveTwo);
    caveOne.addNeighbor(Direction.EAST, testCaveThree);
    caveOne.addNeighbor(Direction.SOUTH, testCaveFour);
    
    Location testCave = new Cave(1, 4, 7);
    caveOne.addNeighbor(Direction.SOUTH, testCave);
  }
  
  /**
   * Test if neighbors are returned correctly.
   */
  @Test
  public void testGetNeighbors() {
    Location testCaveOne = new Cave(2, 4, 6);
    Location testCaveTwo = new Cave(5, 5, 5);
    Location testCaveThree = new Cave(9, 5, 7);
    Location testCaveFour = new Cave(11, 6, 6);
    
    caveOne.addNeighbor(Direction.NORTH, testCaveOne);
    caveOne.addNeighbor(Direction.WEST, testCaveTwo);
    caveOne.addNeighbor(Direction.EAST, testCaveThree);
    caveOne.addNeighbor(Direction.SOUTH, testCaveFour);
    
    Map<Direction, Location> expected = new HashMap<>();
    expected.put(Direction.NORTH, testCaveOne);
    expected.put(Direction.WEST, testCaveTwo);
    expected.put(Direction.EAST, testCaveThree);
    expected.put(Direction.SOUTH, testCaveFour);
    
    assertTrue(expected.equals(caveOne.getNeighbors()));
  }
  
  /**
   * Testing if the list of treasures in the cave 
   * is correctly returned.
   */
  @Test
  public void testGetPresentTreasures() {
    String expected = "The tunnel does not have any treasure to collect!";
    assertEquals(expected, tunnelOne.getPresentTreasures());
    
    expected = "The cave does not have any treasures!";
    assertEquals(expected, caveOne.getPresentTreasures());
    
    Map<Treasure, Integer> test = new HashMap<>();
    test.put(Treasure.DIAMONDS, 2);
    test.put(Treasure.RUBIES, 1);
    test.put(Treasure.SAPPHIRE, 0);
    caveOne.distributeTreasures(test);
    
    expected = "{SAPPHIRE=0, DIAMONDS=2, RUBIES=1} are present in the cave!!";
    assertEquals(expected, caveOne.getPresentTreasures());
  }
  
  /**
   * Testing if the location identifier number is returned 
   * correctly.
   */
  @Test
  public void testGetLocationNum() {
    assertEquals(10, caveOne.getLocationNum());
    assertEquals(3, tunnelOne.getLocationNum());
  }
  
  /**
   * Testing if the number of arrows in a newly created location is 0.
   */
  @Test
  public void testArrowsInNewLocation() {
    assertEquals(0, caveOne.collectArrows());
    assertEquals(0, tunnelOne.collectArrows());
  }
  
  /**
   * Test to ensure if the arrows are correctly added to the location.
   */
  @Test
  public void testAddArrows() {
    assertEquals(0, caveOne.getArrowsInLocation());
    assertEquals(0, tunnelOne.getArrowsInLocation());
    
    caveOne.addArrows(3);
    tunnelOne.addArrows(2);
    
    assertEquals(3, caveOne.getArrowsInLocation());
    assertEquals(2, tunnelOne.getArrowsInLocation());
    
    caveOne.addArrows(1);
    tunnelOne.addArrows(2);
    
    assertEquals(4, caveOne.getArrowsInLocation());
    assertEquals(4, tunnelOne.getArrowsInLocation());
  }
  
  /**
   * Testing if all arrows are collected when required by the player.
   */
  @Test
  public void testCollectArrows() {
    caveOne.addArrows(3);
    tunnelOne.addArrows(5);
    
    assertEquals(3, caveOne.collectArrows());
    assertEquals(5, tunnelOne.collectArrows());
  }
  
  /**
   * Testing to check if a monster can be added to the tunnel.
   */
  @Test (expected = IllegalStateException.class)
  public void testAddMonsterTunnel() {
    tunnelOne.addMonster(oty);
  }
  
  /**
   * Testing if there's a monster in the tunnel.
   */
  @Test
  public void testGetMonsterTunnel() {
    assertNull(tunnelOne.getMonster());
  }
  
  /**
   * Testing if monster is added to the cave.
   */
  @Test
  public void testAddMonsterCave() {
    assertNull(caveOne.getMonster());
    
    caveOne.addMonster(oty);
    
    assertEquals(oty, caveOne.getMonster());
  }
  
  /**
   * Testing adding more than one monsters to the cave.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddMultipleMonsters() {
    caveOne.addMonster(oty);
    
    Monsters test = new Otyugh(111);
    
    caveOne.addMonster(test);
  }
  
  /**
   * Test if player can be moved after being killed.
   */
  @Test (expected = IllegalStateException.class)
  public void testAddPlayerAfterBeingKilled() {
    Players p = new Player(4);
    p.killPlayer();
    caveOne.addPlayer(p);
  }
}
