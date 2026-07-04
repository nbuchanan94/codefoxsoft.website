// Nicholas Buchanan
// December 2010

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class SimpleLottery extends JFrame {

    private JLabel num1Label, num2Label, resultLabel;

    public SimpleLottery() {
        initGUI();
    }

    private void initGUI() {
        setTitle("SimpleLottery - Nick Buchanan");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(255, 215, 0)); // Gold

        JLabel title = new JLabel("$$$ CASINO LOTTERY $$$", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel(new GridLayout(1, 2));
        gamePanel.setOpaque(false);
        num1Label = createNum("?");
        num2Label = createNum("?");
        gamePanel.add(num1Label);
        gamePanel.add(num2Label);
        add(gamePanel, BorderLayout.CENTER);

        JPanel btm = new JPanel(new BorderLayout());
        btm.setOpaque(false);
        resultLabel = new JLabel("Click Play to Win!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        btm.add(resultLabel, BorderLayout.NORTH);
        JButton btn = new JButton("PLAY ($5)");
        btn.setBackground(Color.GREEN);
        btn.addActionListener(e -> play());
        btm.add(btn, BorderLayout.SOUTH);
        add(btm, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JLabel createNum(String t) {
        JLabel l = new JLabel(t, SwingConstants.CENTER);
        l.setFont(new Font("Monospaced", Font.BOLD, 80));
        l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return l;
    }

    private void play() {
        int n1 = (int) (Math.random() * 5);
        int n2 = (int) (Math.random() * 5);
        num1Label.setText("" + n1);
        num2Label.setText("" + n2);

        if (n1 == n2) {
            resultLabel.setText("YOU WIN THE GAME!!!");
            getContentPane().setBackground(Color.GREEN);
        } else {
            resultLabel.setText("UNFORTUNATELY YOU LOSE!!! :P");
            getContentPane().setBackground(new Color(255, 215, 0));
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new SimpleLottery());
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice = "y";

        System.out.println("--- Simple Lottery Game ---");

        do {
            // Generate two random numbers between 0 and 4
            int num1 = (int) (Math.random() * 5);
            int num2 = (int) (Math.random() * 5);

            System.out.println("\nRolling...");
            System.out.println("Number 1: " + num1);
            System.out.println("Number 2: " + num2);

            if (num1 == num2) {
                System.out.println("YOU WIN THE GAME!!!");
            } else {
                System.out.println("UNFORTUNATELY YOU LOSE!!! :P");
            }

            System.out.print("\nTry again? (y/n): ");
            choice = scanner.next();

        } while (choice.equalsIgnoreCase("y"));

        System.out.println("Thanks for playing!");
        scanner.close();
    }
}

/*
 * This program implements a simple game of chance. It generates two
 * random integers between 0 and 4. If the numbers match, the user
 * wins; otherwise, they lose.
 *
 * The program utilizes a do-while loop to allow the user to replay
 * the game as many times as they wish without restarting the application.
 */
