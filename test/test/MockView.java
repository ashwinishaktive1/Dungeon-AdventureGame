package test;

import dungeons.AdventureGameController;
import dungeons.GameView;
import dungeons.ReadonlyGameModel;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;

/**
 * A mock view to just check if the methods are called and used when 
 * necessary.
 * 
 * @author Ashwini Shaktivel Kumar
 * 
 */
public class MockView implements GameView {
  
  private List<String> log;
  
  /**
   * Constructor to initiate a mock view with no application frame.
   * 
   * @param testLog testing log to check if all functions are called correctly
   */
  public MockView(List<String> testLog) {
    this.log = testLog;
  }

  @Override
  public void addControllerListener(AdventureGameController listener) {
    this.log.add("addControllerListener() called.");

  }

  @Override
  public void addListeners(ActionListener action, KeyListener keyboard) {
    this.log.add("addListeners() called.");

  }

  @Override
  public void refresh() {
    this.log.add("refresh() called.");

  }

  @Override
  public void setModel(ReadonlyGameModel updatedModel) throws IllegalArgumentException {
    this.log.add("setModel() called.");

  }

  @Override
  public void displayHomePage() {
    this.log.add("displayHomePage() called.");

  }

  @Override
  public void displayVisible() {
    this.log.add("displayVisible() called.");

  }

  @Override
  public String getSinglePlayerDetail() {
    this.log.add("getSinglePlayerDetail() called.");
    return null;
  }

  @Override
  public String getDoublePlayerDetail() {
    this.log.add("getDoublePlayerDetail() called.");
    return null;
  }

  @Override
  public String[] getGameArgs() {
    this.log.add("getGameArgs() called.");
    return null;
  }

  @Override
  public void displayMazeGame() {
    this.log.add("displayMazeGame() called.");

  }

  @Override
  public void displayGameStatus(String status) {
    this.log.add("displayGameStatus() called.");

  }

  @Override
  public void clearGameArgs() {
    this.log.add("clearGameArgs() called.");

  }

  @Override
  public void clearStatus() {
    this.log.add("clearStatus() called.");

  }

  @Override
  public void clearGame() {
    this.log.add("clearGame() called.");

  }

  @Override
  public void displayGameTurn() {
    this.log.add("displayGameTurn() called.");

  }

  @Override
  public void quitGame() {
    this.log.add("quitGame() called.");

  }

  @Override
  public void repaintView() {
    this.log.add("repaintView() called.");
  }

}
