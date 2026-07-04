// Nicholas Buchanan
// August 2012

import java.awt.Toolkit;
import javax.swing.*;
import java.awt.*;

public class SystemBeep extends JFrame {

    public SystemBeep() {
        initGUI();
    }

    private void initGUI() {
        setTitle("SystemBeep - Nick Buchanan");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btn = new JButton("BEEP!");
        btn.setFont(new Font("Impact", Font.BOLD, 60));
        btn.setBackground(Color.RED);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);

        btn.addActionListener(e -> {
            Toolkit.getDefaultToolkit().beep();
            // Flash effect
            Color original = btn.getBackground();
            btn.setBackground(Color.WHITE);
            Timer t = new Timer(100, x -> btn.setBackground(original));
            t.setRepeats(false);
            t.start();
        });

        add(btn);
        setVisible(true);
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new SystemBeep());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("Initiating System Beep...");

        // Method 1: AWT Toolkit
        Toolkit.getDefaultToolkit().beep();

        // Method 2: ASCII Bell Character (Console dependent)
        System.out.print("\007");
        System.out.flush();

        System.out.println("Beep command sent.");
    }
}
/*
 * 1. Uses 'Toolkit.getDefaultToolkit().beep()', which is the standard Java AWT
 * method
 * for alerting the user.
 */
