// Nicholas Larkin Buchanan
// 2011

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class RandomDots extends JFrame {

    public RandomDots() {
        super("Random Dots");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);

        DotPanel panel = new DotPanel();
        add(panel);

        setVisible(true);
    }

    class DotPanel extends JPanel implements Runnable {
        private Thread animator;

        public DotPanel() {
            setBackground(Color.BLACK);
            animator = new Thread(this);
            animator.start();
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(500);
                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Random r = new Random();
            g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));

             g.fillOval(r.nextInt(getWidth()), r.nextInt(getHeight()), 50, 50);
        }
    }

    public static void main(String[] args) {
        new RandomDots();
    }
}

/*
 * HOW TO USE PROGRAM:
 * 1. Run the program.
 * 2. It displays a window with a black background.
 * 3. Every 0.5 seconds, a new colored circle appears at a random location.
 */
