package dungeons;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * An implementation of the GameView interface. This class defines the structure of the 
 * user interface. 
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class GameViewImpl extends JFrame implements GameView {

  private ReadonlyGameModel model;
  private AdventureGameController control;
  private MenuBar menuBar;
  private HomePagePanel homePanel;
  private GameStatusPanel statusPanel;
  private JPanel moveStatusPanel;
  private JPanel turnPanel;
  private JLabel gameStatus;
  private JLabel gameTurn;
  private GamePanel gamePanel;
  private Font commonFont;
  private JLabel numPlayers;
  private JSpinner players;
  private JSpinner rows;
  private JSpinner cols;
  private JSpinner treasure;
  private JSpinner monsters;
  private JSpinner interconnectivity;
  private JComboBox wrapping;
  private JButton startGame;
    
  /**
   * Constructs the view implementation. 
   * 
   * @param modelForView a read-only copy of the model
   * @throws IllegalArgumentException if the model is null
   */
  public GameViewImpl(ReadonlyGameModel modelForView) 
      throws IllegalArgumentException {
    super("Play a game: Adventure Maze Game");
    if (modelForView == null) {
      throw new IllegalArgumentException("The model cannot be null.");
    }
    this.model = modelForView;
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setResizable(false);
    this.commonFont = new Font("Arial", Font.BOLD, 21);
    
    this.displayHomePage();  
            
    this.moveStatusPanel = new JPanel();
    this.turnPanel = new JPanel();
    this.gameStatus = new JLabel("Game status");
    this.gameTurn = new JLabel("Turn");
    
    this.displayVisible();
  }

  @Override
  public void addListeners(ActionListener action, KeyListener keyboard) {
    this.addKeyListener(keyboard);
    this.startGame.addActionListener(action);
    this.menuBar.addListeners(action);
  }
  
  @Override
  public void displayVisible() {
    this.setVisible(true);
  }
  
  @Override
  public void displayHomePage() {
    this.setSize(700, 700);
    menuBar = new MenuBar();
    this.setJMenuBar(menuBar);
    
    this.homePanel = new HomePagePanel();
    
    // Number of players
    this.numPlayers = new JLabel("How many players?");
    this.numPlayers.setBounds(60, 100, 300, 40);
    this.numPlayers.setFont(this.commonFont);
    this.numPlayers.setForeground(Color.WHITE);
    homePanel.add(numPlayers);
    
    players = new JSpinner(new SpinnerNumberModel(1, 1, 2, 1));
    players.setBounds(450, 100, 50, 40);
    this.players.setFont(this.commonFont);
        
    // Number of rows
    this.numPlayers = new JLabel("Number of rows in dungeon:");
    this.numPlayers.setBounds(60, 150, 300, 40);
    this.numPlayers.setFont(this.commonFont);
    this.numPlayers.setForeground(Color.WHITE);
    homePanel.add(numPlayers);
    
    rows = new JSpinner(new SpinnerNumberModel(5, 4, 12, 1));
    rows.setBounds(450, 150, 50, 40);
    this.rows.setFont(this.commonFont);
    
    // Number of columns
    this.numPlayers = new JLabel("Number of columns in dungeon:");
    this.numPlayers.setBounds(60, 200, 350, 40);
    this.numPlayers.setFont(this.commonFont);
    this.numPlayers.setForeground(Color.WHITE);
    homePanel.add(numPlayers);
    
    cols = new JSpinner(new SpinnerNumberModel(5, 4, 12, 1));
    cols.setBounds(450, 200, 50, 40);
    this.cols.setFont(this.commonFont);
    
    // Interconnectivity
    this.numPlayers = new JLabel("Interconnectivity:");
    this.numPlayers.setBounds(60, 250, 350, 40);
    this.numPlayers.setFont(this.commonFont);
    this.numPlayers.setForeground(Color.WHITE);
    homePanel.add(numPlayers);
    
    interconnectivity = new JSpinner(new SpinnerNumberModel(4, 1, 10, 1));
    interconnectivity.setBounds(450, 250, 50, 40);
    this.interconnectivity.setFont(this.commonFont);
    
    // Treasure spread
    this.numPlayers = new JLabel("Treasure and arrow distribution (%):");
    this.numPlayers.setBounds(60, 300, 400, 40);
    this.numPlayers.setFont(this.commonFont);
    this.numPlayers.setForeground(Color.WHITE);
    homePanel.add(numPlayers);
    
    treasure = new JSpinner(new SpinnerNumberModel(30, 0, 100, 1));
    treasure.setBounds(450, 300, 70, 40);
    this.treasure.setFont(this.commonFont);
    
    // Difficulty
    this.numPlayers = new JLabel("Number of Monsters:");
    this.numPlayers.setBounds(60, 350, 400, 40);
    this.numPlayers.setFont(this.commonFont);
    this.numPlayers.setForeground(Color.WHITE);
    homePanel.add(numPlayers);
    
    monsters = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    monsters.setBounds(450, 350, 70, 40);
    this.monsters.setFont(this.commonFont);
    
    // Dungeon types: wrapped or unwrapped?
    this.numPlayers = new JLabel("Dungeon type:");
    this.numPlayers.setBounds(60, 400, 400, 40);
    this.numPlayers.setFont(this.commonFont);
    this.numPlayers.setForeground(Color.WHITE);
    homePanel.add(numPlayers);
    
    String[] type = {"Wrapped", "Unwrapped"};
    wrapping = new JComboBox(type);
    wrapping.setBounds(450, 400, 150, 40);
    this.wrapping.setFont(this.commonFont);
        
    // Start game
    this.startGame = new JButton("Start Game!");
    this.startGame.setBounds(250, 470, 200, 30);
    this.startGame.setFont(this.commonFont);
    this.startGame.setBackground(Color.lightGray);
    this.startGame.setForeground(Color.black);
    this.startGame.setActionCommand("Start Game!");
    
    homePanel.add(players);
    homePanel.add(rows);
    homePanel.add(cols);
    homePanel.add(interconnectivity);
    homePanel.add(treasure);
    homePanel.add(wrapping);
    homePanel.add(monsters);
    homePanel.add(startGame);
    this.add(this.homePanel);
    this.refresh();
    this.displayVisible();
  }

  @Override
  public String[] getGameArgs() {
    String[] args = new String[7];
    
    args[0] = this.players.getValue().toString();
    args[1] = this.rows.getValue().toString();
    args[2] = this.cols.getValue().toString();
    args[3] = this.interconnectivity.getValue().toString();
    args[4] = this.wrapping.getSelectedItem().toString();
    args[5] = this.treasure.getValue().toString();
    args[6] = this.monsters.getValue().toString();
    
    return args;
  }
  
  @Override
  public String getSinglePlayerDetail() {
    String playerId = JOptionPane.showInputDialog(this, "Enter Player 1 ID (Range 0 - 9)", 1);
    if (playerId != null) {
      if (Integer.parseInt(playerId) > 9) {
        playerId = "1";
      }
      return playerId;
    } else {
      displayHomePage();
    }
    return "";
  }
  
  @Override
  public String getDoublePlayerDetail() {
    String playerId = JOptionPane.showInputDialog(this, "Enter Player 2 ID (Range 0 - 9)", 2);
    if (Integer.parseInt(playerId) > 9) {
      playerId = "2";
    }
    return playerId;
  }
  
  @Override
  public void addControllerListener(AdventureGameController listener) {
    this.control = listener;
  }

  @Override
  public void displayMazeGame() {
    this.getContentPane().removeAll();
    this.setFocusable(true);
    this.requestFocusInWindow();
    this.setSize(800, 550);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.setResizable(false);
    this.statusPanel = new GameStatusPanel(this.model);
    this.gamePanel = new GamePanel(this.control, this.model);
    this.gamePanel.addListener();
    JScrollPane scrollBar = new JScrollPane(this.gamePanel, 
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollBar.getViewport().setMinimumSize(new Dimension(50, 200));
    scrollBar.getViewport().setPreferredSize(new Dimension(400, 100));
    this.add(scrollBar);
    this.add(this.statusPanel, BorderLayout.EAST);
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void setModel(ReadonlyGameModel updatedModel) throws IllegalArgumentException {
    if (updatedModel == null) {
      throw new IllegalArgumentException("Updated model cannot be null.");
    }
    this.model = updatedModel;
  }

  @Override
  public void quitGame() {
    this.dispose();
  }

  @Override
  public void displayGameStatus(String status) {
    this.clearStatus();
    this.gameStatus.setText(status);
    this.gameStatus.setFont(this.commonFont);
    this.add(gameStatus, BorderLayout.NORTH);
  }

  @Override
  public void clearStatus() {
    this.moveStatusPanel.remove(gameStatus);
    this.moveStatusPanel.removeAll();
  }

  @Override
  public void displayGameTurn() {
    this.turnPanel.remove(gameTurn);
    this.turnPanel.removeAll();
    this.gameTurn.setText("It is " + this.model.getTurn().getPlayerId() + "'s turn!");
    this.gameTurn.setFont(this.commonFont);
    this.add(gameTurn, BorderLayout.SOUTH);
  }  

  @Override
  public void repaintView() {
    gamePanel.setupGamePanel();
    statusPanel.setupStatusPanel();
    if (this.model.isGameOver()) {
      this.displayGameStatus(this.model.getGameResult());
      JOptionPane.showMessageDialog(this, "What a great game! You may now start a new game, "
          + "restart the same game, or quit from the Menubar.");
      this.displayGameStatus("Let's play again, start a new game from menu.");
    }
    this.setVisible(true);    
  }

  @Override
  public void clearGame() {
    this.getContentPane().removeAll();
    this.removeKeyListener(this.getKeyListeners()[0]);
  }

  @Override
  public void clearGameArgs() {
    this.remove(players);
    this.remove(rows);
    this.remove(cols);
    this.remove(interconnectivity);
    this.remove(treasure);
    this.remove(wrapping);
    this.remove(monsters);
    this.remove(homePanel);
  }
}