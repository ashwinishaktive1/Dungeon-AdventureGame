package dungeons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An adventure game is implementation of the game, with a maze dungeon 
 * and a currPlayer. The currPlayer starts from the start cave and moves to the 
 * end cave. The currPlayer may also pickup treasures if present in the cave.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class AdventureGame implements Game {
  
  private RandomNumberGenerator random;
  private Dungeons dungeon;
  private int numOfPlayers;
  private Players currPlayer;
  private List<Players> players;
  private Players winner;
  private Location start;
  private Location end;
  private Boolean isPlayerHere;
  private Boolean hasGameStarted;
  private Boolean isGameOver;
  private Boolean didPlayerReachEnd;
  private List<String> visitedLocations;
  
  /**
   * Construct an adventure game for the view. 
   * 
   * @param rand Random Number Generator object
   */
  public AdventureGame(RandomNumberGenerator rand) {
    if (rand == null) {
      throw new IllegalArgumentException("Random Number Generator "
          + "must be of type RandomNumberGenerator.");
    }
    
    this.random = rand;
  }
  
  /**
   * Construct a Adventure Game with a dungeon and required treasure 
   * spread.
   * 
   * @param numPlayers Number of players playing the game
   * @param rows 2D grid position row number
   * @param columns 2D grid position column number
   * @param interconnectivity interconnectivity in the dungeon
   * @param wrapping should the dungeon be wrapped
   * @param treasureSpread amount of caves the treasure must be present in
   * @param difficulty number of monsters
   * @param rand Random Number Generator object
   * @throws IllegalArgumentException if the number of players is not equal to 1 or 2 
   *                                  or the number of rows or columns is less 
   *                                  than 0 or if the interconnectivity 
   *                                  is less than 0 or if the treasure spread 
   *                                  is less than 0 or more than 0 or if the 
   *                                  random number generator is a null object
   */
  public AdventureGame(int numPlayers, int rows, int columns, int interconnectivity, 
      boolean wrapping, double treasureSpread, int difficulty, 
      RandomNumberGenerator rand) throws IllegalArgumentException {
    if (numPlayers != 1 && numPlayers != 2) {
      throw new IllegalArgumentException("The number of players must either be 1 or 2.");
    }
    if (rows < 1 || columns < 1) {
      throw new IllegalArgumentException("The number of rows and "
          + "columns must be greater than 0. Illegal Arguments.");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Interconnectivity "
          + "must be atleast 1.");
    }
    if (treasureSpread < 0 || treasureSpread > 100) {
      throw new IllegalArgumentException("Treasure spread must "
          + "be a percentage value ranging from 0 to 100.");
    }
    if (rand == null) {
      throw new IllegalArgumentException("Random Number Generator "
          + "must be of type RandomNumberGenerator.");
    }
    
    this.random = rand;
    this.dungeon = new MazeDungeon(rows, columns, interconnectivity, 
        wrapping, treasureSpread, difficulty, this.random);
    this.numOfPlayers = numPlayers;
    this.currPlayer = null;
    this.players = new ArrayList<>();
    this.start = null;
    this.end = null;
    this.isPlayerHere = false;
    this.hasGameStarted = false;
    this.isGameOver = false;
    this.didPlayerReachEnd = false;
    this.visitedLocations = new ArrayList<>();
  }

  @Override
  public void setupPlayerOne(int id) {
    this.players.add(new Player(id));
    this.currPlayer = this.players.get(0);
    this.isPlayerHere = true;
  }
  
  @Override
  public void setupPlayerTwo(int id) {
    if (this.numOfPlayers == 1) {
      throw new IllegalStateException("The number of players set for the game is 1.");
    }
    if (this.currPlayer == null) {
      throw new IllegalStateException("Player 1 must be setup first.");
    }
    if (("Player " + id).equals(this.players.get(0).getPlayerId())) {
      throw new IllegalArgumentException("Please choose a different id for second player.");
    }
    
    this.players.add(new Player(id));
  }

  @Override
  public int getNumberOfPlayers() {
    return this.numOfPlayers;
  }  

  @Override
  public Players getTurn() {
    return this.currPlayer;
  }
  
  @Override
  public String getGameStatus() {
    if (!this.hasGameStarted && !this.isGameOver && (this.players.size() == 0)) {
      return "The game is yet to begin! Players not ready.";
    } else if (!this.hasGameStarted && !this.isGameOver && (this.players.size() == 1)) {
      return "The game is yet to begin! " + this.players.get(0).getPlayerId() + " is ready.";
    } else if (!this.hasGameStarted && !this.isGameOver && (this.players.size() == 2)) {
      return "The game is yet to begin! " + this.players.get(0).getPlayerId() + " and " 
          + this.players.get(1).getPlayerId() + " are ready.";
    } else if (this.hasGameStarted && !this.isGameOver) {
      return "The game is ongoing, player is in search of the end!";
    } else {
      if (this.didPlayerReachEnd) {
        if (!this.currPlayer.isAlive()) {
          return "The " + this.currPlayer.getPlayerId() + " successfully reach the end, "
              + "but was killed by the last Otyugh. The game has ended.";
        }
        return "The " + this.currPlayer.getPlayerId() + " successfully reach the end. "
            + "The game has ended.";
      } else {
        return "The player was unable to reach the end. The game has ended.";
      }
    }
  }

  @Override
  public void startGame() throws IllegalStateException {
    if (this.numOfPlayers == 1 && this.currPlayer == null) {
      throw new IllegalStateException("The player must be ready before "
          + "starting the game.");
    }
    if (this.numOfPlayers == 2 && this.players.size() < 2) {
      throw new IllegalStateException("All players must be ready before starting the game.");
    }
    this.dungeon.assignStartAndEnd();
    this.start = this.dungeon.getStart();
    this.end = this.dungeon.getEnd();
    this.start.addPlayer(currPlayer);
    this.visitedLocations.add(this.start.getLocationId());
    if (this.numOfPlayers == 2) {
      this.start.addPlayer(this.players.get(1));
    }
    this.hasGameStarted = true;
  }

  @Override
  public String introducePlayer() {
    return this.getGameStatus() + " \n" + this.currPlayer.getPlayerDescription();
  }

  @Override
  public String getAvailableMoves() {
    return this.currPlayer.getCurrentLocationDetails();
  }

  @Override
  public String pickUpTreasures(List<Treasure> desiredTreasure) {
    String earlier = this.currPlayer.getPlayerDescription();
    
    try {
      this.currPlayer.pickTreasures(desiredTreasure);
    } catch (IllegalStateException s) {
      return s.getMessage();
    }
        
    String after = this.currPlayer.getPlayerDescription();
    
    if (!earlier.equals(after)) {
      return "The " + this.currPlayer.getPlayerId() + " has collected the available " 
          + desiredTreasure + " from the cave.\n"
          + this.currPlayer.getPlayerDescription();
    }
    return "The " + this.currPlayer.getPlayerId() + " could not pick up the desired treasures. \n" 
              + this.currPlayer.getPlayerDescription();
  }
  
  @Override
  public void pickUpArrows() {
    try {
      this.currPlayer.pickupArrows();
    } catch (IllegalStateException s) {
      // do nothing
    }
  }

  @Override
  public String shootArrow(Direction direction, int distance) 
      throws IllegalArgumentException, IllegalStateException {
    if (!this.isPlayerHere) {
      throw new IllegalStateException("Player is not ready.");
    }
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null to shoot arrow.");
    }
    if (distance < 1) {
      throw new IllegalArgumentException("Arrow must be aimed to a location at "
          + "least one position away.");
    }
    
    try {
      boolean success = false;
      currPlayer.useArrow();
      
      Location curr = this.currPlayer.getCurrentLocation();
      
      int i = 0;   
      
      while (i < distance) {     
        if (curr.getLocationId().charAt(0) == 'C') {
          // Arrow in currPlayer's position cave, don't add to arrow distance
          if (curr == this.currPlayer.getCurrentLocation()) {
            i += 0;
          } else {
            // Arrow in other cave, add to the distance
            i += 1;
            // Reached the end destination for arrow
            if (i == distance) {
              // If monster in destination, successful shot
              if (curr.getMonster() != null) {
                curr.getMonster().hit();
                if (this.checkIfMonsterDead(curr.getMonster())) {
                  curr.removeMonster(curr.getMonster());
                }
                success = true;
              } 
              break;
            }
          }
          
          // If neighbor exists in direction
          if (curr.getNeighbors().containsKey(direction)) {
            // move the arrow to new location
            curr = curr.getNeighbors().get(direction);
          } else {
            // no neighbor in required direction, arrow hits the wall
            success = false;
            break;
          }
        } else { 
          for (Direction dir : curr.getNeighbors().keySet()) {
            // find the other direction
            if (dir != this.oppositeDirection(direction)) {
              direction = dir;
              // move the arrow to new position
              curr = curr.getNeighbors().get(direction);
              break;
            }
          }
        }
      }
      this.hasGameEnded();
      // Swapping turn after player shoots.
      this.swapTurn();
      if (success) {
        return "\nYou hear a great howl in the distance\n";
      } else {
        return "\nYou shoot an arrow into the darkness\n";
      }
    } catch (IllegalStateException s) {
      if (s.getMessage().equals("Player is dead, cannot attack.")) {
        return "\nOops, " + this.currPlayer.getPlayerId() + " is dead, cannot shoot!\n";
      } else {
        return "\n" + this.currPlayer.getPlayerId() + " does not have any arrows to shoot, "
            + "collect more!\n";
      }
    }
  }
  
  /**
   * Swap turns after each move or arrow shoot. The turns are swapped only if the next 
   * player is alive.
   */
  private void swapTurn() {
    if (this.numOfPlayers == 2) {
      Players playerOne = this.players.get(0);
      Players playerTwo = this.players.get(1);
      
      Players nextPlayer = null;
      nextPlayer = this.currPlayer == playerOne ? playerTwo : playerOne;
      
      if (nextPlayer.isAlive()) {
        this.currPlayer = nextPlayer;
      }
    }
  }
  
  /**
   * Get the entry point for a tunnel.
   * 
   * @param come arrow incoming direction
   * @return direction of the entrance for the tunnel
   */
  private Direction oppositeDirection(Direction come) {
    if (come == Direction.NORTH) {
      return Direction.SOUTH;
    } else if (come == Direction.SOUTH) {
      return Direction.NORTH;
    } else if (come == Direction.EAST) {
      return Direction.WEST;
    } else {
      return Direction.EAST;
    }
  }
  
  @Override
  public String move(Direction towards) throws IllegalStateException, 
      IllegalArgumentException {
    if (this.isGameOver) {
      throw new IllegalStateException("The game has ended, please view "
          + "the result.");
    }    
    String moveResult = "";
    
    Location currentLocation = this.currPlayer.getCurrentLocation();
    Map<Direction, Location> currentNeighbors = currentLocation.getNeighbors();
    if (currentNeighbors.keySet().contains(towards)) {
      try {
        currentNeighbors.get(towards).addPlayer(currPlayer);
        currentLocation.removePlayer(this.currPlayer);
        this.visitedLocations.add(currPlayer.getCurrentLocation().getLocationId());
        moveResult = this.fightBetweenMonsterAndPlayer();
      } catch (IllegalStateException s) {
        return s.getMessage();
      }
      this.hasGameEnded();
    } else {
      throw new IllegalArgumentException("Illegal move.");
    }
    
    // Swapping turn after player moves.
    this.swapTurn();
    return moveResult;
  }
  
  private String fightBetweenMonsterAndPlayer() {
    // Check only if cave
    if (this.currPlayer.getCurrentLocation().getLocationId().charAt(0) == 'C') {
      Monsters possibleMonster = this.currPlayer.getCurrentLocation().getMonster();
      // if monster present
      if (possibleMonster != null) {
        // check for health
        String monsterHealth = possibleMonster.getStatus();
        // Monster healthy
        if ((possibleMonster.getMonsterId() + " is standing strong.").equals(monsterHealth)) {
          this.currPlayer.killPlayer();
          return "Chomp, chomp, chomp, " + this.currPlayer.getPlayerId() + " eaten by an Otyugh!";
        } else if ((possibleMonster.getMonsterId() + " is weak, "
            + "hit again and it dies!").equals(monsterHealth)) {
          // Monster hurt
          int pick = random.getRandomNumber(0, 2);
          if (pick == 0) {
            return "Monster hurt: " + this.currPlayer.getPlayerId() + " escaped this "
                + "time by luck, move quick!";
          } else {
            this.currPlayer.killPlayer();
            return "Chomp, chomp, chomp, " + this.currPlayer.getPlayerId() + " eaten by an Otyugh!";
          }
        } else {
          // No monster: do nothing
        }
      }
    }
    return "";
  }
  
  private boolean checkIfMonsterDead(Monsters monsInCave) {
    if ((monsInCave.getMonsterId() + " is dead!!").equals(monsInCave.getStatus())) {
      return true;
    }
    return false;
  }

  @Override
  public String getPlayerStatus() {
    return this.currPlayer.getPlayerDescription();
  }

  @Override
  public String getGameResult() {
    if (!this.isGameOver) {
      return "The game is ongoing. Please recheck after ending the game.";
    }
    if (this.winner != null) {
      return "Awesome! The " + this.winner.getPlayerId() + " has won the game.";
    }
    if (!this.currPlayer.isAlive()) {
      return "Oh no, " + this.currPlayer.getPlayerId() + " died before winning over "
          + "the end location!!";
    }
    return "Oops, looks like a long run! The player is stuck in the maze.";
  }
  
  /**
   * Check if the game has ended for single player and two player games.
   */
  private void hasGameEnded() {
    // Single player game
    if (this.numOfPlayers == 1) {
      if (!this.currPlayer.isAlive()) {
        this.isGameOver = true;
      }
      if (this.currPlayer.getCurrentLocation().getLocationId().equals(this.end.getLocationId())) {
        // Player reaches the end, game is over by either currPlayer winning or by being killed
        this.isGameOver = true;
        this.didPlayerReachEnd = true;
        // Player reached end and is alive
        if (this.currPlayer.isAlive()) {
          this.winner = this.currPlayer;
        }
      }
    } else {
      // Two player game
      if (!this.players.get(0).isAlive() && !this.players.get(1).isAlive()) {
        this.isGameOver = true;
      }
      // Current player kills the monster in the end cave
      if (this.end.getMonster() == null) {
        this.isGameOver = true;
        this.winner = this.currPlayer;
      }
    }   
  }

  @Override
  public boolean isGameOver() {
    return this.isGameOver;
  }

  @Override
  public Players getWinner() {
    return this.winner;
  }

  @Override
  public Players getPlayerOne() {
    return this.players.get(0);
  }

  @Override
  public Players getPlayerTwo() throws IllegalStateException {
    if (this.numOfPlayers != 2) {
      return null;
    }
    return this.players.get(1);
  }

  @Override
  public String getPlayerArrows(String playerId) {
    for (Players p : this.players) {
      if (playerId.equals(p.getPlayerId())) {
        return Integer.toString(p.getArrowsInHand());
      }
    }
    return "";
  }

  @Override
  public Map<Treasure, Integer> getPlayerTreasures(String playerId) {
    for (Players p : this.players) {
      if (playerId.equals(p.getPlayerId())) {
        // Deep copy of map
        Map<Treasure, Integer> mapCopy = p.getTreasuresInBag().entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        return mapCopy;
      }
    }
    return null;
  }

  @Override
  public List<List<Location>> getDungeon() {
    return this.dungeon.getAllLocations();
  }

  @Override
  public List<String> getVisitedLocations() {
    return this.visitedLocations;
  }

  @Override
  public String getPlayerOneLocation() {
    return this.players.get(0).getCurrentLocation().getLocationId();
  }

  @Override
  public String getPlayerTwoLocation() throws IllegalStateException {
    return this.players.get(1).getCurrentLocation().getLocationId();
  }
}
