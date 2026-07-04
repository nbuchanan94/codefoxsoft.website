// Nicholas Larkin Buchanan
// 2011

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ColorCheckBoxWindow extends JFrame {
    private JLabel messageLabel;
    private JCheckBox yellowCheckBox;
    private JCheckBox redCheckBox; // Fixed capitalization in variable name
    private final int WINDOW_HEIGHT = 200;
    private final int WINDOW_WIDTH = 300;

    public ColorCheckBoxWindow() {
        super("Color Checkbox Window");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        messageLabel = new JLabel("Check boxes to change colors.");
        yellowCheckBox = new JCheckBox("Yellow Background");
        redCheckBox = new JCheckBox("Red Text");

        // Add listeners
        CheckBoxListener listener = new CheckBoxListener();
        yellowCheckBox.addItemListener(listener);
        redCheckBox.addItemListener(listener);

        add(messageLabel);
        add(yellowCheckBox);
        add(redCheckBox);

        setVisible(true);
        System.out.println("Window initialized.");
    }

    private class CheckBoxListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            // Logic for Yellow Background
            if (e.getSource() == yellowCheckBox) {
                if (yellowCheckBox.isSelected()) {
                    getContentPane().setBackground(Color.YELLOW);
                    yellowCheckBox.setBackground(Color.YELLOW);
                    redCheckBox.setBackground(Color.YELLOW);
                } else {
                    getContentPane().setBackground(Color.WHITE);
                    yellowCheckBox.setBackground(Color.WHITE);
                    redCheckBox.setBackground(Color.WHITE);
                }
            }
            // Logic for Red Text
            if (e.getSource() == redCheckBox) {
                if (redCheckBox.isSelected()) {
                    messageLabel.setForeground(Color.RED);
                    yellowCheckBox.setForeground(Color.RED);
                    redCheckBox.setForeground(Color.RED);
                } else {
                    messageLabel.setForeground(Color.BLACK);
                    yellowCheckBox.setForeground(Color.BLACK);
                    redCheckBox.setForeground(Color.BLACK);
                }
            }
        }
    }

    public static void main(String[] args) {
        new ColorCheckBoxWindow();
    }
}

/*
 * HOW TO USE PROGRAM:
 * 1. Run the application.
 * 2. Click "Yellow Background" to toggle the window background color.
 * 3. Click "Red Text" to toggle the text color.
 */
