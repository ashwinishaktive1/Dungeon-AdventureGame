package dungeons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Game Status panel displays the current player status including their 
 * collection of arrows and treasure. The status panel also displays the 
 * winner of the game upon game ending.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class GameStatusPanel extends JPanel {
  
  ReadonlyGameModel model;
  JLabel title;
  Players playerOneName;
  Players playerTwoName;
  
  /**
   * Construct the game status panel.
   * 
   * @param givenModel Game model
   * @throws IllegalArgumentException if the model is null
   */
  public GameStatusPanel(ReadonlyGameModel givenModel) throws IllegalArgumentException {
    if (givenModel == null) {
      throw new IllegalArgumentException("The read-only model cannot be null.");
    }
    this.model = givenModel;
    this.playerOneName = this.model.getPlayerOne();
    this.playerTwoName = this.model.getPlayerTwo();
    this.setBackground(Color.WHITE);
    this.setupStatusPanel();
  }
  
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
  
  /**
   * Setting up the game status panel.
   */
  public void setupStatusPanel() {
    this.removeAll();
    this.setBounds(700, 0, 300, 700);
    this.setBackground(Color.gray);
    this.setLayout(new GridBagLayout());
    GridBagConstraints grid = new GridBagConstraints();
    grid.fill = GridBagConstraints.HORIZONTAL;
    
    grid.insets = new Insets(0, 0, 0, 0);
    grid.gridx = 0;
    grid.gridy = 0;
    title = new JLabel("Game Status");
    title.setForeground(Color.black);
    this.add(title, grid);
    
    grid.gridx = 0;
    grid.gridy = 1;
    grid.insets = new Insets(5, 0, 5, 0);
    JLabel playerIcon = new JLabel();
    Image player = new ImageIcon(getClass().getResource("/images/player1.png")).getImage()
        .getScaledInstance(15, 30, Image.SCALE_DEFAULT);
    playerIcon.setIcon(new ImageIcon(player));
    this.add(playerIcon, grid);
    
    grid.gridx = 1;
    grid.gridy = 1;
    grid.insets = new Insets(5, -45, 5, 0);
    JLabel playerOne = new JLabel();
    playerOne.setText(this.playerOneName.getPlayerId());
    this.add(playerOne, grid);
    
    grid.gridx = 0;
    grid.gridy = 2;
    grid.insets = new Insets(5, 0, 5, 0);
    JLabel arrowsIcon = new JLabel();
    Image arrows = new ImageIcon(getClass().getResource("/images/arrows-icon.png")).getImage()
        .getScaledInstance(30, 30, Image.SCALE_DEFAULT);
    arrowsIcon.setIcon(new ImageIcon(arrows));
    this.add(arrowsIcon, grid);
    
    grid.gridx = 1;
    grid.gridy = 2;
    grid.insets = new Insets(5, -35, 5, 0);
    playerOne = new JLabel();
    playerOne.setText(this.model.getPlayerArrows(playerOneName.getPlayerId()));
    this.add(playerOne, grid);
            
    grid.gridx = 0;
    grid.gridy = 3;
    grid.insets = new Insets(5, 0, 5, 0);
    JLabel diamondsIcon = new JLabel();
    Image diamonds = new ImageIcon(getClass().getResource("/images/diamonds.png")).getImage()
        .getScaledInstance(30, 30, Image.SCALE_DEFAULT);
    diamondsIcon.setIcon(new ImageIcon(diamonds));
    this.add(diamondsIcon, grid);
    
    grid.gridx = 1;
    grid.gridy = 3;
    grid.insets = new Insets(5, -35, 5, 0);
    playerOne = new JLabel();
    Map<Treasure, Integer> playerOneTreasures = 
        this.model.getPlayerTreasures(playerOneName.getPlayerId());
    playerOne.setText(Integer.toString(playerOneTreasures.get(Treasure.DIAMONDS)));
    this.add(playerOne, grid);
    
    grid.gridx = 0;
    grid.gridy = 4;
    grid.insets = new Insets(5, 0, 5, 0);
    JLabel rubiesIcon = new JLabel();
    Image rubies = new ImageIcon(getClass().getResource("/images/ruby.png")).getImage()
        .getScaledInstance(30, 30, Image.SCALE_DEFAULT);
    rubiesIcon.setIcon(new ImageIcon(rubies));
    this.add(rubiesIcon, grid);
    
    grid.gridx = 1;
    grid.gridy = 4;
    grid.insets = new Insets(5, -35, 5, 0);
    playerOne = new JLabel();
    playerOne.setText(Integer.toString(playerOneTreasures.get(Treasure.RUBIES)));
    this.add(playerOne, grid);
        
    grid.gridx = 0;
    grid.gridy = 5;
    grid.insets = new Insets(5, 0, 5, 0);
    JLabel sapphireIcon = new JLabel();
    Image sapphire = new ImageIcon(getClass().getResource("/images/sapphire.png")).getImage()
        .getScaledInstance(30, 30, Image.SCALE_DEFAULT);
    sapphireIcon.setIcon(new ImageIcon(sapphire));
    this.add(sapphireIcon, grid);
    
    grid.gridx = 1;
    grid.gridy = 5;
    grid.insets = new Insets(5, -35, 5, 0);
    playerOne = new JLabel();
    playerOne.setText(Integer.toString(playerOneTreasures.get(Treasure.SAPPHIRE)));
    this.add(playerOne, grid);
    
    if (this.model.getPlayerTwo() != null) {
      grid.gridx = 2;
      grid.gridy = 1;
      grid.insets = new Insets(5, 10, 5, 0);
      playerIcon = new JLabel();
      playerIcon.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/player2.png"))
          .getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
      this.add(playerIcon, grid);
      
      grid.gridx = 3;
      grid.gridy = 1;
      grid.insets = new Insets(5, 10, 5, 0);
      JLabel playerTwo = new JLabel();
      playerTwo.setText(this.playerTwoName.getPlayerId());
      this.add(playerTwo, grid);
      
      grid.gridx = 2;
      grid.gridy = 2;
      grid.insets = new Insets(5, 10, 5, 0);
      playerIcon = new JLabel();
      playerIcon.setIcon(new ImageIcon(arrows));
      this.add(playerIcon, grid);
      
      grid.gridx = 3;
      grid.gridy = 2;
      grid.insets = new Insets(5, 10, 5, 0);
      playerTwo = new JLabel();
      playerTwo.setText(this.model.getPlayerArrows(playerTwoName.getPlayerId()));
      this.add(playerTwo, grid);
      
      grid.gridx = 2;
      grid.gridy = 3;
      grid.insets = new Insets(5, 10, 5, 0);
      diamondsIcon = new JLabel();
      diamondsIcon.setIcon(new ImageIcon(diamonds));
      this.add(diamondsIcon, grid);
      
      grid.gridx = 3;
      grid.gridy = 3;
      grid.insets = new Insets(5, 10, 5, 0);
      playerTwo = new JLabel();
      Map<Treasure, Integer> playerTwoTreasures = 
          this.model.getPlayerTreasures(playerTwoName.getPlayerId());
      playerTwo.setText(Integer.toString(playerTwoTreasures.get(Treasure.DIAMONDS)));
      this.add(playerTwo, grid);
      
      grid.gridx = 2;
      grid.gridy = 4;
      grid.insets = new Insets(5, 10, 5, 0);
      rubiesIcon = new JLabel();
      rubiesIcon.setIcon(new ImageIcon(rubies));
      this.add(rubiesIcon, grid);
      
      grid.gridx = 3;
      grid.gridy = 4;
      grid.insets = new Insets(5, 10, 5, 0);
      playerTwo = new JLabel();
      playerTwo.setText(Integer.toString(playerTwoTreasures.get(Treasure.RUBIES)));
      this.add(playerTwo, grid);
      
      grid.gridx = 2;
      grid.gridy = 5;
      grid.insets = new Insets(5, 10, 5, 0);
      sapphireIcon = new JLabel();
      sapphireIcon.setIcon(new ImageIcon(sapphire));
      this.add(sapphireIcon, grid);
      
      grid.gridx = 3;
      grid.gridy = 5;
      grid.insets = new Insets(5, 10, 5, 0);
      playerTwo = new JLabel();
      playerTwo.setText(Integer.toString(playerTwoTreasures.get(Treasure.SAPPHIRE)));
      this.add(playerTwo, grid);
    }
    if (this.model.isGameOver()) {
      grid.gridx = 0;
      grid.gridy = 6;
      JLabel winner = new JLabel("Winner");
      if (this.model.getWinner() != null) {
        winner.setText(this.model.getWinner().getPlayerId() + " wins!!");
      } else {
        winner.setText("No winner");
      }
      this.add(winner, grid);
    }
    this.setVisible(true);    
  }

}
