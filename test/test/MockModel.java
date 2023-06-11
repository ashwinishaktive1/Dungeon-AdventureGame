package test;

import dungeons.Direction;
import dungeons.Game;
import dungeons.Location;
import dungeons.Players;
import dungeons.Treasure;
import java.util.List;
import java.util.Map;

/**
 * A mock model to just check if the methods are called and used when 
 * necessary.
 * 
 * @author Ashwini Shaktivel Kumar
 * 
 */
public class MockModel implements Game {
  
  private List<String> log;
  private int numOfPlayers;
  private String turn;
  
  /**
   * Constructor to initiate a mock model with no operations.
   *
   * @param log testing log to check if all functions are called correctly
   * @param numPlayers test number of players
   */
  public MockModel(List<String> log, int numPlayers) {
    this.log = log;
    this.numOfPlayers = numPlayers;
    this.turn = "Player 1";
  }
  
  private void switchTurn() {
    if (numOfPlayers == 2) {
      if ("Player 1".equals(turn)) {
        this.turn = "Player 2";
      } else {
        this.turn = "Player 1";
      }
    }
  }
  
  @Override
  public int getNumberOfPlayers() {
    this.log.add("getNumberOfPlayers() called.");
    return 0;
  }

  @Override
  public Players getTurn() {
    this.log.add("getTurn() called.");
    return null;
  }

  @Override
  public String getGameStatus() {
    this.log.add("getGameStatus() called.");
    return null;
  }

  @Override
  public String introducePlayer() {
    this.log.add("introducePlayer() called.");
    return null;
  }

  @Override
  public String getAvailableMoves() {
    this.log.add("getAvailableMoves() called.");
    return null;
  }

  @Override
  public String getPlayerStatus() {
    this.log.add("getPlayerStatus() called.");
    return null;
  }

  @Override
  public String getGameResult() {
    this.log.add("getGameResult() called.");
    return null;
  }

  @Override
  public boolean isGameOver() {
    this.log.add("isGameOver() called.");
    return false;
  }

  @Override
  public Players getWinner() {
    this.log.add("getWinner() called.");
    return null;
  }

  @Override
  public Players getPlayerOne() {
    this.log.add("getPlayerOne() called.");
    return null;
  }

  @Override
  public Players getPlayerTwo() throws IllegalStateException {
    this.log.add("getPlayerTwo() called.");
    return null;
  }

  @Override
  public String getPlayerOneLocation() {
    this.log.add("getPlayerOneLocation() called.");
    return null;
  }

  @Override
  public String getPlayerTwoLocation() throws IllegalStateException {
    this.log.add("getPlayerTwoLocation() called.");
    return null;
  }

  @Override
  public String getPlayerArrows(String playerId) {
    this.log.add("getPlayerArrows() called.");
    return null;
  }

  @Override
  public Map<Treasure, Integer> getPlayerTreasures(String playerId) {
    this.log.add("getPlayerTreasures() called.");
    return null;
  }

  @Override
  public List<List<Location>> getDungeon() {
    this.log.add("getDungeon() called.");
    return null;
  }

  @Override
  public List<String> getVisitedLocations() {
    this.log.add("getVisitedLocations() called.");
    return null;
  }

  @Override
  public void setupPlayerOne(int id) {
    this.log.add("setupPlayerOne() called.");

  }

  @Override
  public void setupPlayerTwo(int id) {
    this.log.add("setupPlayerTwo() called.");

  }

  @Override
  public void startGame() throws IllegalStateException {
    this.log.add("startGame() called.");

  }

  @Override
  public String pickUpTreasures(List<Treasure> desiredTreasure) {
    this.log.add("pickUpTreasures() called.");
    return null;
  }

  @Override
  public void pickUpArrows() {
    this.log.add("pickUpArrows() called.");

  }

  @Override
  public String shootArrow(Direction direction, int distance)
      throws IllegalArgumentException, IllegalStateException {
    this.log.add("shootArrow() called.");
    return null;
  }

  @Override
  public String move(Direction towards) throws IllegalStateException, IllegalArgumentException {
    this.log.add(this.turn + " move() called.");
    switchTurn();
    return null;
  }

}
