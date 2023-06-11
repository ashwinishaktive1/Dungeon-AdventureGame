package dungeons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Implement a console controller for the Adventure Game Model.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class AdventureGameConsoleController implements AdventureGameController {
  
  private Game game;
  private final Readable in;
  private final Appendable out;
  
  /**
   * Constructor for the controller.
   * 
   * @param rd  the source to read from
   * @param ap the target to print to
   * @param model a non-null adventure game model
   */
  public AdventureGameConsoleController(Readable rd, Appendable ap, Game model) {
    if (model == null) {
      throw new IllegalArgumentException("The game must be setup before starting to play "
          + "game.");
    }
    
    this.game = model;
    this.in = rd;
    this.out = ap;
  }

  @Override
  public void playGame() {
    try {
      Scanner scan = new Scanner(in);
      
      out.append("\n!!!!!!!!-------------------------WELCOME TO THE DUNGEON" 
          + "--------------------------!!!!!!!!\n");
      
      out.append(this.game.getGameStatus());
      out.append("\n-------------------------------------"
          + "------------------------------------\n");
      
      this.game.setupPlayerOne(101);
      if (this.game.getNumberOfPlayers() == 2) {
        this.game.setupPlayerTwo(100);
      }
      out.append(this.game.introducePlayer());
      out.append("\n-------------------------------------"
          + "------------------------------------\n");
      
      this.game.startGame();
      out.append(this.game.getGameStatus());
      out.append("\n-------------------------------------"
          + "------------------------------------\n");
      
      boolean quitGame = false;
      while (!quitGame && !this.game.isGameOver()) {
        out.append("\n" + this.game.getAvailableMoves());
        out.append("\n\n---\n\n");
        
        out.append("What would you like to do? Press:\nM: Move\n"
            + "T: Collect treasures\nA: Pickup arrows\nS: Shoot arrow"
            + "\nQ: Quit game");
        
        String next = scan.next();
        
        if ("M".equalsIgnoreCase(next)) {
          if (!this.movePlayer(scan)) {
            quitGame = true;
          }
        } else if ("T".equalsIgnoreCase(next)) {
          if (!this.collectTreasures(scan)) {
            quitGame = true;
          }
        } else if ("A".equalsIgnoreCase(next)) {
          this.pickArrows();
        } else if ("S".equalsIgnoreCase(next)) {
          if (!this.shoot(scan)) {
            quitGame = true;
          }
        } else if ("Q".equalsIgnoreCase(next)) {
          quitGame = true;
        } else {
          out.append("\nInvalid input!! Please provide proper input!!\n");
        }
      }
      if (!quitGame) {
        out.append("\n-------------------------The game ENDS here---"
            + "----------------------\n");
        out.append(this.game.getGameResult());
      }
      out.append("\n\nThank you for playing. Hope you enjoyed the adventure game!");
    } catch (IOException i) {
      throw new IllegalStateException("Failed during game playing.");
    }
  }
  
  private boolean movePlayer(Scanner scan) {
    try {
      out.append("\nGreat choice! Select a direction to move from the "
          + "available neighbors: Press\nN: North | E: East | W: West | S: South\n"
          + "Quit game by pressing Q");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed while asking for a move.", ioe);
    }
    
    boolean invalidInput = true;
    
    while (invalidInput) {
      String move = scan.next();
      
      if ("N".equalsIgnoreCase(move)) {
        invalidInput = this.tryMove(Direction.NORTH);
      } else if ("E".equalsIgnoreCase(move)) {
        invalidInput = this.tryMove(Direction.EAST);
      } else if ("W".equalsIgnoreCase(move)) {
        invalidInput = this.tryMove(Direction.WEST);
      } else if ("S".equalsIgnoreCase(move)) {
        invalidInput = this.tryMove(Direction.SOUTH);
      } else if ("Q".equalsIgnoreCase(move)) {
        return false;
      }
      
      if (invalidInput) {
        try {
          out.append("\nOh no, that's a dead-end, make another move!! Q for quitting game.\n");
        } catch (IOException ioe) {
          throw new IllegalStateException("Append failed while notifying invalid move.", ioe);
        }
      }
    }
    return true;
  }
  
  private boolean tryMove(Direction dir) {
    try {
      out.append(this.game.move(dir));
      return false;
    } catch (IllegalArgumentException e) {
      return true;
    } catch (IllegalStateException s) {
      try {
        out.append("IllegalStateException: " + s.getMessage());
      } catch (IOException e) {
        throw new IllegalStateException("Append failed while telling player cannot move.", e);
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed while trying move.", ioe);
    }
    return true;
  }
  
  private boolean collectTreasures(Scanner scan) {
    try {
      out.append("\nWow! Let's collect some beautiful gems here. Which ones do you want?\n"
          + "R: Ruby | D: Diamond | S: Sapphire\n\nQuit game by pressing Q\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed while appreciating treasure.", ioe);
    }
    
    boolean invalidInput = true;
    
    while (invalidInput) {
      String move = scan.next();
      List<Treasure> desired = new ArrayList<>();
      if ("R".equalsIgnoreCase(move)) {
        desired.add(Treasure.RUBIES);
        invalidInput = this.tryTreasures(desired);
      } else if ("D".equalsIgnoreCase(move)) {
        desired.add(Treasure.DIAMONDS);
        invalidInput = this.tryTreasures(desired);
      } else if ("S".equalsIgnoreCase(move)) {
        desired.add(Treasure.SAPPHIRE);
        invalidInput = this.tryTreasures(desired);
      } else if ("Q".equalsIgnoreCase(move)) {
        return false;
      }
      if (invalidInput) {
        try {
          out.append("\nInvalid input!! Please provide proper input!! Q for quitting game.\n");
        } catch (IOException ioe) {
          throw new IllegalStateException("Append failed while notifying invalid move.", ioe);
        }
      }
    }
    return true;
  }
  
  private boolean tryTreasures(List<Treasure> desired) {
    try {
      out.append("\n" + this.game.pickUpTreasures(desired) + "\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed while error with treasure pickup.", ioe);
    }
    return false;
  }
  
  private void pickArrows() {
    this.game.pickUpArrows();
    
    try {
      out.append("\n" + this.game.getPlayerStatus() + "\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed while displaying player status.", ioe);
    }
  }
  
  private boolean shoot(Scanner scan) {
    try {
      out.append("\nThat's a fighter spirit! Enter the direction you want to shoot in. Press\n"
          + "N: North | E: East | W: West | S: South\n\nQuit game by pressing Q\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed while asking for direction.", ioe);
    }
    
    Direction shootDir = null;
    boolean invalidDirection = true;
    
    while (invalidDirection) {
      String move = scan.next();
      
      if ("N".equalsIgnoreCase(move)) {
        shootDir = Direction.NORTH;
        invalidDirection = false;
      } else if ("E".equalsIgnoreCase(move)) {
        shootDir = Direction.EAST;
        invalidDirection = false;
      } else if ("W".equalsIgnoreCase(move)) {
        shootDir = Direction.WEST;
        invalidDirection = false;
      } else if ("S".equalsIgnoreCase(move)) {
        shootDir = Direction.SOUTH;
        invalidDirection = false;
      } else if ("Q".equalsIgnoreCase(move)) {
        return false;
      }
      
      if (invalidDirection) {
        try {
          out.append("\nInvalid input!! Please provide proper input!! Q for quitting game.\n");
        } catch (IOException ioe) {
          throw new IllegalStateException("Append failed while notifying invalid direction.", ioe);
        }
      }
    }
    
    try {
      out.append("\nEnter the distance you want to shoot at, e.g. 1, 2, 3, ..."
          + "\n\nQuit game by pressing Q\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed while asking for direction.", ioe);
    }
    
    int shootDis;
    boolean invalidDistance = true;
    
    while (invalidDistance) {
      try {
        shootDis = scan.nextInt();
        invalidDistance = false;
        this.tryShoot(shootDir, shootDis);
      } catch (InputMismatchException m) {
        if (scan.next().equalsIgnoreCase("Q")) {
          return false;
        }
        try {
          out.append("\nInvalid input!! Please provide proper input!! Q for quitting game.\n");
        } catch (IOException ioe) {
          throw new IllegalStateException("Append failed while notifying invalid distance.", ioe);
        }
      }
    }
    return true;
  }
  
  private void tryShoot(Direction shootDir, int shootDis) {
    try {
      out.append(this.game.shootArrow(shootDir, shootDis));
    } catch (IllegalArgumentException e) {
      try {
        out.append("IllegalArgumentException: " + e.getMessage());
      } catch (IOException ioe) {
        throw new IllegalStateException("Append failed while telling player cannot move.", ioe);
      }
    } catch (IllegalStateException s) {
      try {
        out.append("IllegalStateException: " + s.getMessage());
      } catch (IOException ioe) {
        throw new IllegalStateException("Append failed while telling player cannot move.", ioe);
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed while printing shoot output.", ioe);
    }
  }

  @Override
  public void handleCellClick(Direction dir) {
    // do nothing    
  }
}
