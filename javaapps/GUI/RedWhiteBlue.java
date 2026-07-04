// Nicholas Buchanan
// 6/10/2011

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RedWhiteBlue extends JFrame implements ActionListener {
  private JButton r = new JButton("Red");
  private JButton w = new JButton("White");
  private JButton b = new JButton("Blue");

  public RedWhiteBlue() {
    super("Red White Blue");
    setSize(300, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new FlowLayout());

    // Add standard AWT/Swing logic
    r.addActionListener(this);
    w.addActionListener(this);
    b.addActionListener(this);

    r.setActionCommand("r");
    w.setActionCommand("w");
    b.setActionCommand("b");

    // Colorize buttons for better UI
    r.setForeground(Color.RED);
    w.setForeground(Color.DARK_GRAY);
    b.setForeground(Color.BLUE);

    add(r);
    add(w);
    add(b);

    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
    Color c = Color.WHITE; // Default

    if (cmd.equals("r")) {
      c = Color.RED;
    } else if (cmd.equals("w")) {
      c = Color.WHITE;
    } else if (cmd.equals("b")) {
      c = Color.BLUE;
    }

    // In JFrame, you must set the ContentPane's background
    getContentPane().setBackground(c);
  }

  public static void main(String[] args) {
    new RedWhiteBlue();
  }
}

/*
 * 1. Displays three buttons: Red, White, and Blue.
 * 2. Listens for button clicks.
 * 3. Updates the window's background color to match the selected button.
 */