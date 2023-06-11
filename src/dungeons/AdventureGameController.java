package dungeons;

/**
 * Represents a Controller for Adventure Game: handle user moves by executing them using the model;
 * convey move outcomes to the user in some form.
 */
public interface AdventureGameController {

  /**
   * Execute a single game of adventure maze game given a model. When the game is over,
   * the playGame method ends.
   */
  void playGame();
  
  /**
   * Handle an action in a relative direction to the current location of the dungeon, such as to 
   * make a move.
   *
   * @param dir Direction in which mouse click is observed
   */
  void handleCellClick(Direction dir);
}