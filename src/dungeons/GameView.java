package dungeons;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * A view for Adventure Maze Game: display the game maze gradually and provide visual 
 * interface for users.
 */
public interface GameView {
  
  /**
   * Set up the controller to handle click events in this view.
   * 
   * @param listener the controller
   */
  void addControllerListener(AdventureGameController listener);
  
  /**
   * Set up the listeners to handle mouse click and key events in this view.
   * 
   * @param action click action listener
   * @param keyboard keyboard action listener
   */
  void addListeners(ActionListener action, KeyListener keyboard);
  
  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();
  
  /**
   * Setter for adventure game model, to initialize a new game.
   * 
   * @param updatedModel new game model to be used
   * @throws IllegalArgumentException if the updated model is null
   */
  void setModel(ReadonlyGameModel updatedModel) throws IllegalArgumentException;
  
  /**
   * Display the first landing page/home page to the user.
   */
  void displayHomePage();
  
  /**
   * Display the entire application view including menu and various panels.
   * The visibility is set to true.
   */
  void displayVisible();
  
  /**
   * Display the player detail request page to the user for a single player game
   * and get player ID.
   * 
   * @return identifier for player 1
   */
  String getSinglePlayerDetail();
  
  /**
   * Display the player detail request page to the user for a two player game
   * and get player ID.
   * 
   * @return identifier for player 2
   */
  String getDoublePlayerDetail();
  
  /**
   * Get the requested game features from the view.
   * 
   * @return get the game arguments set by player
   */
  String[] getGameArgs();
  
  /**
   * Display the maze game panel and game status side-by-side for the player.
   */
  void displayMazeGame();
  
  /**
   * Display game status.
   * 
   * @param status updated game status based on the user move
   */
  void displayGameStatus(String status);
  
  /**
   * Clear the user requested game arguments. To be used after the model is setup.
   */
  void clearGameArgs();
  
  /**
   * Clear the game status bar.
   */
  void clearStatus();
  
  /**
   * Dispose all dungeon setup from the previous game.
   */
  void clearGame();
  
  /**
   * Display which player's turn it is.
   */
  void displayGameTurn();
  
  /**
   * Quit the game and close the window.
   */
  void quitGame();
  
  /**
   * Repaint the entire view with updates to dungeon and game status.
   */
  void repaintView();
}
