package dungeons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An implementation of AdventureGame controller communicating with the view and model.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class AdventureGameViewController implements AdventureGameController, 
    ActionListener, KeyListener {
  
  private RandomNumberGenerator random;
  private int randomSeed;
  private GameView view;
  private Game model;
  private List<Integer> viewScan;
  private String[] config;
  
  /**
   * A constructor to create a controller linked to the view to manage I/O from 
   * the user through a GUI.
   * 
   * @param currentView Game view
   * @param currentModel Game model
   * @param rand Random Number Generator
   * @throws IllegalArgumentException if the view or model or RandomNumberGenerator is null
   */
  public AdventureGameViewController(GameView currentView, Game currentModel, 
      RandomNumberGenerator rand) throws IllegalArgumentException {
    if (currentView == null) {
      throw new IllegalArgumentException("The adventure game view cannot be null.");
    }
    if (currentModel == null) {
      throw new IllegalArgumentException("The adventure game model cannot be null.");
    }
    if (rand == null) {
      throw new IllegalArgumentException("The RandomNumberGenerator cannot be null.");
    }
    
    this.random = rand;
    this.view = currentView;
    this.model = currentModel;
    this.viewScan = new ArrayList<>();
  }

  @Override
  public void playGame() {
    this.view.addListeners(this, this);
    this.view.displayVisible();
    this.view.refresh();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Start Game!" :
        config = this.view.getGameArgs();
        this.view.clearGameArgs();
        this.randomSeed = new Random().nextInt();
        int numPlayers = this.updateModel(config);
        this.view.setModel(model);
        try {
          this.model.setupPlayerOne(Integer.parseInt(this.view.getSinglePlayerDetail()));
        } catch (NumberFormatException n) {
          this.view.clearGame();
          this.view.displayHomePage();
          this.view.addListeners(this, this);
          break;
        }
        if (numPlayers == 2) {
          try {
            this.model.setupPlayerTwo(Integer.parseInt(this.view.getDoublePlayerDetail()));
          } catch (NumberFormatException n) {
            this.view.clearGame();
            this.view.displayHomePage();
            this.view.addListeners(this, this);
            break;
          }
        }
        this.model.startGame();
        this.view.addControllerListener(this);
        this.view.displayMazeGame();
        this.view.displayGameStatus("Welcome to the Dungeon!!");
        this.view.displayGameTurn();
        this.view.refresh();
        break;
      case "New game" :
        this.view.clearGame();
        this.view.displayHomePage();
        this.view.addListeners(this, this);
        break;
      case "Restart game" :
        this.restartGame();
        this.view.addListeners(this, this);
        break;
      case "Quit" :
        this.view.quitGame();
        break;
      default : 
        break;
    }
  }
  
  private int updateModel(String[] args) {
    if (args.length != 7) {
      throw new IllegalArgumentException("Incorrect command-line arguments.");
    }
    
    this.random.setSeed(randomSeed);
    
    int numOfPlayers = Integer.parseInt(args[0]);
    int row = Integer.parseInt(args[1]);
    int col = Integer.parseInt(args[2]);
    int interconnectivity = Integer.parseInt(args[3]);
    boolean wrapped;
    if (args[4].equals("Wrapped")) {
      wrapped = true;
    } else {
      wrapped = false;
    }
    
    double treasure = Double.parseDouble(args[5]);
    int difficulty = Integer.parseInt(args[6]);
    
    // Initializing an adventure game with requested game features.
    this.model = new AdventureGame(numOfPlayers, row, col, interconnectivity, wrapped, 
          treasure, difficulty, random);
    
    return numOfPlayers;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // do nothing
  }

  @Override
  public void keyPressed(KeyEvent e) {
    viewScan.add(e.getKeyCode());
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        this.tryMove(Direction.NORTH);
        break;
      case KeyEvent.VK_DOWN:
        this.tryMove(Direction.SOUTH);
        break;
      case KeyEvent.VK_LEFT:
        this.tryMove(Direction.WEST);
        break;
      case KeyEvent.VK_RIGHT:
        this.tryMove(Direction.EAST);
        break;
      case KeyEvent.VK_W:
        this.tryShoot(Direction.NORTH);
        break;
      case KeyEvent.VK_S:
        this.tryShoot(Direction.SOUTH);
        break;
      case KeyEvent.VK_A:
        this.tryShoot(Direction.WEST);
        break;
      case KeyEvent.VK_D:
        this.tryShoot(Direction.EAST);
        break;
      case KeyEvent.VK_T:
        this.tryTreasures();
        break;
      case KeyEvent.VK_P:
        this.pickArrows();
        break;
      default:
        break;
    }
    view.refresh();
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // do nothing
  }
    
  private String tryMove(Direction dir) {
    try {
      displayStatus(this.model.move(dir));
      viewScan.clear();
      view.repaintView();
      view.displayGameTurn();
      return "Success";
    } catch (IllegalArgumentException e) {
      displayStatus("You hit a wall, cannot move there!");
      return "Wrong";
    } catch (IllegalStateException s) {
      displayStatus(s.getMessage());
      return "Dead";
    }
  }
  
  private void tryShoot(Direction dir) {
    int shootDis = 1;
    if (viewScan.size() != 0) {
      shootDis = Integer.parseInt(KeyEvent.getKeyText(viewScan.get(0)));
      try {
        displayStatus(this.model.shootArrow(dir, shootDis));
        viewScan.clear();
        view.repaintView();
        view.displayGameTurn();
      } catch (IllegalArgumentException e) {
        displayStatus(e.getMessage());
      } catch (IllegalStateException s) {
        displayStatus(s.getMessage());
      }
    }
  }
  
  private void tryTreasures() {
    List<Treasure> type = new ArrayList<>();
        
    if (viewScan.size() != 0) {
      switch (viewScan.get(0)) {
        case KeyEvent.VK_1:
          type.add(Treasure.DIAMONDS);
          displayStatus(this.model.pickUpTreasures(type).split("\n")[0]);
          break;
        case KeyEvent.VK_2:
          type.add(Treasure.RUBIES);
          displayStatus(this.model.pickUpTreasures(type).split("\n")[0]);
          break;
        case KeyEvent.VK_3:
          type.add(Treasure.SAPPHIRE);
          displayStatus(this.model.pickUpTreasures(type).split("\n")[0]);
          break;
        default:
      }
      viewScan.clear();
      view.repaintView();
      view.displayGameTurn();
    }  
  }
  
  private void pickArrows() {
    this.model.pickUpArrows();
    displayStatus(this.model.getPlayerStatus().split("\n")[1]);
    viewScan.clear();
    view.repaintView();
    view.displayGameTurn();
  }
  
  private void displayStatus(String status) {
    this.view.displayGameStatus("");
    this.view.displayGameStatus(status);
  }
  
  private void restartGame() {
    int numPlayers = this.updateModel(config);
    this.view.setModel(model);
    int id = Integer.parseInt(this.view.getSinglePlayerDetail());
    this.model.setupPlayerOne(id);
    if (numPlayers == 2) {
      id = Integer.parseInt(this.view.getDoublePlayerDetail());
      this.model.setupPlayerTwo(id);
    }
    this.view.clearGame();
    this.model.startGame();
    this.view.displayMazeGame();
    this.view.displayGameStatus("Welcome back to the Dungeon!!");
    this.view.displayGameTurn();
    this.view.refresh();
  }

  @Override
  public void handleCellClick(Direction dir) {
    this.tryMove(dir);
  }
}
