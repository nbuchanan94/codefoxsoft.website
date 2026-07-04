// Nicholas Buchanan
// 2010

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Date;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Welcome extends JPanel {

   private Image puppyImage;

   public Welcome() {
      // Set window properties
      this.setPreferredSize(new Dimension(500, 300));
      this.setBackground(Color.BLACK);

      // Load the Puppy Image from the Web
      try {
         // "Random" puppy image from Dog API
         URL url = new URL("https://images.dog.ceo/breeds/labrador/n02099712_7418.jpg");
         puppyImage = ImageIO.read(url);
      } catch (IOException e) {
         System.err.println("Error loading puppy image. Please check your internet connection.");
         e.printStackTrace();
      }
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      // Set text properties
      g.setColor(Color.WHITE);
      g.setFont(new Font("SansSerif", Font.BOLD, 14));

      Date currentDate = new Date(); // Current date time

      // Drawing text (Coordinates adjusted slightly for Swing relative layout)
      g.drawString("Welcome to my day!", 200, 70);
      g.drawString("Daily Planner for Nick Buchanan", 200, 100);
      g.drawString(currentDate.toString(), 200, 130);

      // Draw the puppy image if loaded
      if (puppyImage != null) {
         // Draw it scaled or original size. Let's keep it near 10,10 as original, but
         // maybe lower to not overlap text?
         // Original code: g.drawImage (smile, 10,10, this);
         // It might overlap text at 200,70. Let's move it down or keep original layout.
         // Actually, 10,10 is Top-Left. Text is at 200+. So it might be side-by-side or
         // overlapping depending on image size.
         // Let's resize it to fit nicely.
         g.drawImage(puppyImage, 10, 10, 180, 180, this);
      }
   }

   public static void main(String[] args) {
      JFrame frame = new JFrame("Welcome App");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      Welcome panel = new Welcome();
      frame.add(panel);
      frame.pack();

      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }
}

/*
 * This program demonstrates basic Java GUI rendering using Graphics.
 * It displays a welcome message, the current date/time, and an image loaded
 * from the internet.
 *
 */
