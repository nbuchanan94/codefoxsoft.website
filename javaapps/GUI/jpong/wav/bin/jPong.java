import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class jPongMenu extends JPanel implements KeyListener {
    // Images from your original paths
    Image startimg = new ImageIcon("wav/bin/cover.gif").getImage();
    Image background = new ImageIcon("wav/bin/game.gif").getImage();

    Image difficulty = new ImageIcon("wav/bin/difficulty.png").getImage();
    Image difficulty2 = new ImageIcon("wav/bin/difficulty2.png").getImage();
    Image exit = new ImageIcon("wav/bin/exit.png").getImage();
    Image exit2 = new ImageIcon("wav/bin/exit2.png").getImage();
    Image optionsimg = new ImageIcon("wav/bin/options.png").getImage();
    Image options2 = new ImageIcon("wav/bin/options2.png").getImage();
    Image sensitivity = new ImageIcon("wav/bin/sensitivity.png").getImage();
    Image sensitivity2 = new ImageIcon("wav/bin/sensitivity2.png").getImage();
    Image start = new ImageIcon("wav/bin/start.png").getImage();
    Image start2 = new ImageIcon("wav/bin/start2.png").getImage();
    Image volume = new ImageIcon("wav/bin/volume.png").getImage();
    Image volume2 = new ImageIcon("wav/bin/volume2.png").getImage();

    Image high = new ImageIcon("wav/bin/high.png").getImage();
    Image medium = new ImageIcon("wav/bin/medium.png").getImage();
    Image low = new ImageIcon("wav/bin/low.png").getImage();

    // Menu selection states (index in menu)
    String[] menuItems = {"Start", "Options", "Difficulty", "Volume", "Sensitivity", "Exit"};
    int selectedIndex = 0;

    // For difficulty, volume, sensitivity settings
    int difficultyLevel = 1; // 1=Low,2=Medium,3=High
    int volumeLevel = 2; // same scale
    int sensitivityLevel = 2;

    public jPongMenu() {
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background or splash image (optional)
        g.drawImage(startimg, 0, 0, getWidth(), getHeight(), this);

        int x = 100; // X base position for menu items
        int y = 150; // Y base position
        int spacing = 60; // Vertical spacing between menu items

        for (int i = 0; i < menuItems.length; i++) {
            boolean selected = (i == selectedIndex);

            switch (menuItems[i]) {
                case "Start":
                    g.drawImage(start, x, y + i * spacing, this);
                    if (selected) g.drawImage(start2, x, y + i * spacing, this);
                    break;
                case "Options":
                    g.drawImage(optionsimg, x, y + i * spacing, this);
                    if (selected) g.drawImage(options2, x, y + i * spacing, this);
                    break;
                case "Difficulty":
                    g.drawImage(difficulty, x, y + i * spacing, this);
                    if (selected) g.drawImage(difficulty2, x, y + i * spacing, this);
                    // Draw current difficulty level icon to the right
                    Image diffIcon = (difficultyLevel == 3) ? high : (difficultyLevel == 2) ? medium : low;
                    g.drawImage(diffIcon, x + 250, y + i * spacing, this);
                    break;
                case "Volume":
                    g.drawImage(volume, x, y + i * spacing, this);
                    if (selected) g.drawImage(volume2, x, y + i * spacing, this);
                    // Draw current volume level icon to the right
                    Image volIcon = (volumeLevel == 3) ? high : (volumeLevel == 2) ? medium : low;
                    g.drawImage(volIcon, x + 250, y + i * spacing, this);
                    break;
                case "Sensitivity":
                    g.drawImage(sensitivity, x, y + i * spacing, this);
                    if (selected) g.drawImage(sensitivity2, x, y + i * spacing, this);
                    // Draw current sensitivity level icon to the right
                    Image sensIcon = (sensitivityLevel == 3) ? high : (sensitivityLevel == 2) ? medium : low;
                    g.drawImage(sensIcon, x + 250, y + i * spacing, this);
                    break;
                case "Exit":
                    g.drawImage(exit, x, y + i * spacing, this);
                    if (selected) g.drawImage(exit2, x, y + i * spacing, this);
                    break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            selectedIndex--;
            if (selectedIndex < 0) selectedIndex = menuItems.length - 1;
            repaint();
        } else if (key == KeyEvent.VK_DOWN) {
            selectedIndex++;
            if (selectedIndex >= menuItems.length) selectedIndex = 0;
            repaint();
        } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            // Change values of settings if on Difficulty, Volume, or Sensitivity
            if (menuItems[selectedIndex].equals("Difficulty")) {
                if (key == KeyEvent.VK_LEFT) difficultyLevel = Math.max(1, difficultyLevel - 1);
                else difficultyLevel = Math.min(3, difficultyLevel + 1);
                repaint();
            } else if (menuItems[selectedIndex].equals("Volume")) {
                if (key == KeyEvent.VK_LEFT) volumeLevel = Math.max(1, volumeLevel - 1);
                else volumeLevel = Math.min(3, volumeLevel + 1);
                repaint();
            } else if (menuItems[selectedIndex].equals("Sensitivity")) {
                if (key == KeyEvent.VK_LEFT) sensitivityLevel = Math.max(1, sensitivityLevel - 1);
                else sensitivityLevel = Math.min(3, sensitivityLevel + 1);
                repaint();
            }
        } else if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
            // Perform action on selected menu item
            switch (menuItems[selectedIndex]) {
                case "Start":
                    System.out.println("Starting game...");
                    // TODO: switch to gameplay state
                    break;
                case "Options":
                    System.out.println("Options selected.");
                    // You can handle submenus here
                    break;
                case "Exit":
                    System.out.println("Exiting game.");
                    System.exit(0);
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("jPong Start Menu");
        jPongMenu menu = new jPongMenu();
        frame.add(menu);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        menu.requestFocusInWindow();
    }
}
