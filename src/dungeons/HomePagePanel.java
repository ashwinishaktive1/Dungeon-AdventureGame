package dungeons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * The HomePagePanel displays the first page the user views, through which they 
 * can access the menu and decide on the game specifications.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class HomePagePanel extends JPanel {
  
  /**
   * Construct the home page panel.
   */
  public HomePagePanel() {
    this.setBackground(Color.WHITE);
    this.setVisible(true);
    this.setLayout(null);
  }
  
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    Graphics2D g2 = (Graphics2D) g;
    try {
      Image img = ImageIO.read(getClass().getResource("/images/homePage.jpeg"))
              .getScaledInstance(900, 600, Image.SCALE_SMOOTH);
      g2.drawImage(img, 0, 0, this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
