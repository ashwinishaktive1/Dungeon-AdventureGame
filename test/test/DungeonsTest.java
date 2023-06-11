package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import dungeons.Direction;
import dungeons.Dungeons;
import dungeons.Location;
import dungeons.MazeDungeon;
import dungeons.Player;
import dungeons.Players;
import dungeons.RandomNumberGenerator;
import dungeons.RandomNumberGeneratorTest;
import dungeons.Treasure;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test case for Dungeons Interface and MazeDungeons class.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class DungeonsTest {
  
  RandomNumberGenerator rand;
  Dungeons wrappedDungeon;
  Dungeons unwrappedDungeon;
  
  /**
   * Setting up dungeons.
   */
  @Before
  public void setup() {
    rand = new RandomNumberGeneratorTest(0, 2, 3, 5, 6, 8);
    wrappedDungeon = new MazeDungeon(5, 5, 4, true, 30, 2, rand);
    unwrappedDungeon = new MazeDungeon(5, 5, 4, false, 30, 2, rand);
  }
  
  /**
   * Test for set data structure features.
   */
  @Test
  public void testSets() {
    Set<Set<Integer>> test = new HashSet<>();
    
    test.add(new HashSet<>(Arrays.asList(1, 2)));
    test.add(new HashSet<>(Arrays.asList(3, 4)));
    test.add(new HashSet<>(Arrays.asList(2, 3)));
    test.add(new HashSet<>(Arrays.asList(2, 1)));
    
    assertEquals("[[1, 2], [2, 3], [3, 4]]", test.toString());
    
    Set<Integer> testTwo = new HashSet<>();
    testTwo.add(1);
    testTwo.add(2);
    
    assertEquals(1, testTwo.toArray()[0]);
    
    testTwo.remove(1);
    
    assertEquals(2, testTwo.toArray()[0]);
  }
  
  /**
   * Testing MazeDungeon construction by checking the layout.
   */
  @Test
  public void testMazeDungeonConstructor() {
    rand = new RandomNumberGeneratorTest(0, 2, 3, 5, 6, 8);
    Dungeons testDug = new MazeDungeon(5, 4, 5, true, 20.1, 2, rand);
    String expected = "0, 1, 2, 3, \n"
        + "4, 5, 6, 7, \n"
        + "8, 9, 10, 11, \n"
        + "12, 13, 14, 15, \n"
        + "16, 17, 18, 19, \n";
    assertEquals(expected, testDug.printDungeonLayout());
  }
  
  /**
   * Testing invalid row count.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRows() {
    wrappedDungeon = new MazeDungeon(-1, 5, 3, true, 40.5, 2, rand);
  }
  
  /**
   * Testing invalid column count.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidColumns() {
    wrappedDungeon = new MazeDungeon(5, 0, 3, true, 40.5, 2, rand);
  }
  
  /**
   * Testing if negative interconnectivity is accepted.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidInterconnectivity() {
    wrappedDungeon = new MazeDungeon(5, 5, -1, true, 40.5, 2, rand);
  }
  
  /**
   * Testing a negative treasure spread.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testLessTreasureSpread() {
    wrappedDungeon = new MazeDungeon(5, 5, 3, true, -20.0, 2, rand);
  }
  
  /**
   * Testing a treasure spread more than 100%.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testMoreTreasureSpread() {
    wrappedDungeon = new MazeDungeon(5, 5, 3, true, 120.0, 2, rand);
  }
  
  /**
   * Testing a null random generator object.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNullRandom() {
    wrappedDungeon = new MazeDungeon(5, 5, 3, true, 20.0, 2, null);
  }
  
  /**
   * Testing if the wrapped maze is constructed correctly. The border 
   * rows and columns of the grid have wrapped connections.
   */
  @Test
  public void testMazeWrapping() {
    rand = new RandomNumberGeneratorTest(0, 8, 3, 5, 6, 2);
    wrappedDungeon = new MazeDungeon(3, 3, 0, true, 30, 2, rand);
    
    List<List<Integer>> adjacency = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      List<Integer> adjacencyRow = new ArrayList<>();
      for (int j = 0; j < 3; j++) {
        Location curr = wrappedDungeon.getAllLocations().get(i).get(j);
        Map<Direction, Location> neighbors = curr.getNeighbors();
        adjacencyRow.add(neighbors.values().size());
      }
      adjacency.add(adjacencyRow);
    }
    
    List<List<Integer>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList(4, 3, 3)));
    expected.add(new ArrayList<>(Arrays.asList(1, 1, 1)));
    expected.add(new ArrayList<>(Arrays.asList(1, 1, 1)));
    assertEquals(expected, adjacency);
  }
  
  /**
   * Testing if the unwrapped maze is constructed correctly. The border 
   * rows and columns of the grid have same row/column connections only.
   */
  @Test
  public void testMazeUnwrapping() {
    rand = new RandomNumberGeneratorTest(0, 8, 3, 5, 6, 2);
    unwrappedDungeon = new MazeDungeon(3, 3, 0, false, 30, 2, rand);
    
    List<List<Integer>> adjacency = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      List<Integer> adjacencyRow = new ArrayList<>();
      for (int j = 0; j < 3; j++) {
        Location curr = unwrappedDungeon.getAllLocations().get(i).get(j);
        Map<Direction, Location> neighbors = curr.getNeighbors();
        adjacencyRow.add(neighbors.values().size());
      }
      adjacency.add(adjacencyRow);
    }
      
    List<List<Integer>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList(2, 3, 2)));
    expected.add(new ArrayList<>(Arrays.asList(2, 2, 2)));
    expected.add(new ArrayList<>(Arrays.asList(1, 1, 1)));
    assertEquals(expected, adjacency);
  }
  
  @Test
  public void testZeroInterconnectivity() {
    // Wrapped dungeon
    wrappedDungeon = new MazeDungeon(5, 5, 0, true, 30, 2, rand);
    
    List<List<Location>> allLocations = wrappedDungeon.getAllLocations();
    
    Location start = allLocations.get(0).get(0);
    
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        List<List<Location>> paths = getAllPossiblePaths(start, 
            allLocations.get(i).get(j), wrappedDungeon);
        // Only one path
        assertTrue(paths.size() == 1);
      }
    }
    
    // Unwrapped dungeon
    unwrappedDungeon = new MazeDungeon(5, 5, 0, false, 30, 2, rand);
    
    allLocations = unwrappedDungeon.getAllLocations();
    
    start = allLocations.get(0).get(0);
    
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        List<List<Location>> paths = getAllPossiblePaths(start, 
            allLocations.get(i).get(j), unwrappedDungeon);
        // Only one path
        assertTrue(paths.size() == 1);
      }
    }
  }
   
  /**
   * Testing if the treasure is distributed to the required 
   * number of caves in a wrapped dungeon.
   */
  @Test
  public void testTreasureDistributionWrapped() {
    rand = new RandomNumberGeneratorTest(0, 2, 3, 5, 6, 8);
    wrappedDungeon = new MazeDungeon(5, 5, 4, true, 30, 2, rand);
    List<List<Location>> dungeonLocations = wrappedDungeon.getAllLocations();
    int count = 0;
    
    List<Treasure> allTreasures = new ArrayList<>();
    allTreasures.addAll(Arrays.asList(Treasure.DIAMONDS, Treasure.RUBIES, Treasure.SAPPHIRE));
        
    for (List<Location> locRow : dungeonLocations) {
      for (Location loc : locRow) {
        Map<Treasure, Integer> availableTreasures = new HashMap<>();
        
        List<Integer> randomPicks = rand.getUniqueRandomNumbersList(2, 0, 0);
        
        List<Treasure> treasuresToBePicked = new ArrayList<>();
        treasuresToBePicked.add(allTreasures.get(randomPicks.get(0)));
        treasuresToBePicked.add(allTreasures.get(randomPicks.get(1)));
        
        try {
          availableTreasures = loc.collectTreasures(treasuresToBePicked);
        } catch (IllegalArgumentException e) {
          // do nothing
        }
        if (availableTreasures.size() > 0) {
          count++;
        }
      }
    }
    assertTrue((double) count / wrappedDungeon.getCaves().size() >= 0.3);
  }
  
  /**
   * Testing if the treasure is distributed to the required 
   * number of caves in an unwrapped dungeon.
   */
  @Test
  public void testTreasureDistributionUnwrapped() {
    List<List<Location>> dungeonLocations = unwrappedDungeon.getAllLocations();
    int count = 0;
    
    List<Treasure> allTreasures = new ArrayList<>();
    allTreasures.addAll(Arrays.asList(Treasure.DIAMONDS, Treasure.RUBIES, Treasure.SAPPHIRE));
    
    for (List<Location> locRow : dungeonLocations) {
      for (Location loc : locRow) {
        Map<Treasure, Integer> availableTreasures = new HashMap<>();
        
        List<Integer> randomPicks = rand.getUniqueRandomNumbersList(2, 0, 0);
        
        List<Treasure> treasuresToBePicked = new ArrayList<>();
        treasuresToBePicked.add(allTreasures.get(randomPicks.get(0)));
        treasuresToBePicked.add(allTreasures.get(randomPicks.get(1)));
        
        try {
          availableTreasures = loc.collectTreasures(treasuresToBePicked);
        } catch (IllegalArgumentException e) {
          // do nothing
        }
        if (availableTreasures.size() > 0) {
          count++;
        }
      }
    }
    assertTrue((double) count / unwrappedDungeon.getCaves().size() >= 0.3);
  }
  
  /**
   * Test if the GetAllAdjacency() function correctly returns the 
   * neighbors for a wrapped dungeon.
   */
  @Test
  public void testGetAllAdjacencyWrapped() {
    rand = new RandomNumberGeneratorTest(0, 8, 3, 5, 6, 2);
    wrappedDungeon = new MazeDungeon(3, 3, 0, true, 30, 2, rand);
    
    List<List<Integer>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList(4, 3, 3)));
    expected.add(new ArrayList<>(Arrays.asList(1, 1, 1)));
    expected.add(new ArrayList<>(Arrays.asList(1, 1, 1)));
    
    List<List<List<Location>>> adjacency = wrappedDungeon.getAllAdjacency();
    
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals((int) expected.get(i).get(j), adjacency.get(i).get(j).size());
      }
    }
  }
  
  /**
   * Test if the GetAllAdjacency() function correctly returns the 
   * neighbors for an unwrapped dungeon.
   */
  @Test
  public void testGetAllAdjacencyUnwrapped() {
    rand = new RandomNumberGeneratorTest(0, 8, 3, 5, 6, 2);
    unwrappedDungeon = new MazeDungeon(3, 3, 0, false, 30, 2, rand);
    
    List<List<Integer>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList(2, 3, 2)));
    expected.add(new ArrayList<>(Arrays.asList(2, 2, 2)));
    expected.add(new ArrayList<>(Arrays.asList(1, 1, 1)));
    
    List<List<List<Location>>> adjacency = unwrappedDungeon.getAllAdjacency();
    
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals((int) expected.get(i).get(j), adjacency.get(i).get(j).size());
      }
    }
  }
  
  /**
   * Testing reachability for the first corner of 2-D grid. 
   * If at least one location is reachable to all other locations, we can 
   * conclude that every cave is reachable from every other cave. 
   */
  @Test
  public void testCaveReachability() {
    // Wrapped dungeon
    List<List<Location>> allLocations = wrappedDungeon.getAllLocations();
    
    Location start = allLocations.get(0).get(0);
    
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        List<List<Location>> paths = getAllPossiblePaths(start, 
            allLocations.get(i).get(j), wrappedDungeon);
        assertTrue(paths.size() > 0);
      }
    }
    
    // Unwrapped dungeon
    allLocations = unwrappedDungeon.getAllLocations();
    
    start = allLocations.get(0).get(0);
    
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        List<List<Location>> paths = getAllPossiblePaths(start, 
            allLocations.get(i).get(j), unwrappedDungeon);
        assertTrue(paths.size() > 0);
      }
    }
  }
  
  /**
   * Testing if the start and end caves are assigned correctly.
   */
  @Test
  public void testAssignStartAndEnd() {
    // Wrapped dungeon
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    wrappedDungeon = new MazeDungeon(5, 5, 4, true, 30, 2, rand);
    wrappedDungeon.assignStartAndEnd();
    assertEquals(0, wrappedDungeon.getStart().getLocationNum());
    assertEquals(16, wrappedDungeon.getEnd().getLocationNum());
    
    // Unwrapped dungeon
    rand = new RandomNumberGeneratorTest(0, 8, 2, 3, 5, 6);
    unwrappedDungeon = new MazeDungeon(5, 5, 4, false, 30, 2, rand);
    unwrappedDungeon.assignStartAndEnd();
    assertEquals(1, unwrappedDungeon.getStart().getLocationNum());
    assertEquals(23, unwrappedDungeon.getEnd().getLocationNum());
  }
  
  /**
   * Testing it the start and end locations are of type cave.
   */
  @Test
  public void testAssignedStartAndEndAreCaves() {
    // Wrapped dungeon
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    wrappedDungeon = new MazeDungeon(5, 5, 4, true, 30, 2, rand);
    wrappedDungeon.assignStartAndEnd();
    assertTrue(wrappedDungeon.getStart().getNeighbors().size() != 2);
    assertTrue(wrappedDungeon.getEnd().getNeighbors().size() != 2);
    
    // Unwrapped dungeon
    rand = new RandomNumberGeneratorTest(0, 8, 2, 3, 5, 6);
    unwrappedDungeon = new MazeDungeon(5, 5, 4, false, 30, 2, rand);
    unwrappedDungeon.assignStartAndEnd();
    assertTrue(unwrappedDungeon.getStart().getNeighbors().size() != 2);
    assertTrue(unwrappedDungeon.getEnd().getNeighbors().size() != 2);
  }
  
  /**
   * Tested if the the getStarts is correctly returned for 
   * current random test assignments.
   */
  @Test
  public void testGetStart() {
    // Wrapped dungeon
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    wrappedDungeon = new MazeDungeon(5, 5, 4, true, 30, 2, rand);
    assertNull(wrappedDungeon.getStart());
    wrappedDungeon.assignStartAndEnd();
    assertEquals(0, wrappedDungeon.getStart().getLocationNum());
    
    rand = new RandomNumberGeneratorTest(0, 11, 2, 3, 5, 6, 8);
    wrappedDungeon = new MazeDungeon(5, 5, 4, true, 30, 2, rand);
    assertNull(wrappedDungeon.getStart());
    wrappedDungeon.assignStartAndEnd();
    assertEquals(0, wrappedDungeon.getStart().getLocationNum());
    
    // Unwrapped dungeon
    rand = new RandomNumberGeneratorTest(0, 8, 2, 3, 5, 6);
    unwrappedDungeon = new MazeDungeon(5, 5, 4, false, 30, 2, rand);
    assertNull(unwrappedDungeon.getStart());
    unwrappedDungeon.assignStartAndEnd();
    assertEquals(1, unwrappedDungeon.getStart().getLocationNum());
    
    rand = new RandomNumberGeneratorTest(0, 7, 2, 3, 5, 6);
    unwrappedDungeon = new MazeDungeon(5, 5, 4, false, 30, 2, rand);
    assertNull(unwrappedDungeon.getStart());
    unwrappedDungeon.assignStartAndEnd();
    assertEquals(1, unwrappedDungeon.getStart().getLocationNum());
  }
  
  /**
   * Tested if the the getEnd is correctly returned for 
   * current random test assignments.
   */
  @Test
  public void testGetEnd() {
    // Wrapped dungeon
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    wrappedDungeon = new MazeDungeon(5, 5, 4, true, 30, 2, rand);
    assertNull(wrappedDungeon.getEnd());
    wrappedDungeon.assignStartAndEnd();
    assertEquals(16, wrappedDungeon.getEnd().getLocationNum());
    
    rand = new RandomNumberGeneratorTest(0, 11, 2, 3, 5, 6, 8);
    wrappedDungeon = new MazeDungeon(5, 5, 4, true, 30, 2, rand);
    assertNull(wrappedDungeon.getEnd());
    wrappedDungeon.assignStartAndEnd();
    assertEquals(17, wrappedDungeon.getEnd().getLocationNum());
    
    // Unwrapped dungeon
    rand = new RandomNumberGeneratorTest(0, 8, 2, 3, 5, 6);
    unwrappedDungeon = new MazeDungeon(5, 5, 4, false, 30, 2, rand);
    assertNull(unwrappedDungeon.getEnd());
    unwrappedDungeon.assignStartAndEnd();
    assertEquals(23, unwrappedDungeon.getEnd().getLocationNum());
    
    rand = new RandomNumberGeneratorTest(0, 7, 2, 3, 5, 6);
    unwrappedDungeon = new MazeDungeon(5, 5, 4, false, 30, 2, rand);
    assertNull(unwrappedDungeon.getEnd());
    unwrappedDungeon.assignStartAndEnd();
    assertEquals(22, unwrappedDungeon.getEnd().getLocationNum());
  }
  
  /**
   * Testing the distance between assigned start and end caves 
   * is always at least 5. 
   */
  @Test
  public void testPathDistance() {
    // Wrapped
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    wrappedDungeon = new MazeDungeon(5, 5, 4, true, 30, 2, rand);
    wrappedDungeon.assignStartAndEnd();
    
    List<List<Location>> allPaths = getAllPossiblePaths(wrappedDungeon.getStart(), 
        wrappedDungeon.getEnd(), wrappedDungeon);
    for (List<Location> path : allPaths) {
      assertTrue(path.size() >= 5);
    }
    
    // Unwrapped
    rand = new RandomNumberGeneratorTest(0, 8, 2, 3, 5, 6);
    unwrappedDungeon = new MazeDungeon(5, 5, 4, false, 30, 2, rand);
    unwrappedDungeon.assignStartAndEnd();
    
    allPaths = getAllPossiblePaths(unwrappedDungeon.getStart(), 
        unwrappedDungeon.getEnd(), unwrappedDungeon);
    for (List<Location> path : allPaths) {
      assertTrue(path.size() >= 5);
    }
  }
  
  /**
   * Testing if the start and end locations are reachable using any one 
   * simulated paths in a wrapped dungeon. The player moves through the 
   * neighbors starting from the start to reach the end.
   */
  @Test
  public void testMovingFromStartToEndWrapped() {
    // Dungeon to be tested, player located at the start
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    wrappedDungeon = new MazeDungeon(5, 5, 4, true, 30, 2, rand);
    
    // Creating a test dungeon to simulate path with same seed
    Dungeons test = new MazeDungeon(5, 5, 4, true, 30, 2, rand);
    test.assignStartAndEnd();
    
    Players player = new Player(100);
    wrappedDungeon.assignStartAndEnd();
    Location start = wrappedDungeon.getStart();
    Location end = wrappedDungeon.getEnd();
    start.addPlayer(player);
    
    // Confirming start and end are the same
    assertEquals(test.getStart().getLocationId(), start.getLocationId());
    assertEquals(test.getEnd().getLocationId(), end.getLocationId());
    
    // Creating a test path list
    List<List<Location>> allPaths = getAllPossiblePaths(test.getStart(), test.getEnd(), test);
    
    // Using any one generated path
    List<Location> path = allPaths.get(0);
    
    for (int i = 1; i < path.size(); i++) {
      // Getting next location from the test path
      Location next = path.get(i);
      Map<Direction, Location> currNeighbors = player.getCurrentLocation().getNeighbors();
      for (Entry<Direction, Location> entry : currNeighbors.entrySet()) {
        // Checking if the next location is a neighbor in our original dungeon
        if (entry.getValue().getLocationId().equals(next.getLocationId())) {
          // Moving to the next locations
          entry.getValue().addPlayer(player);
        }
      }
    }
    
    // Checking if the player reaches the end of the dungeon
    assertEquals(end.getLocationId(), player.getCurrentLocation().getLocationId());
  }
  
  /**
   * Testing if the start and end locations are reachable using any one 
   * simulated paths in a unwrapped dungeon. The player moves through the 
   * neighbors starting from the start to reach the end.
   */
  @Test
  public void testMovingFromStartToEndUnwrapped() {
    // Dungeon to be tested, player located at the start
    rand = new RandomNumberGeneratorTest(0, 8, 2, 3, 5, 6);
    unwrappedDungeon = new MazeDungeon(5, 5, 4, false, 30, 2, rand);
    
    // Creating a test dungeon to simulate path with same seed
    Dungeons test = new MazeDungeon(5, 5, 4, false, 30, 2, rand);
    test.assignStartAndEnd();
    
    Players player = new Player(100);
    unwrappedDungeon.assignStartAndEnd();
    Location start = unwrappedDungeon.getStart();
    Location end = unwrappedDungeon.getEnd();
    start.addPlayer(player);
        
    // Confirming start and end are the same
    assertEquals(test.getStart().getLocationId(), start.getLocationId());
    assertEquals(test.getEnd().getLocationId(), end.getLocationId());
    
    // Creating a test path list
    List<List<Location>> allPaths = getAllPossiblePaths(test.getStart(), test.getEnd(), test);
    
    // Using any one generated path
    List<Location> path = allPaths.get(0);
    
    for (int i = 1; i < path.size(); i++) {
      // Getting next location from the test path
      Location next = path.get(i);
      Map<Direction, Location> currNeighbors = player.getCurrentLocation().getNeighbors();
      for (Entry<Direction, Location> entry : currNeighbors.entrySet()) {
        // Checking if the next location is a neighbor in our original dungeon
        if (entry.getValue().getLocationId().equals(next.getLocationId())) {
          // Moving to the next locations
          entry.getValue().addPlayer(player);
        }
      }
    }
    
    // Checking if the player reaches the end of the dungeon
    assertEquals(end.getLocationId(), player.getCurrentLocation().getLocationId());
  }
  
  /**
   * Testing to ensure if the monster is added to the end cave.
   */
  @Test
  public void testMonsterInEndCave() {
    rand = new RandomNumberGeneratorTest(0, 8, 2, 3, 5, 6);
    unwrappedDungeon = new MazeDungeon(5, 5, 4, false, 30, 5, rand);
    unwrappedDungeon.assignStartAndEnd();
    assertEquals("Otyugh 1", unwrappedDungeon.getEnd().getMonster().getMonsterId());
  }
  
  /**
   * Testing to check if the requested number of monsters are 
   * present in the dungeon to satisfy the difficulty level.
   */
  @Test
  public void testMultipleMonstersInDungeon() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    wrappedDungeon = new MazeDungeon(5, 5, 4, true, 30, 5, rand);
    wrappedDungeon.assignStartAndEnd();
    assertEquals("Otyugh 1", wrappedDungeon.getEnd().getMonster().getMonsterId());
    
    List<List<Location>> allLocations = wrappedDungeon.getAllLocations();
    int count = 0;
    for (List<Location> row : allLocations) {
      for (Location col : row) {
        if (col.getMonster() != null) {
          count += 1;
        }
      }
    }
    assertEquals(5, count);
  }
  
  /**
   * Test if the arrows are added to the dungeon.
   */
  @Test
  public void testAddArrowsInDungeon() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    wrappedDungeon = new MazeDungeon(5, 5, 4, true, 30, 5, rand);
    
    // With the random numbers, all the arrows are assigned to the start 
    // location.
    
    assertTrue(wrappedDungeon.getAllLocations().get(0).get(0).collectArrows() > 0);
  }
  
  /**
   * Test to check if player can pick up arrows correctly.
   */
  @Test
  public void testPickUpArrows() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    wrappedDungeon = new MazeDungeon(5, 5, 4, true, 30, 5, rand);
    wrappedDungeon.assignStartAndEnd();
    Players player = new Player(77);
    wrappedDungeon.getStart().addPlayer(player);
    
    assertEquals(3, player.getArrowsInHand());
    
    player.pickupArrows();
    
    assertEquals(11, player.getArrowsInHand());
  }
  
  /**
   * Get all possible paths from point A to point B. Implemented a DFS algorithm.
   * 
   * @param start start location
   * @param end end location
   * @param dungeon current dungeon
   */
  private List<List<Location>> getAllPossiblePaths(
      Location startLoc, Location endLoc, Dungeons dungeon) {
    List<List<Location>> allPaths = new ArrayList<>();
    Boolean[] visited = new Boolean[(5 * 5) + 1];
    Arrays.fill(visited, Boolean.FALSE);
    List<Location> availablePath = new ArrayList<>();
    availablePath.add(startLoc);
    this.getAllPossiblePathsRecur(startLoc, endLoc, visited, availablePath, 
        allPaths, dungeon);
    
    return allPaths;
  }
  
  /**
   * Recursively find paths between start and end location using the DFS 
   * algorithm.
   * 
   * @param start start location
   * @param end end location
   * @param visited boolean array of not visits
   * @param availablePath identified paths between start and end
   * @param allPaths all paths in dungeon
   * @param dungeon current dungeon
   */
  private void getAllPossiblePathsRecur(Location startLoc, Location endLoc, 
      Boolean[] visited, List<Location> availablePath, List<List<Location>> allPaths, 
      Dungeons dungeon) {
    if (startLoc.equals(endLoc)) {
      List<Location> path = new ArrayList<>(availablePath);
      allPaths.add(path);
    } else {
      visited[startLoc.getLocationNum()] = true;
      
      int startRow = startLoc.getPosition()[0];
      int startCol = startLoc.getPosition()[1];
      
      for (Location neighbor : dungeon.getAllAdjacency().get(startRow).get(startCol)) {
        
        if (!visited[neighbor.getLocationNum()]) {
          availablePath.add(neighbor);
          this.getAllPossiblePathsRecur(neighbor, endLoc, visited, 
              availablePath, allPaths, dungeon);
          availablePath.remove(neighbor);
        }
      }
      visited[startLoc.getLocationNum()] = false;
    }
  }
}
