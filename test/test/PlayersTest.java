package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dungeons.Cave;
import dungeons.Direction;
import dungeons.Location;
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
 * A JUnit test case for Players. 
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class PlayersTest {
  Players player;
  Location cave;
  Location tunnel;
  
  /**
   * Setting up player test case of a player.
   */
  @Before
  public void setup() {
    player = new Player(100);
    cave = new Cave(11, 2, 4);
    cave.addArrows(3);
    tunnel = new Tunnel(5, 1, 4);
    tunnel.addArrows(1);
  }
  
  /**
   * Testing the constructor for player.
   */
  @Test
  public void testPlayersConstructor() {
    Players test = new Player(2);
    assertEquals("Player 2", test.getPlayerId());
  }
  
  /**
   * Testing setting current location of player to a random location. 
   * The player presence is not yet added in the details of the location.
   */
  @Test
  public void testSetCurrentLocation() {
    player.setCurrentLocation(cave);
    String expected = "Player 100 is currently residing in Cave 11.\n"
        + "The cave does not have any treasures!\n"
        + "You find 3 arrows here.\n"
        + "The player can move to the following neighbors: \n";
    assertEquals(expected, player.getCurrentLocationDetails());
    
    Location testCaveOne = new Cave(2, 4, 6);
    Location testCaveTwo = new Cave(5, 5, 5);
    
    cave.addNeighbor(Direction.NORTH, testCaveOne);
    cave.addNeighbor(Direction.WEST, testCaveTwo);
    expected += "WEST: Cave 5\nNORTH: Cave 2"; 
    assertEquals(expected, player.getCurrentLocationDetails());
  }
  
  /**
   * Testing moving/adding the player to a new location.
   */
  @Test
  public void testAddPlayerToLocation() {
    cave.addPlayer(player);
    List<Players> expected = new ArrayList<>();
    expected.add(player);
    
    assertTrue(expected.equals(cave.getPlayersPresent()));
    
    String playerStatusExpected = "Player 100 is currently residing in Cave 11.\n"
        + "The cave does not have any treasures!\n"
        + "You find 3 arrows here.\n"
        + "The player can move to the following neighbors: \n";
    assertEquals(playerStatusExpected, player.getCurrentLocationDetails());
  }
  
  /**
   * Testing picking up treasure from the current location. 
   */
  @Test
  public void testPickTreasures() {
    cave.addPlayer(player);
    List<Treasure> desired = new ArrayList<>();
    desired.add(Treasure.SAPPHIRE);
    player.pickTreasures(desired);
    
    String expected = "Player 100 has not collected any treasure yet.\nThe "
        + "player is equipped with 3 arrows.";
    assertEquals(expected, player.getPlayerDescription());
    
    Map<Treasure, Integer> test = new HashMap<>();
    test.put(Treasure.DIAMONDS, 2);
    test.put(Treasure.RUBIES, 1);
    test.put(Treasure.SAPPHIRE, 2);
    cave.distributeTreasures(test);
    cave.addPlayer(player);
    player.pickTreasures(desired);
    
    expected = "Player 100 has lifetime collection of: {DIAMONDS=0, RUBIES=0, SAPPHIRE=2}"
        + "\nPlayer 100 is equipped with 3 arrows.";
    assertEquals(expected, player.getPlayerDescription());
  }
  
  /**
   * Testing to ensure player has only 3 arrows when they start the game.
   */
  @Test
  public void testGetArrowsStartOfTheGame() {
    assertEquals(3, player.getArrowsInHand());
  }
  
  /**
   * Testing if the player collects and adds the arrow to their back-pack.
   */
  @Test
  public void testPickupArrows() {
    cave.addPlayer(player);
    player.pickupArrows();
    assertEquals(6, player.getArrowsInHand());
    
    tunnel.addPlayer(player);
    player.pickupArrows();
    assertEquals(7, player.getArrowsInHand());
  }
  
  /**
   * Testing picking up arrows from an empty cave.
   */
  @Test (expected = IllegalStateException.class)
  public void testPickUpFromEmptyCave() {
    cave.addPlayer(player);
    player.pickupArrows();
    player.pickupArrows();
  }
  
  /**
   * Testing picking up arrows from an empty tunnel.
   */
  @Test (expected = IllegalStateException.class)
  public void testPickUpFromEmptyTunnel() {
    tunnel.addPlayer(player);
    player.pickupArrows();
    player.pickupArrows();
  }
  
  /**
   * Testing to ensure usage of arrows reduces the count of arrows with 
   * the player.
   */
  @Test
  public void testUseArrow() {
    assertEquals(3, player.getArrowsInHand());
    player.useArrow();
    assertEquals(2, player.getArrowsInHand());
  }
  
  /**
   * Testing if usage of arrows after using all available arrows throws 
   * an illegal state exception.
   */
  @Test (expected = IllegalStateException.class)
  public void testUseArrowAfterUsingAll() {
    player.useArrow();
    player.useArrow();
    player.useArrow();
    player.useArrow();
  }
  
  /**
   * Testing if the player is killed.
   */
  @Test
  public void testKillPlayer() {
    player.killPlayer();
    assertEquals("Oops.. Player 100 is dead!!", player.getPlayerDescription());
  }
  
  /**
   * Test if player can pickup treasures after dying.
   */
  @Test (expected = IllegalStateException.class)
  public void testPickUpTreasureKilledPlayer() {
    player.killPlayer();
    player.pickTreasures(null);
  }
  
  /**
   * Test if player can pickup arrows after dying.
   */
  @Test (expected = IllegalStateException.class)
  public void testPickUpArrowsKilledPlayer() {
    player.killPlayer();
    player.pickupArrows();
  }
  
  /**
   * Test if player can use arrows after dying.
   */
  @Test (expected = IllegalStateException.class)
  public void testUseArrowsKilledPlayer() {
    player.killPlayer();
    player.useArrow();
  }
  
  /**
   * Test if player can move after dying.
   */
  @Test (expected = IllegalStateException.class)
  public void testMoveKilledPlayer() {
    player.killPlayer();
    player.setCurrentLocation(cave);
  }

  @Test
  public void testMonsterInSameCave() {
    cave.addMonster(new Otyugh(23));
    player.setCurrentLocation(cave);
    
    String expected = "Player 100 is currently residing in Cave 11.\n"
        + "Arrghhh..Monster in cave!!!\n"
        + "The cave does not have any treasures!\n"
        + "You find 3 arrows here.\n"
        + "The player can move to the following neighbors: \n";
    assertEquals(expected, player.getCurrentLocationDetails());
  }
  
  /**
   * Test to check if the smell is correctly estimated for a 
   * monster one position away from the current location.
   */
  @Test
  public void testMonsterLevelOneSmell() {
    cave.addNeighbor(Direction.SOUTH, new Cave(91, 1, 2));
    cave.addNeighbor(Direction.NORTH,  new Cave(21, 2, 3));
    cave.addNeighbor(Direction.EAST, new Cave(53, 1, 3));
    player.setCurrentLocation(cave);
    
    String expected = "Player 100 is currently residing in Cave 11.\n"
        + "The cave does not have any treasures!\n"
        + "You find 3 arrows here.\n"
        + "The player can move to the following neighbors: \n"
        + "EAST: Cave 53\n"
        + "NORTH: Cave 21\n"
        + "SOUTH: Cave 91";
    assertEquals(expected, player.getCurrentLocationDetails());
    
    List<Location> neighborList = new ArrayList<Location>(cave.getNeighbors().values());
    
    // Add monster to a neighbor location
    neighborList.get(2).addMonster(new Otyugh(44));
    
    expected = "Player 100 is currently residing in Cave 11.\n"
        + "There's something smelling terrible nearby..\n"
        + "The cave does not have any treasures!\n"
        + "You find 3 arrows here.\n"
        + "The player can move to the following neighbors: \n"
        + "EAST: Cave 53\n"
        + "NORTH: Cave 21\n"
        + "SOUTH: Cave 91";
    assertEquals(expected, player.getCurrentLocationDetails());
    
    // Add another monster to a neighbor location in level one
    neighborList.get(0).addMonster(new Otyugh(33));
    
    expected = "Player 100 is currently residing in Cave 11.\n"
        + "There's something smelling terrible nearby..\n"
        + "The cave does not have any treasures!\n"
        + "You find 3 arrows here.\n"
        + "The player can move to the following neighbors: \n"
        + "EAST: Cave 53\n"
        + "NORTH: Cave 21\n"
        + "SOUTH: Cave 91";
    assertEquals(expected, player.getCurrentLocationDetails());
  }
  
  /**
   * Test to check if the smell is correctly estimated for a 
   * single monster two positions away from the current location.
   */
  @Test
  public void testSingleMonsterLevelTwoSmell() {
    cave.addNeighbor(Direction.SOUTH, new Cave(91, 1, 2));
    cave.addNeighbor(Direction.NORTH,  new Cave(21, 2, 3));
    cave.addNeighbor(Direction.EAST, new Cave(53, 1, 3));
    player.setCurrentLocation(cave);
    
    List<Location> neighborList = new ArrayList<Location>(cave.getNeighbors().values());
    
    // Select a random level one neighbor (one position away)
    Location firstNeigh = neighborList.get(0);
    firstNeigh.addNeighbor(Direction.NORTH,  new Cave(11, 2, 3));
    firstNeigh.addNeighbor(Direction.SOUTH, new Cave(34, 1, 3));
    firstNeigh.addNeighbor(Direction.WEST, new Cave(99, 1, 3));
    
    // Select a random level two neighbor (two positions away)
    List<Location> secondNeighborList = new ArrayList<Location>(firstNeigh.getNeighbors().values());
    // Add monster
    secondNeighborList.get(2).addMonster(new Otyugh(55));
    
    String expected = "Player 100 is currently residing in Cave 11.\n"
        + "It's slightly smelly here..\n"
        + "The cave does not have any treasures!\n"
        + "You find 3 arrows here.\n"
        + "The player can move to the following neighbors: \n"
        + "EAST: Cave 53\n"
        + "NORTH: Cave 21\n"
        + "SOUTH: Cave 91";
    assertEquals(expected, player.getCurrentLocationDetails());
  }
  
  /**
   * Test to check if the smell is correctly estimated for 
   * multiple monsters two positions away from the current location.
   */
  @Test
  public void testMultipleMonsterLevelTwoSmell() {
    cave.addNeighbor(Direction.SOUTH, new Cave(91, 1, 2));
    cave.addNeighbor(Direction.NORTH,  new Cave(21, 2, 3));
    cave.addNeighbor(Direction.EAST, new Cave(53, 1, 3));
    player.setCurrentLocation(cave);
    
    List<Location> neighborList = new ArrayList<Location>(cave.getNeighbors().values());
    
    // Select a random level one neighbor (one position away)
    Location firstNeigh = neighborList.get(0);
    firstNeigh.addNeighbor(Direction.NORTH,  new Cave(11, 2, 3));
    firstNeigh.addNeighbor(Direction.SOUTH, new Cave(34, 1, 3));
    firstNeigh.addNeighbor(Direction.WEST, new Cave(99, 1, 3));
    
    // Select multiple random level two neighbors (two positions away)
    List<Location> secondNeighborList = new ArrayList<Location>(firstNeigh.getNeighbors().values());
    // Add monster to each
    secondNeighborList.get(2).addMonster(new Otyugh(55));
    secondNeighborList.get(0).addMonster(new Otyugh(89));
    
    String expected = "Player 100 is currently residing in Cave 11.\n"
        + "There's something smelling terrible nearby..\n"
        + "The cave does not have any treasures!\n"
        + "You find 3 arrows here.\n"
        + "The player can move to the following neighbors: \n"
        + "EAST: Cave 53\n"
        + "NORTH: Cave 21\n"
        + "SOUTH: Cave 91";
    assertEquals(expected, player.getCurrentLocationDetails());
  }
}
