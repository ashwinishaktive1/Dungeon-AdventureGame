package dungeons;

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Design the menu bar of the game application.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class MenuBar extends JMenuBar {
  
  private static JMenuBar menuBar;
  private static JMenu menu;
  private static JMenuItem newGame;
  private static JMenuItem restartGame;
  private static JMenuItem close;
  ActionListener mouse;
  
  /**
   * Construct a menu bar with required menu items including 
   * new game, restart game and quit options.
   */
  public MenuBar() {
    menuBar = new JMenuBar();
    menu = new JMenu("Menu");
    menuBar.add(menu);
    
    newGame = new JMenuItem("Start a new game");
    restartGame = new JMenuItem("Restart the game");
    close = new JMenuItem("Quit game");
    
    menu.add(newGame);
    menu.add(restartGame);
    menu.add(close);
    
    this.add(menuBar);
  }
  
  /**
   * Adding listeners to each of the menubar elements.
   * 
   * @param mouse Action listener capture the button clicks
   */
  public void addListeners(ActionListener mouse) {
    close.addActionListener(mouse);
    close.setActionCommand("Quit");
    newGame.addActionListener(mouse);
    newGame.setActionCommand("New game");
    restartGame.addActionListener(mouse);
    restartGame.setActionCommand("Restart game");
  }

}
