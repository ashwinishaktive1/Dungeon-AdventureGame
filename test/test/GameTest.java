package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import dungeons.AdventureGame;
import dungeons.Direction;
import dungeons.Dungeons;
import dungeons.Game;
import dungeons.MazeDungeon;
import dungeons.Player;
import dungeons.RandomNumberGenerator;
import dungeons.RandomNumberGeneratorDev;
import dungeons.RandomNumberGeneratorTest;
import dungeons.Treasure;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;


/**
 * A JUnit test case for Games Interface and AdventureGame class.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class GameTest {
  
  RandomNumberGenerator rand;
  Game wrappedMazeGame;
  Game unwrappedMazeGame;
  
  /**
   * Setting up game.
   */
  @Before
  public void setup() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    wrappedMazeGame = new AdventureGame(1, 5, 5, 3, true, 40.5, 3, rand);
    unwrappedMazeGame = new AdventureGame(1, 5, 5, 5, false, 20.5, 3, rand);
  }
  
  /**
   * Testing AdventureGame construction.
   */
  @Test
  public void testAdventureGameConstructor() {
    Game test = new AdventureGame(1, 5, 5, 3, true, 40.5, 2, rand);
    assertEquals("The game is yet to begin! Players not ready.", test.getGameStatus());
  }
  
  /**
   * Testing invalid number of players, less than 1.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidNumberOfPlayersLess() {
    wrappedMazeGame = new AdventureGame(0, 5, 5, 3, true, 40.5, 2, rand);
  }
  
  /**
   * Testing invalid number of players, more than 2.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidNumberOfPlayersMore() {
    wrappedMazeGame = new AdventureGame(5, 5, 5, 3, true, 40.5, 2, rand);
  }
  
  /**
   * Testing invalid row count.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidRows() {
    wrappedMazeGame = new AdventureGame(1, -1, 5, 3, true, 40.5, 2, rand);
  }
  
  /**
   * Testing invalid column count.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidColumns() {
    wrappedMazeGame = new AdventureGame(1, 5, 0, 3, true, 40.5, 2, rand);
  }
  
  /**
   * Testing if negative interconnectivity is accepted.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidInterconnectivity() {
    wrappedMazeGame = new AdventureGame(1, 5, 5, -1, true, 40.5, 2, rand);
  }
  
  /**
   * Testing a negative treasure spread.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testLessTreasureSpread() {
    wrappedMazeGame = new AdventureGame(1, 5, 5, 3, true, -20.0, 2, rand);
  }
  
  /**
   * Testing a treasure spread more than 100%.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testMoreTreasureSpread() {
    wrappedMazeGame = new AdventureGame(1, 5, 5, 3, true, 120.0, 2, rand);
  }
  
  /**
   * Testing a null random generator object.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNullRandom() {
    wrappedMazeGame = new AdventureGame(1, 5, 5, 3, true, 20.0, 2, null);
  }
  
  /**
   * Testing if player is correctly setup.
   */
  @Test
  public void testSetupPlayers() {
    assertEquals("The game is yet to begin! Players not ready.", 
        wrappedMazeGame.getGameStatus());
    wrappedMazeGame.setupPlayerOne(100);
    assertEquals("The game is yet to begin! Player 100 is ready.", 
        wrappedMazeGame.getGameStatus());
  }
  
  /**
   * Testing if player 2 can be setup in a one player game.
   */
  @Test (expected = IllegalStateException.class)
  public void testPlayerTwoSetupInOnePlayerGame() {
    wrappedMazeGame.setupPlayerTwo(101);
  }
  
  /**
   * Testing game status, before the player is ready, after the player 
   * is ready, after the game has started.
   */
  @Test
  public void testGetGameStatus() {
    wrappedMazeGame = new AdventureGame(2, 5, 5, 3, true, 40.5, 3, rand);
    assertEquals("The game is yet to begin! Players not ready.", 
        wrappedMazeGame.getGameStatus());
    wrappedMazeGame.setupPlayerOne(100);
    assertEquals("The game is yet to begin! Player 100 is ready.", 
        wrappedMazeGame.getGameStatus());
    wrappedMazeGame.setupPlayerTwo(101);
    assertEquals("The game is yet to begin! Player 100 and Player 101 are ready.", 
        wrappedMazeGame.getGameStatus());
    wrappedMazeGame.startGame();
    assertEquals("The game is ongoing, player is in search of the end!", 
        wrappedMazeGame.getGameStatus());
  }
  
  /**
   * Testing start game without setting up player.
   */
  @Test (expected = IllegalStateException.class)
  public void testStartGameWithoutPlayer() {
    wrappedMazeGame.startGame();
  }
  
  /**
   * Testing start game without setting up all players in two player game.
   */
  @Test (expected = IllegalStateException.class)
  public void testStartGameWithoutPlayerTwo() {
    wrappedMazeGame = new AdventureGame(2, 5, 5, 3, true, 40.5, 3, rand);
    wrappedMazeGame.setupPlayerOne(1);
    wrappedMazeGame.startGame();
  }
  
  /**
   * Test if player 2 can be setup before player 1 is ready.
   */
  @Test (expected = IllegalStateException.class)
  public void testSettingPlayerTwoBeforePlayerOne() {
    wrappedMazeGame = new AdventureGame(2, 5, 5, 3, true, 40.5, 3, rand);
    wrappedMazeGame.setupPlayerTwo(101);
  }
  
  /**
   * Testing if player 2 can be set with the same id as player 1.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testTwoPlayersSameId() {
    wrappedMazeGame = new AdventureGame(2, 5, 5, 3, true, 40.5, 3, rand);
    wrappedMazeGame.setupPlayerOne(101);
    wrappedMazeGame.setupPlayerTwo(101);
  }
  
  /**
   * Testing if the game has started correctly.
   */
  @Test
  public void testStartGame() {
    // Wrapped
    wrappedMazeGame.setupPlayerOne(2);
    wrappedMazeGame.startGame();
    assertEquals("The game is ongoing, player is in search of the end!", 
        wrappedMazeGame.getGameStatus());
    
    // Unwrapped
    unwrappedMazeGame.setupPlayerOne(56);
    unwrappedMazeGame.startGame();
    assertEquals("The game is ongoing, player is in search of the end!", 
        unwrappedMazeGame.getGameStatus());
  }
  
  /**
   * Testing introduction of player before and after starting the game.
   */
  @Test
  public void testIntroducePlayer() {
    wrappedMazeGame.setupPlayerOne(100); 
    
    assertEquals("The game is yet to begin! Player 100 is ready. \n"
        + "Player 100 has not collected any treasure yet.\n"
        + "The player is equipped with 3 arrows.",
        wrappedMazeGame.introducePlayer());
    
    wrappedMazeGame.startGame();
    
    assertEquals("The game is ongoing, player is in search of the end! \n"
        + "Player 100 has not collected any treasure yet.\n"
        + "The player is equipped with 3 arrows.",
        wrappedMazeGame.introducePlayer());
  }
  
  /**
   * Testing the available moves from start position.
   */
  @Test
  public void testGetAvailableMoves() {
    Dungeons testDungeon = new MazeDungeon(5, 5, 3, true, 40.5, 2, rand);
    testDungeon.assignStartAndEnd();
    Player test = new Player(3);
    testDungeon.getStart().addPlayer(test); 
        
    wrappedMazeGame.setupPlayerOne(3);
    wrappedMazeGame.startGame();
    
    String expected = "Player 3 is currently residing in Cave 0.\n" 
        + "There's something smelling terrible nearby..\n"
        + testDungeon.getStart().getPresentTreasures() 
        + "\nYou find 10 arrows here."
        + "\nThe player can move "
        + "to the following neighbors: \n" 
        + testDungeon.getStart().getLocationDetails();
    
    assertEquals(test.getCurrentLocationDetails(), wrappedMazeGame.getAvailableMoves());
    assertEquals(expected, wrappedMazeGame.getAvailableMoves());
  }
  
  /**
   * Test picking up treasure from the cave.
   */
  @Test
  public void testPickUpTreasures() {
    wrappedMazeGame.setupPlayerOne(3);
    wrappedMazeGame.startGame();
    
    List<Treasure> desired = new ArrayList<>();
    desired.add(Treasure.DIAMONDS);
    assertEquals("The Player 3 has collected the available [DIAMONDS] from the cave.\n"
        + "Player 3 has lifetime collection of: {DIAMONDS=1, SAPPHIRE=0, RUBIES=0}\n"
        + "Player 3 is equipped with 3 arrows.", 
        wrappedMazeGame.pickUpTreasures(desired));
  }
    
  /**
   * Testing get player status before and after collection of treasures.
   */
  @Test
  public void testGetPlayerStatus() {
    wrappedMazeGame.setupPlayerOne(3);
    wrappedMazeGame.startGame();
    
    String expected = "Player 3 has not collected any treasure yet.\n"
        + "The player is equipped with 3 arrows.";
    assertEquals(expected, wrappedMazeGame.getPlayerStatus());
    
    List<Treasure> desired = new ArrayList<>();
    desired.add(Treasure.DIAMONDS);
    wrappedMazeGame.pickUpTreasures(desired);
    
    assertEquals("Player 3 has lifetime collection of: {DIAMONDS=1, SAPPHIRE=0, RUBIES=0}\n"
        + "Player 3 is equipped with 3 arrows.", 
        wrappedMazeGame.getPlayerStatus());
  }
  
  /**
   * Testing illegal moves from the current position should 
   * throw an exception.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testIllegalMove() {
    unwrappedMazeGame.setupPlayerOne(3);
    unwrappedMazeGame.startGame();
    
    unwrappedMazeGame.move(Direction.NORTH);
  }
    
  /** 
   * Test making moves to all 4 directions north, south, east, and west 
   * from a location in wrapped dungeon. Tested moving to caves and tunnel. 
   * Move also includes stepping to a wrapped cave. 
   */
  @Test
  public void testMoveWrapped() {    
    wrappedMazeGame.setupPlayerOne(3);
    wrappedMazeGame.startGame();
    
    String expected = "Player 3 is currently residing in Cave 0.\n" 
        + "There's something smelling terrible nearby..\n"
        + "{DIAMONDS=1, SAPPHIRE=1, RUBIES=1} are present in the cave!!\n"
        + "You find 10 arrows here.\n"
        + "The player can move to the following neighbors: \n"
        + "SOUTH: Cave 5\n"
        + "EAST: Cave 1\n"
        + "WEST: Cave 4\n"
        + "NORTH: Cave 20";
    
    assertEquals(expected, wrappedMazeGame.getAvailableMoves());
    
    wrappedMazeGame.move(Direction.NORTH);
    
    expected = "Player 3 is currently residing in Cave 20.\n" 
        + "It's slightly smelly here..\n"
        + "The cave does not have any treasures!"
        + "\nYou find 0 arrows here."
        + "\nThe player can move to the following neighbors: \n" 
        + "SOUTH: Cave 0";
    
    assertEquals(expected, wrappedMazeGame.getAvailableMoves());
    
    wrappedMazeGame.move(Direction.SOUTH);
    
    // Shoot the monster in cave 1
    wrappedMazeGame.shootArrow(Direction.EAST, 1);
    
    wrappedMazeGame.move(Direction.EAST);
    
    expected = "Player 3 is currently residing in Cave 1.\n" 
        + "Arrghhh..Monster in cave!!!\n"
        + "The cave does not have any treasures!" 
        + "\nYou find 0 arrows here."
        + "\nThe player can move to the following neighbors: \n" 
        + "SOUTH: Cave 6\n"
        + "EAST: Cave 2\n"
        + "WEST: Cave 0\n"
        + "NORTH: Cave 21";
    
    assertEquals(expected, wrappedMazeGame.getAvailableMoves());
    
    wrappedMazeGame.move(Direction.SOUTH);
    
    expected = "Player 3 is currently residing in Cave 6.\n" 
        + "There's something smelling terrible nearby..\n"
        + "{DIAMONDS=1, SAPPHIRE=1, RUBIES=1} are present in the cave!!\n"
        + "You find 0 arrows here.\n"
        + "The player can move to the following neighbors: \n"
        + "SOUTH: Tunnel 11\n"
        + "EAST: Cave 7\n"
        + "WEST: Cave 5\n"
        + "NORTH: Cave 1";
    
    assertEquals(expected, wrappedMazeGame.getAvailableMoves());
    
    wrappedMazeGame.move(Direction.SOUTH);
    
    expected = "Player 3 is currently residing in Tunnel 11.\n" 
        + "It's slightly smelly here..\n"
        + "The tunnel does not have any treasure to collect!" 
        + "\nYou find 0 arrows here."
        + "\nThe player can move to the following neighbors: \n" 
        + "SOUTH: Cave 16\n"
        + "NORTH: Cave 6";
    
    assertEquals(expected, wrappedMazeGame.getAvailableMoves());
    
    wrappedMazeGame.move(Direction.NORTH);
    wrappedMazeGame.move(Direction.WEST);
    
    expected = "Player 3 is currently residing in Cave 5.\n" 
        + "It's slightly smelly here..\n"
        + "{DIAMONDS=1, SAPPHIRE=1, RUBIES=1} are present in the cave!!\n"
        + "You find 0 arrows here.\n"
        + "The player can move to the following neighbors: \n"
        + "SOUTH: Tunnel 10\n"
        + "EAST: Cave 6\n"
        + "NORTH: Cave 0";
    
    assertEquals(expected, wrappedMazeGame.getAvailableMoves());
  }
  
  /** 
   * Test making moves to all 4 directions north, south, east, and west 
   * from a location in unwrapped dungeon. Tested moving to caves and tunnel. 
   * Move also includes checks for presence of wrapped caves in available 
   * moves. 
   */
  @Test
  public void testMoveUnwrapped() {    
    unwrappedMazeGame.setupPlayerOne(3);
    unwrappedMazeGame.startGame();
    
    String expected = "Player 3 is currently residing in Cave 1.\n" 
        + "There's something smelling terrible nearby..\n"
        + "{DIAMONDS=1, SAPPHIRE=1, RUBIES=1} are present in the cave!!\n"
        + "You find 0 arrows here.\n"
        + "The player can move to the following neighbors: \n"
        + "SOUTH: Cave 6\n"
        + "EAST: Cave 2\n"
        + "WEST: Tunnel 0";
    
    assertEquals(expected, unwrappedMazeGame.getAvailableMoves());
    
    unwrappedMazeGame.move(Direction.WEST);
    
    expected = "Player 3 is currently residing in Tunnel 0.\n" 
        + "It's slightly smelly here..\n"
        + "The tunnel does not have any treasure to collect!"
        + "\nYou find 5 arrows here."
        + "\nThe player can move to the following neighbors: \n" 
        + "SOUTH: Cave 5\n"
        + "EAST: Cave 1";
    
    assertEquals(expected, unwrappedMazeGame.getAvailableMoves());
    
    unwrappedMazeGame.move(Direction.SOUTH);
    
    expected = "Player 3 is currently residing in Cave 5.\n" 
        + "The cave does not have any treasures!"
        + "\nYou find 0 arrows here."
        + "\nThe player can move to the following neighbors: \n" 
        + "SOUTH: Tunnel 10\n"
        + "EAST: Cave 6\n"
        + "NORTH: Tunnel 0";
    
    assertEquals(expected, unwrappedMazeGame.getAvailableMoves());
    
    unwrappedMazeGame.move(Direction.EAST);
    
    expected = "Player 3 is currently residing in Cave 6.\n"
        + "It's slightly smelly here..\n"
        + "The cave does not have any treasures!" 
        + "\nYou find 0 arrows here."
        + "\nThe player can move to the following neighbors: \n" 
        + "SOUTH: Tunnel 11\n"
        + "WEST: Cave 5\n"
        + "NORTH: Cave 1";
    
    assertEquals(expected, unwrappedMazeGame.getAvailableMoves());
    
    unwrappedMazeGame.move(Direction.NORTH);
    
    expected = "Player 3 is currently residing in Cave 1.\n" 
        + "There's something smelling terrible nearby..\n"
        + "{DIAMONDS=1, SAPPHIRE=1, RUBIES=1} are present in the cave!!\n"
        + "You find 0 arrows here.\n"
        + "The player can move to the following neighbors: \n"
        + "SOUTH: Cave 6\n"
        + "EAST: Cave 2\n"
        + "WEST: Tunnel 0";
    
    assertEquals(expected, unwrappedMazeGame.getAvailableMoves());
  }

  /**
   * Testing the game result before reaching the end.
   */
  @Test
  public void testGetGameResult() {
    wrappedMazeGame.setupPlayerOne(100);
    wrappedMazeGame.startGame();
    assertEquals("The game is ongoing. Please recheck after ending the "
        + "game.", wrappedMazeGame.getGameResult());
    
    wrappedMazeGame.move(Direction.EAST);
    assertEquals("Oh no, Player 100 died before winning over the end location!!", 
        wrappedMazeGame.getGameResult());
  }
  
  /**
   * Testing if the player reaches the end of the maze.
   */
  @Test
  public void testCompletingTheMazeWrapped() {
    RandomNumberGenerator randomDev = new RandomNumberGeneratorDev();
    wrappedMazeGame = new AdventureGame(1, 5, 5, 3, true, 40.5, 1, rand);
    wrappedMazeGame.setupPlayerOne(100);
    wrappedMazeGame.startGame();
    
    List<Direction> allDirections = new ArrayList<>();
    allDirections.add(Direction.NORTH);
    allDirections.add(Direction.SOUTH);
    allDirections.add(Direction.WEST);
    allDirections.add(Direction.EAST);
    boolean flag = true;
    
    while (flag) {
      List<Direction> availableDirections = new ArrayList<>();
      
      String[] availableMoves = wrappedMazeGame.getAvailableMoves().split("\n");
      List<String> directions = new ArrayList<>();
      for (int i = 3; i < availableMoves.length; i++) {
        directions.add(availableMoves[i].split(":")[0]);
      }
      for (String dirString : directions) {
        for (Direction dir : allDirections) {
          if (dir.toString().equals(dirString)) {
            availableDirections.add(dir);
          }
        }
      }
      Integer randomDirection = randomDev.getRandomNumber(0, availableDirections.size());
      
      try {
        wrappedMazeGame.move(availableDirections.get(randomDirection));
      } catch (IllegalStateException s) {
        assertEquals("Oh no, Player 100 died before winning over the end location!!", 
            wrappedMazeGame.getGameResult());
        assertEquals("The Player 100 successfully reach the end, but was "
            + "killed by the last Otyugh. The game has ended.", 
            wrappedMazeGame.getGameStatus());
        flag = false;
      }
    }
  }
  
  /**
   * Test to check if player can shoot after being killed by monster.
   */
  @Test 
  public void testShootArrowAfterPlayerDied() {
    wrappedMazeGame.setupPlayerOne(100);
    wrappedMazeGame.startGame();
    
    wrappedMazeGame.move(Direction.EAST);
    
    String expected = "\nOops, Player 100 is dead, cannot shoot!\n";
    
    assertEquals(expected, wrappedMazeGame.shootArrow(Direction.EAST, 1));
  }
  
  /**
   * Test to check if player can move after being killed by monster.
   */
  @Test (expected = IllegalStateException.class)
  public void testMoveAfterPlayerDied() {
    wrappedMazeGame.setupPlayerOne(100);
    wrappedMazeGame.startGame();
    
    wrappedMazeGame.move(Direction.EAST);
    
    wrappedMazeGame.move(Direction.EAST);
  }
  
  /**
   * Test to check player cannot shoot when they do not have any arrow stack.
   */
  @Test
  public void testShootArrowWhenNoArrowsRemaining() {
    wrappedMazeGame.setupPlayerOne(100);
    wrappedMazeGame.startGame();
    
    wrappedMazeGame.shootArrow(Direction.NORTH, 1);
    wrappedMazeGame.shootArrow(Direction.NORTH, 1);
    wrappedMazeGame.shootArrow(Direction.NORTH, 1);
    
    String expected = "\nPlayer 100 does not have any arrows to shoot,"
        + " collect more!\n";
    
    assertEquals(expected, wrappedMazeGame.shootArrow(Direction.NORTH, 1));
  }
  
  /**
   * Test to check if arrow goes waste when no monster is available one 
   * cave away.
   */
  @Test
  public void testShootArrowIntoDarknessOnePosition() {
    wrappedMazeGame.setupPlayerOne(3);
    wrappedMazeGame.startGame();
    
    String expected = "Player 3 is currently residing in Cave 0.\n" 
        + "There's something smelling terrible nearby..\n"
        + "{DIAMONDS=1, SAPPHIRE=1, RUBIES=1} are present in the cave!!\n"
        + "You find 10 arrows here.\n"
        + "The player can move to the following neighbors: \n"
        + "SOUTH: Cave 5\n"
        + "EAST: Cave 1\n"
        + "WEST: Cave 4\n"
        + "NORTH: Cave 20";
    
    assertEquals(expected, wrappedMazeGame.getAvailableMoves());
    
    // North cave has no exit in north direction
    wrappedMazeGame.move(Direction.NORTH);
    
    expected = "Player 3 is currently residing in Cave 20.\n" 
        + "It's slightly smelly here..\n"
        + "The cave does not have any treasures!"
        + "\nYou find 0 arrows here."
        + "\nThe player can move to the following neighbors: \n" 
        + "SOUTH: Cave 0";
    
    assertEquals(expected, wrappedMazeGame.getAvailableMoves());
    
    wrappedMazeGame.move(Direction.SOUTH);
    
    expected = "\nYou shoot an arrow into the darkness\n";
    assertEquals(expected, wrappedMazeGame.shootArrow(Direction.NORTH, 1));
  }
  
  /**
   * Test to check if arrow goes waste when no monster is available two 
   * caves away.
   */
  @Test
  public void testShootArrowIntoDarknessTwoPositions() {
    unwrappedMazeGame.setupPlayerOne(3);
    unwrappedMazeGame.startGame();
    
    //    Cave 1
    //       |
    //    Cave 6
    //       |
    //    Tunnel 11
    //       |
    //    Cave 16
    String expected = "\nYou shoot an arrow into the darkness\n";
    assertEquals(expected, unwrappedMazeGame.shootArrow(Direction.SOUTH, 2));
  }
  
  /**
   * Test to check if the arrow moves in crooked direction correctly.
   */
  @Test
  public void testShootArrowThroughTunnelCrooked() {
    unwrappedMazeGame.setupPlayerOne(3);
    unwrappedMazeGame.startGame();
    
    // Cave 1 - Cave 6 - Tunnel 11 - Cave 16 - Tunnel 21 - Tunnel 15 - Tunnel 10 - Cave 5
    
    String expected = "\nYou shoot an arrow into the darkness\n";
    assertEquals(expected, unwrappedMazeGame.shootArrow(Direction.SOUTH, 3));
  }
  
  /**
   * Test to ensure the arrow hits and hurts the monster successfully.
   */
  @Test
  public void testShootArrowSuccess() {
    unwrappedMazeGame.setupPlayerOne(3);
    unwrappedMazeGame.startGame();
    
    // Cave 1 - Cave 2
    
    String expected = "\nYou hear a great howl in the distance\n";
    assertEquals(expected, unwrappedMazeGame.shootArrow(Direction.EAST, 1));
    
    // Cave 1 - Cave 6 - Tunnel 11 - Cave 16 - Tunnel 21 - Tunnel 15 - Tunnel 10 - 
    // Cave 5 - Tunnel 0 - Cave 1 - Cave 2
    
    assertEquals(expected, unwrappedMazeGame.shootArrow(Direction.SOUTH, 4));
  }
  
  /**
   * Test to check if the arrow moves across monster for a distance 
   * across monster cave.
   */
  @Test
  public void testShootArrowMissMonster() {
    unwrappedMazeGame.setupPlayerOne(3);
    unwrappedMazeGame.startGame();
    
    // Cave 2 and Cave 3 have monsters in them
    
    // Cave 1 - Cave 6 - Tunnel 11 - Cave 16 - Tunnel 21 - Tunnel 15 - Tunnel 10 - 
    // Cave 5 - Tunnel 0 - Cave 1 - **Cave 2** - **Cave 3** - Tunnel 4 - Tunnel 9 - 
    // Tunnel 14 - Cave 19
    
    String expected = "\nYou shoot an arrow into the darkness\n";
    assertEquals(expected, unwrappedMazeGame.shootArrow(Direction.SOUTH, 6));
    
    wrappedMazeGame.setupPlayerOne(3);
    wrappedMazeGame.startGame();
    
    // Cave 1 and Cave 2 have monsters in them
    
    // Cave 0 - **Cave 1** - **Cave 2** - Cave 3
    
    expected = "\nYou shoot an arrow into the darkness\n";
    assertEquals(expected, wrappedMazeGame.shootArrow(Direction.EAST, 3));
  }
  
  /**
   * Test to ensure player is killed by the monster.
   */
  @Test
  public void testPlayerKilledByHealthyMonster() {
    // Unwrapped
    unwrappedMazeGame.setupPlayerOne(3);
    unwrappedMazeGame.startGame();
    
    String expected = "Chomp, chomp, chomp, Player 3 eaten by an Otyugh!";
    assertEquals(expected, unwrappedMazeGame.move(Direction.EAST));
    
    assertEquals("Oh no, Player 3 died before winning over the end location!!", 
        unwrappedMazeGame.getGameResult());
    
    // Wrapped
    wrappedMazeGame.setupPlayerOne(3);
    wrappedMazeGame.startGame();
    assertEquals(expected, wrappedMazeGame.move(Direction.EAST));
    
    assertEquals("Oh no, Player 3 died before winning over the end location!!", 
        wrappedMazeGame.getGameResult());
  }
  
  /**
   * Test to check if player can escape a hurt monster. 
   * With the RandomNumberGeneratorTest, the player will always escape 
   * the monster, however, in development it has a 50-50 chance to escape.
   */
  @Test
  public void testPlayerEscapeHurtMonster() {
    // Unwrapped
    unwrappedMazeGame.setupPlayerOne(3);
    unwrappedMazeGame.startGame();
    
    String expected = "\nYou hear a great howl in the distance\n";
    assertEquals(expected, unwrappedMazeGame.shootArrow(Direction.EAST, 1));
    
    expected = "Monster hurt: Player 3 escaped this time by luck, move quick!";
    assertEquals(expected, unwrappedMazeGame.move(Direction.EAST));
    
    // Wrapped
    wrappedMazeGame.setupPlayerOne(3);
    wrappedMazeGame.startGame();
    
    expected = "\nYou hear a great howl in the distance\n";
    assertEquals(expected, wrappedMazeGame.shootArrow(Direction.EAST, 1));
    
    expected = "Monster hurt: Player 3 escaped this time by luck, move quick!";
    assertEquals(expected, wrappedMazeGame.move(Direction.EAST));
  }
  
  /**
   * Test to check if the player can safely access the cave with 
   * killed monster.
   */
  @Test
  public void testPlayerMoveAcrossDeadMonster() {
    // Unwrapped
    unwrappedMazeGame.setupPlayerOne(3);
    unwrappedMazeGame.startGame();
    
    unwrappedMazeGame.shootArrow(Direction.EAST, 1);
    unwrappedMazeGame.shootArrow(Direction.EAST, 1);
    
    String expected = "";
    assertEquals(expected, unwrappedMazeGame.move(Direction.EAST));
    
    // Wrapped
    wrappedMazeGame.setupPlayerOne(3);
    wrappedMazeGame.startGame();
    
    wrappedMazeGame.shootArrow(Direction.EAST, 1);
    wrappedMazeGame.shootArrow(Direction.EAST, 1);
    
    expected = "";
    assertEquals(expected, wrappedMazeGame.move(Direction.EAST));
  }
  
  /**
   * Testing the turns are swapped after each move or shoot in a two-player game.
   */
  @Test
  public void testGetTurn() {
    wrappedMazeGame = new AdventureGame(2, 5, 5, 3, true, 40.5, 3, rand);
    wrappedMazeGame.setupPlayerOne(101);
    wrappedMazeGame.setupPlayerTwo(100);
    
    wrappedMazeGame.startGame();
    
    assertEquals("Player 101", wrappedMazeGame.getTurn().getPlayerId());
    
    wrappedMazeGame.move(Direction.NORTH);
    
    assertEquals("Player 100", wrappedMazeGame.getTurn().getPlayerId());
    
    wrappedMazeGame.move(Direction.WEST);
    
    assertEquals("Player 101", wrappedMazeGame.getTurn().getPlayerId());
    
    wrappedMazeGame.shootArrow(Direction.WEST, 2);
    
    assertEquals("Player 100", wrappedMazeGame.getTurn().getPlayerId());
    
    wrappedMazeGame.shootArrow(Direction.SOUTH, 1);
  }
  
  /**
   * Testing if winner is returned correctly.
   */
  @Test
  public void testGetWinner() {
    wrappedMazeGame = new AdventureGame(2, 5, 5, 3, true, 40.5, 3, rand);
    wrappedMazeGame.setupPlayerOne(101);
    wrappedMazeGame.setupPlayerTwo(100);
    
    wrappedMazeGame.startGame();
    
    assertNull(wrappedMazeGame.getWinner());
  }
  
  /**
   * Testing if the game continues after playerOne is killed by monster. 
   */
  @Test
  public void testGetTurnAfterPlayerOneKilled() {
    // Unwrapped
    unwrappedMazeGame = new AdventureGame(2, 5, 5, 5, false, 20.5, 3, rand);
    unwrappedMazeGame.setupPlayerOne(3);
    unwrappedMazeGame.setupPlayerOne(8);
    unwrappedMazeGame.startGame();
    
    String expected = "Chomp, chomp, chomp, Player 3 eaten by an Otyugh!";
    assertEquals(expected, unwrappedMazeGame.move(Direction.EAST));
    
    assertEquals("The game is ongoing. Please recheck after ending the game.", 
        unwrappedMazeGame.getGameResult());
    
    unwrappedMazeGame.shootArrow(Direction.EAST, 1);
    unwrappedMazeGame.shootArrow(Direction.EAST, 1);
    unwrappedMazeGame.move(Direction.EAST);
    
    assertEquals("The game is ongoing. Please recheck after ending the game.", 
        unwrappedMazeGame.getGameResult());
  }
  
  /**
   * Testing if the game continues after playerTwo is killed by monster. 
   */
  @Test
  public void testGetTurnAfterPlayerTwoKilled() {
    // Unwrapped
    unwrappedMazeGame = new AdventureGame(2, 5, 5, 5, false, 20.5, 3, rand);
    unwrappedMazeGame.setupPlayerOne(3);
    unwrappedMazeGame.setupPlayerOne(8);
    unwrappedMazeGame.startGame();
    
    unwrappedMazeGame.move(Direction.SOUTH);
    
    String expected = "Chomp, chomp, chomp, Player 8 eaten by an Otyugh!";
    assertEquals(expected, unwrappedMazeGame.move(Direction.EAST));
    
    assertEquals("The game is ongoing. Please recheck after ending the game.", 
        unwrappedMazeGame.getGameResult());
    
    unwrappedMazeGame.move(Direction.NORTH);
    unwrappedMazeGame.shootArrow(Direction.EAST, 1);
       
    assertEquals("Monster hurt: Player 3 escaped this time by luck, move quick!", 
        unwrappedMazeGame.move(Direction.EAST));
  }
  
  /**
   * Testing if the game continues when Player Two goes to cave with monster shot 
   * by Player One. So even if the monster is hurt by other player, the current player 
   * can take advantage of the same. 
   */
  @Test
  public void testGetTurnAfterPlayerGoesToHurtMonster() {
    // Unwrapped
    unwrappedMazeGame = new AdventureGame(2, 5, 5, 5, false, 20.5, 3, rand);
    unwrappedMazeGame.setupPlayerOne(3);
    unwrappedMazeGame.setupPlayerOne(8);
    unwrappedMazeGame.startGame();
    
    unwrappedMazeGame.shootArrow(Direction.EAST, 1);
    
    String expected = "Monster hurt: Player 8 escaped this time by luck, move quick!";
    assertEquals(expected, unwrappedMazeGame.move(Direction.EAST));
  }
}
