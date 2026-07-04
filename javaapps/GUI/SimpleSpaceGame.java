// Nicholas Larkin Buchanan
// 2012

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleSpaceGame extends JFrame implements ActionListener {

    private int x = 50;
    private int y = 50;
    private int xSpeed = 2;
    private int ySpeed = 2;
    private Timer timer;

    public SimpleSpaceGame() {
        super("Simple Game Loop ");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timer = new Timer(10, this); // 10ms loop
        timer.start();

        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.BLACK);
                g.setColor(Color.WHITE);
                g.drawString("Hello World!", x, y);
                g.setColor(Color.GREEN);
                g.fillRect(x, y + 10, 20, 10);
            }
        };
        add(gamePanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Move logic
        x += xSpeed;
        y += ySpeed;

        // Bounce logic
        if (x < 0 || x > getWidth() - 40)
            xSpeed = -xSpeed;
        if (y < 0 || y > getHeight() - 40)
            ySpeed = -ySpeed;

        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleSpaceGame::new);
    }
}

/*
 * HOW TO USE PROGRAM:
 * 1. Run the program.
 * 2. Watch the  Animation bounce around the screen.
 */
