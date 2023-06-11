package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dungeons.AdventureGame;
import dungeons.AdventureGameConsoleController;
import dungeons.AdventureGameController;
import dungeons.AdventureGameViewController;
import dungeons.Direction;
import dungeons.Game;
import dungeons.GameView;
import dungeons.RandomNumberGenerator;
import dungeons.RandomNumberGeneratorTest;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Test;

/**
 * A JUnit test class for the AdventureGameController interface and its' 
 * implementing classes.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class AdventureGameControllerTest {
  
  RandomNumberGenerator rand;
  AdventureGameController testGame;
      
  /**
   * Test if the constructor creates a game controller object without any 
   * errors.
   */
  @Test
  public void testAdventureGameControllerConstructor() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    Game model = new AdventureGame(1, 5, 5, 5, false, 45, 3, rand);
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    testGame = new AdventureGameConsoleController(input, output, model);
  }
  
  @Test
  public void testHangingEnd() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    Game model = new AdventureGame(1, 5, 5, 5, false, 45, 3, rand);
    
    StringReader input = new StringReader("A A T S N");
    StringBuilder gameLog = new StringBuilder();
    testGame = new AdventureGameConsoleController(input, gameLog, model);
    
    Thread current = new Thread() {
      @Override
      public void run() {
        try {
          testGame.playGame();
        } catch (NoSuchElementException n) {
          // do nothing
        }
      }
    };
    
    current.start();
    
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // do nothing
    }
    
    String[] lines = gameLog.toString().split("\n");
    
    assertEquals("Q: Quit game", lines[lines.length - 1]);
  }
  
  /**
   * Test quitting game at start of the game.
   */
  @Test
  public void testQuittingGameAtStart() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    Game model = new AdventureGame(1, 5, 5, 5, false, 45, 3, rand);
    
    // At start
    StringReader input = new StringReader("Q");
    StringBuilder gameLog = new StringBuilder();
    testGame = new AdventureGameConsoleController(input, gameLog, model);
    
    testGame.playGame();
    
    String[] lines = gameLog.toString().split("\n");
    
    String expected = "Thank you for playing. Hope you enjoyed the adventure game!";
    assertEquals(expected, lines[lines.length - 1]);
  }
  
  /**
   * Test quitting game after making a move.
   */
  @Test
  public void testQuittingGameAfterMove() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    Game model = new AdventureGame(1, 5, 5, 5, false, 45, 3, rand);
    
    // After making a move to a new position
    StringReader input = new StringReader("M N M Q");
    StringBuilder gameLog = new StringBuilder();
    testGame = new AdventureGameConsoleController(input, gameLog, model);
    
    testGame.playGame();
    
    String[] lines = gameLog.toString().split("\n");
    
    String expected = "Thank you for playing. Hope you enjoyed the adventure game!";
    assertEquals(expected, lines[lines.length - 1]);
  }
  
  /**
   * Test quitting game while shooting arrow.
   */
  @Test
  public void testQuittingGameWhileShooting() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    Game model = new AdventureGame(1, 5, 5, 5, false, 45, 3, rand);
    
    // While shooting arrow in N direction
    StringReader input = new StringReader("S N Q");
    StringBuilder gameLog = new StringBuilder();
    testGame = new AdventureGameConsoleController(input, gameLog, model);
    
    testGame.playGame();
    
    String[] lines = gameLog.toString().split("\n");
    
    String expected = "Thank you for playing. Hope you enjoyed the adventure game!";
    assertEquals(expected, lines[lines.length - 1]);
  }
  
  /**
   * Test that verifies that the controller handles a move command 
   * in wrong direction correctly.
   */
  @Test
  public void testMoveInWrongDirection() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    Game model = new AdventureGame(1, 5, 5, 5, false, 45, 3, rand);
    
    // While shooting arrow in N direction
    StringReader input = new StringReader("M N Q");
    StringBuilder gameLog = new StringBuilder();
    testGame = new AdventureGameConsoleController(input, gameLog, model);
    
    testGame.playGame();
    
    String[] lines = gameLog.toString().split("\n");
        
    String expected = "Oh no, that's a dead-end, make another move!! Q for quitting game.";
    assertEquals(expected, lines[lines.length - 4]);
  }
  
  /**
   * Test that verifies that the controller handles a move command 
   * in open entrance direction correctly.
   */
  @Test
  public void testMoveInCorrectDirection() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    Game model = new AdventureGame(1, 5, 5, 5, false, 45, 3, rand);
    
    // While shooting arrow in N direction
    StringReader input = new StringReader("M W Q");
    StringBuilder gameLog = new StringBuilder();
    testGame = new AdventureGameConsoleController(input, gameLog, model);
    
    testGame.playGame();
    
    String[] lines = gameLog.toString().split("\n");
    
    String expected = "Player 101 is currently residing in Tunnel 0.";
    assertEquals(expected, lines[lines.length - 18]);
  }
  
  /**
   * Test to ensure controller handles collecting treasures correctly.
   */
  @Test
  public void testCollectTreasure() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    Game model = new AdventureGame(1, 5, 5, 5, false, 45, 3, rand);
    
    // While shooting arrow in N direction
    StringReader input = new StringReader("T R Q");
    StringBuilder gameLog = new StringBuilder();
    testGame = new AdventureGameConsoleController(input, gameLog, model);
    
    testGame.playGame();
    
    String[] lines = gameLog.toString().split("\n");
    
    String expected = "The Player 101 has collected the available [RUBIES] from the cave.";
    assertEquals(expected, lines[lines.length - 23]);
  }
  
  /**
   * Test to ensure controller handles picking up arrows correctly.
   */
  @Test
  public void testPickupArrows() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    Game model = new AdventureGame(1, 5, 5, 5, false, 45, 3, rand);
    
    // While shooting arrow in N direction
    StringReader input = new StringReader("A Q");
    StringBuilder gameLog = new StringBuilder();
    testGame = new AdventureGameConsoleController(input, gameLog, model);
    
    testGame.playGame();
    
    String[] lines = gameLog.toString().split("\n");
    
    String expected = "The player is equipped with 3 arrows.";
    assertEquals(expected, lines[lines.length - 21]);
  }
  
  /**
   * Test to ensure controller handles shooting arrows correctly.
   */
  @Test
  public void testShootArrow() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    Game model = new AdventureGame(1, 5, 5, 5, false, 45, 3, rand);
    
    // While shooting arrow in N direction
    StringReader input = new StringReader("S E 1 Q");
    StringBuilder gameLog = new StringBuilder();
    testGame = new AdventureGameConsoleController(input, gameLog, model);
    
    testGame.playGame();
    
    String[] lines = gameLog.toString().split("\n");
    
    String expected = "You hear a great howl in the distance";
    assertEquals(expected, lines[lines.length - 21]);
  }
  
  /**
   * Test to ensure controller handles invalid inputs correctly.
   */
  @Test
  public void testInvalidMove() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    Game model = new AdventureGame(1, 5, 5, 5, false, 45, 3, rand);
    
    // While shooting arrow in N direction
    StringReader input = new StringReader("l Q");
    StringBuilder gameLog = new StringBuilder();
    testGame = new AdventureGameConsoleController(input, gameLog, model);
    
    testGame.playGame();
    
    String[] lines = gameLog.toString().split("\n");
    
    String expected = "Invalid input!! Please provide proper input!!";
    assertEquals(expected, lines[lines.length - 21]);
  }
  
  /**
   * Test to ensure controller handles IO exception correctly.
   */
  @Test
  public void testIoException() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    Game model = new AdventureGame(1, 5, 5, 5, false, 45, 3, rand);
    
    Readable input = new StringReader("Q");
    Appendable gameLog = new FailingAppendable();
    
    testGame = new AdventureGameConsoleController(input, gameLog, model);
    try {
      testGame.playGame();
    } catch (IllegalStateException s) {
      assertEquals("Failed during game playing.", s.getMessage());
    }
  }
  
  @Test
  public void testMockView() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    List<String> gameLogModel = new ArrayList<>();
    Game model = new MockModel(gameLogModel, 1);
    List<String> gameLogView = new ArrayList<>();
    GameView view = new MockView(gameLogView);
    AdventureGameController c = new AdventureGameViewController(view, model, rand);
    
    c.playGame();
    assertTrue(gameLogView.toString().contains("addListeners() called."));
    assertTrue(gameLogView.toString().contains("displayVisible() called."));
    
    view.refresh();
    assertTrue(gameLogView.toString().contains("refresh() called."));
    
    view.quitGame();
    assertTrue(gameLogView.toString().contains("quitGame() called."));
  }
  
  @Test
  public void testMove() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    List<String> gameLogModel = new ArrayList<>();
    Game model = new MockModel(gameLogModel, 1);
    List<String> gameLogView = new ArrayList<>();
    GameView view = new MockView(gameLogView);
    AdventureGameController c = new AdventureGameViewController(view, model, rand);
    
    c.handleCellClick(Direction.NORTH);
    
    assertTrue(gameLogModel.toString().contains("Player 1 move() called."));
  }
  
  @Test
  public void testTwoPlayerMove() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    List<String> gameLogModel = new ArrayList<>();
    Game model = new MockModel(gameLogModel, 2);
    List<String> gameLogView = new ArrayList<>();
    GameView view = new MockView(gameLogView);
    AdventureGameController c = new AdventureGameViewController(view, model, rand);
    
    c.handleCellClick(Direction.NORTH);
    
    assertTrue(gameLogModel.toString().contains("Player 1 move() called."));
    
    c.handleCellClick(Direction.NORTH);
    
    assertTrue(gameLogModel.toString().contains("Player 2 move() called."));
  }
  
  @Test
  public void testShoot() {
    rand = new RandomNumberGeneratorTest(0, 10, 2, 3, 5, 6, 8);
    List<String> gameLogModel = new ArrayList<>();
    Game model = new MockModel(gameLogModel, 1);
    List<String> gameLogView = new ArrayList<>();
    GameView view = new MockView(gameLogView);
    AdventureGameController c = new AdventureGameViewController(view, model, rand);
    
    // In the event of KeyEvent.VK_1 and KeyEvent.VK_W, the controller should call shoot.
  }
}
