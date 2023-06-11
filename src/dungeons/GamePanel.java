package dungeons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The game panel displays the dungeon and player action responses.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class GamePanel extends JPanel {
  
  private AdventureGameController control;
  private ReadonlyGameModel model;
  private int dungeonRows;
  private int dungeonCols;
  private int currRow;
  private int currCol;
  
  /**
   * Constructing the entire game view including dungeon view.
   * 
   * @param givenControl Game View controller
   * @param givenModel Game model
   */
  public GamePanel(AdventureGameController givenControl, ReadonlyGameModel givenModel) {
    if (givenControl == null) {
      throw new IllegalArgumentException("The controller cannot be null.");
    }
    if (givenModel == null) {
      throw new IllegalArgumentException("The read-only model cannot be null.");
    }
    this.control = givenControl;
    this.model = givenModel;
    this.dungeonRows = this.model.getDungeon().size();
    this.dungeonCols = this.model.getDungeon().get(0).size();
    this.currRow = 0;
    this.currCol = 0;
    this.setupGamePanel();
    this.setVisible(true);
  }
  
  /**
   * Set up the game dungeon view panel.
   */
  public void setupGamePanel() {
    this.removeAll();
    this.setBackground(Color.WHITE);
    this.setLayout(new GridBagLayout());
    GridBagConstraints grid = new GridBagConstraints();
    BufferedImage img;
    try {
      BufferedImage arrowInCave = ImageIO.read(getClass().getResource("/images/arrow.png"));
      arrowInCave = this.bufferedImageProcessing(arrowInCave, 30, 10);
      BufferedImage monsterInCave = ImageIO.read(getClass().getResource("/images/wumpus.png"));
      monsterInCave = this.bufferedImageProcessing(monsterInCave, 30, 30);
      BufferedImage playerOneIcon = ImageIO.read(getClass().getResource("/images/player1.png"));
      playerOneIcon = this.bufferedImageProcessing(playerOneIcon, 12, 24);
      BufferedImage playerTwoIcon = ImageIO.read(getClass().getResource("/images/player2.png"));
      playerTwoIcon = this.bufferedImageProcessing(playerTwoIcon, 24, 24);
      BufferedImage mildSmell = ImageIO.read(getClass()
          .getResource("/images/slime-pit-nearby.png"));
      BufferedImage strongSmell = ImageIO.read(getClass().getResource("/images/wumpus-nearby.png"));
      BufferedImage dark = ImageIO.read(getClass().getResource("/images/dark.png"));
      
      for (int i = 0; i < this.dungeonRows; i++) {
        for (int j = 0; j < this.dungeonCols; j++) {
          grid.gridx = j;
          grid.gridy = i;
          int spacing = 8;
          Location currLocation = this.model.getDungeon().get(i).get(j);
          if (this.model.getVisitedLocations().contains(currLocation.getLocationId())) {
            String fileName = "";
            if (currLocation.getLocationId().charAt(0) == 'C') {
              fileName = this.displayCave(currLocation);
            } else {
              fileName = this.displayTunnel(currLocation);
            }
            img = ImageIO.read(getClass().getResource("/images/" + fileName + ".png"));
            img = this.bufferedImageProcessing(img, 70, 70);
            
            List<String> treasureList = this.displayTreasureInCave(img, currLocation);
            
            for (String l : treasureList) {
              BufferedImage treasureImg = ImageIO.read(getClass()
                  .getResource("/images/" + l + ".png"));
              treasureImg = this.bufferedImageProcessing(treasureImg, 15, 15);
              img = this.overlay(img, treasureImg, spacing, 20);
              spacing = spacing + 15;
            }
                       
            if (currLocation.getArrowsInLocation() > 0) {
              img = this.overlay(img, arrowInCave, 15, 40);
            }
            
            if (currLocation.getMonster() != null) {
              img = this.overlay(img, monsterInCave, 40, 35);
            }
            
            if (this.model.getPlayerOneLocation().equals(currLocation.getLocationId())) {
              if (this.model.getTurn() == this.model.getPlayerOne()) {
                this.currRow = i;
                this.currCol = j;
              }
              img = this.overlay(img, playerOneIcon, 20, 20);
              if (this.model.getPlayerOne().getCurrentLocationDetails().contains("terrible")) {
                img = this.overlay(img, strongSmell, 0, 0);
              } else if (this.model.getPlayerOne().getCurrentLocationDetails()
                  .contains("slightly smelly")) {
                img = this.overlay(img, mildSmell, 0, 0);
              }
            }
            
            if (this.model.getPlayerTwo() != null) {
              if (this.model.getPlayerTwoLocation().equals(currLocation.getLocationId())) {
                if (this.model.getTurn().getPlayerId()
                    .equals(this.model.getPlayerTwo().getPlayerId())) {
                  this.currRow = i;
                  this.currCol = j;
                }
                img = this.overlay(img, playerTwoIcon, 35, 20);
                if (this.model.getPlayerTwo().getCurrentLocationDetails().contains("terrible")) {
                  img = this.overlay(img, strongSmell, 0, 0);
                } else if (this.model.getPlayerTwo().getCurrentLocationDetails()
                    .contains("slightly smelly")) {
                  img = this.overlay(img, mildSmell, 0, 0);
                }
              }
            }
            JLabel pic = new JLabel(new ImageIcon(img));
            this.add(pic, grid);
          } else {
            dark = bufferedImageProcessing(dark, 70, 70);
            JLabel hidden = new JLabel(new ImageIcon(dark));
            this.add(hidden, grid);
          }
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
  
  private BufferedImage bufferedImageProcessing(BufferedImage img, int width, int height) {
    Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = dimg.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();
    return dimg;
  }
  
  private BufferedImage overlay(BufferedImage cell, BufferedImage element, int horiSpacing, 
      int vertiSpacing) {
    BufferedImage updated = new BufferedImage(cell.getWidth(), cell.getHeight(), 
        BufferedImage.TYPE_INT_ARGB);
    Graphics g = updated.createGraphics();
    g.drawImage(cell, 0, 0, null);
    g.drawImage(element, horiSpacing, vertiSpacing, null);
    return updated;
  }
  
  private String displayCave(Location curr) {
    String[] locDetails = curr.getLocationDetails().split("\n");
    
    List<String> allNeighborDirections = new ArrayList<>();
    
    for (int j = 0; j < locDetails.length; j++) {
      allNeighborDirections.add(locDetails[j].substring(0, 1));
    }
       
    switch (allNeighborDirections.size()) {
      case 1:
        if (allNeighborDirections.contains("N")) {
          return "N";
        } else if (allNeighborDirections.contains("E")) {
          return "E";
        } else if (allNeighborDirections.contains("W")) {
          return "W";
        } else {
          return "S";
        }
      case 3:
        if (allNeighborDirections.contains("N") && allNeighborDirections.contains("E") 
            && allNeighborDirections.contains("W")) {
          return "ENW";
        } else if (allNeighborDirections.contains("N") && allNeighborDirections.contains("W") 
            && allNeighborDirections.contains("S")) {
          return "NWS";
        } else if (allNeighborDirections.contains("W") && allNeighborDirections.contains("S") 
            && allNeighborDirections.contains("E")) {
          return "WSE";
        } else {
          return "SEN";
        }
      case 4:
        return "NEWS";
      default:
    }
    
    return "";
  }
  
  private String displayTunnel(Location curr) {
    String[] locDetails = curr.getLocationDetails().split("\n");

    List<String> allNeighborDirections = new ArrayList<>();
    
    for (int j = 0; j < locDetails.length; j++) {
      allNeighborDirections.add(locDetails[j].substring(0, 1));
    }
    
    if (allNeighborDirections.contains("E") && allNeighborDirections.contains("W")) {
      return "EW";
    } else if (allNeighborDirections.contains("E") && allNeighborDirections.contains("N")) {
      return "NE";
    } else if (allNeighborDirections.contains("S") && allNeighborDirections.contains("N")) {
      return "NS";
    } else if (allNeighborDirections.contains("W") && allNeighborDirections.contains("N")) {
      return "NW";
    } else if (allNeighborDirections.contains("E") && allNeighborDirections.contains("S")) {
      return "SE";
    } else {
      return "SW";
    }
  }
  
  private List<String> displayTreasureInCave(BufferedImage img, Location curr) throws IOException {
    List<String> treasureList = new ArrayList<>();
    String treasureInCave = curr.getPresentTreasures();
    
    if (!treasureInCave.contains("does not have any treasure")) {
      treasureInCave = treasureInCave.substring(0, treasureInCave.length() - 26);
      if (treasureInCave.contains("DIAMOND")) {
        treasureList.add("diamonds");
      }
      if (treasureInCave.contains("RUBIES")) {
        treasureList.add("ruby");
      }
      if (treasureInCave.contains("SAPPHIRE")) {
        treasureList.add("sapphire");
      }
    }
    return treasureList;
  }
  
  /**
   * Adding a mouse listener to the panel and communicating with 
   * the controller.
   */
  public void addListener() {
    MouseListener ml = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int row = e.getY();
        int col = e.getX();
        Direction dir = getMoveDirection(row, col);
        if (dir != null) {
          control.handleCellClick(dir);
        }
      }
    };
    this.addMouseListener(ml);
  }
  
  private Direction getMoveDirection(int row, int col) {
    int panelWidth = 695;
    int panelHeight = 419;
    if (this.model.getNumberOfPlayers() == 2) {
      panelWidth = 600;
      panelHeight = 419;
    }     
    int dungeonWidth = dungeonRows * 70;
    int dungeonHeight = dungeonCols * 70;
    
    int yaxisCoordDiff = panelWidth < dungeonWidth ? 0 : (panelWidth - dungeonWidth) / 2;
    int xaxisCoordDiff = panelHeight < dungeonHeight ? 0 : (panelHeight - dungeonHeight) / 2;
    
    if (row < ((currRow * 70) + xaxisCoordDiff) 
        && row >= (((currRow - 1) * 70) + xaxisCoordDiff) 
        && col < (((currCol + 1) * 70) + yaxisCoordDiff) 
        && col >= ((currCol * 70) + yaxisCoordDiff)) {
      return Direction.NORTH;
    } else if (row >= (currRow * 70 + xaxisCoordDiff) 
        && row <= ((currRow + 1) * 70 + xaxisCoordDiff) 
        && col > ((currCol + 1) * 70 + yaxisCoordDiff) 
        && col <= (currCol + 2) * 70 + yaxisCoordDiff) {
      return Direction.EAST;
    } else if (row >= (currRow * 70 + xaxisCoordDiff) 
        && row <= ((currRow + 1) * 70 + xaxisCoordDiff) 
        && col < (currCol * 70 + yaxisCoordDiff) 
        && col >= (currCol - 1) * 70 + yaxisCoordDiff) {
      return Direction.WEST;
    } else if (row > ((currRow + 1) * 70 + xaxisCoordDiff) 
        && row <= ((currRow + 2) * 70 + xaxisCoordDiff) 
        && col < ((currCol + 1) * 70 + yaxisCoordDiff) 
        && col >= ((currCol * 70) + yaxisCoordDiff)) {
      return Direction.SOUTH;
    }
    return null;
  }
}
