// Nicholas Larkin Buchanan
// 2013

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RandomWalkAnimation extends JFrame {

    private int currentRow;
    private int currentCol;
    private final int ROWS = 20;
    private final int COLS = 20;
    private final int CELL_SIZE = 20;

    public RandomWalkAnimation() {
        super("Random Walk Simulation");
        setSize(ROWS * CELL_SIZE + 20, COLS * CELL_SIZE + 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentRow = ROWS / 2;
        currentCol = COLS / 2;

        WalkPanel panel = new WalkPanel();
        add(panel);

        Timer timer = new Timer(100, e -> {
            moveRandomly();
            panel.repaint();
        });
        timer.start();

        setVisible(true);
    }

    private void moveRandomly() {
        Random rand = new Random();
        int direction = rand.nextInt(4);

        switch (direction) {
            case 0:
                currentRow--;
                break; // Up
            case 1:
                currentRow++;
                break; // Down
            case 2:
                currentCol--;
                break; // Left
            case 3:
                currentCol++;
                break; // Right
        }

        // Keep in bounds
        if (currentRow < 0)
            currentRow = 0;
        if (currentRow >= ROWS)
            currentRow = ROWS - 1;
        if (currentCol < 0)
            currentCol = 0;
        if (currentCol >= COLS)
            currentCol = COLS - 1;
    }

    private class WalkPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            // Don't clear background to show "trail"
            // super.paintComponent(g);

            g.setColor(Color.RED);
            g.fillRect(currentCol * CELL_SIZE, currentRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(currentCol * CELL_SIZE, currentRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandomWalkAnimation::new);
    }
}

/*
 * HOW TO USE PROGRAM:
 * 1. Run the program.
 * 2. It simulates a "Random Walk" on a grid.
 */
