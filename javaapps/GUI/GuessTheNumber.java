
// Nicholas Larkin Buchanan
// March 15, 2011
import java.util.Scanner;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GuessTheNumber extends JFrame {

    private JTextField guessField;
    private JLabel messageLabel;
    private int secret;
    private int attempts;
    private Random random = new Random();

    public GuessTheNumber() {
        initGUI();
    }

    private void initGUI() {
        setTitle("GuessTheNumber - Nick Buchanan");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        getContentPane().setBackground(new Color(0, 255, 255)); // Cyan

        JLabel title = new JLabel("::: GUESS THE NUMBER :::", SwingConstants.CENTER);
        title.setFont(new Font("Impact", Font.BOLD, 24));
        title.setForeground(new Color(75, 0, 130)); // Indigo
        add(title);

        JLabel instruct = new JLabel("I picked a number 1-10. Can you guess it?", SwingConstants.CENTER);
        add(instruct);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setOpaque(false);
        inputPanel.add(new JLabel("Your Guess:"));
        guessField = new JTextField(5);
        inputPanel.add(guessField);
        JButton guessBtn = new JButton("GUESS!");
        guessBtn.setBackground(Color.YELLOW);
        guessBtn.addActionListener(e -> checkGuess());
        inputPanel.add(guessBtn);
        add(inputPanel);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setForeground(Color.BLUE);
        add(messageLabel);

        JButton resetBtn = new JButton("Play Again / Reset");
        resetBtn.addActionListener(e -> startNewGame());
        add(resetBtn);

        startNewGame();
        setVisible(true);
    }

    private void startNewGame() {
        secret = random.nextInt(10) + 1;
        attempts = 0;
        messageLabel.setText("New game started!");
        guessField.setText("");
        guessField.setEditable(true);
        getContentPane().setBackground(new Color(0, 255, 255));
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            attempts++;
            if (guess == secret) {
                messageLabel.setText("WINNER in " + attempts + " tries!");
                getContentPane().setBackground(Color.GREEN);
            } else if (guess > secret) {
                messageLabel.setText("Too high! Try smaller.");
                getContentPane().setBackground(new Color(255, 182, 193));
            } else {
                messageLabel.setText("Too low! Try bigger.");
                getContentPane().setBackground(new Color(173, 216, 230));
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new GuessTheNumber());
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean keepPlaying = true;

        System.out.println("::: GUESS THE NUMBER CHALLENGE :::");
        System.out.println("Can you beat the computer?");

        while (keepPlaying) {
            int secret = random.nextInt(10) + 1; // 1 to 10
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("\n-----------------------------------");
            System.out.println("I have picked a number between 1 and 10.");
            System.out.println("Start guessing!");

            while (!guessedCorrectly) {
                System.out.print("Your guess: ");
                if (!scanner.hasNextInt()) {
                    String trash = scanner.next(); // Consume bad input
                    System.out.println("Hey! '" + trash + "' is not a number. Try again.");
                    continue;
                }

                int guess = scanner.nextInt();
                attempts++;

                if (guess == secret) {
                    System.out.println("***********************************");
                    System.out.println("   WINNER! WINNER! WINNER!");
                    System.out.println("   You got it in " + attempts + " try(s)!");
                    System.out.println("***********************************");
                    guessedCorrectly = true;
                } else if (guess > secret) {
                    System.out.println(" -> Too high! Try something smaller.");
                } else {
                    System.out.println(" -> Too low! Try something bigger.");
                }
            }

            System.out.print("\nDo you want to play again? (y/n): ");
            String response = scanner.next();
            if (!response.equalsIgnoreCase("y")) {
                keepPlaying = false;
                System.out.println("Thanks for playing! Goodbye!");
            }
        }
    }
}
// Run the program to play a guessing game against the computer.
// Follow the on-screen prompts to guess the secret number (1-10).
