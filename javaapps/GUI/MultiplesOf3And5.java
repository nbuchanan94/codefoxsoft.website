
//Nicholas Larkin Buchanan
//2012-05-15
//pd 8
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class MultiplesOf3And5 extends JFrame {

    private JTextField limitField;
    private JLabel resLabel;

    public MultiplesOf3And5() {
        initGUI();
    }

    private void initGUI() {
        setTitle("MultiplesOf3And5 - Nick Buchanan");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        // Puzzle Theme (Pink)
        getContentPane().setBackground(Color.PINK);

        JPanel p = new JPanel();
        p.setOpaque(false);
        p.add(new JLabel("Enter limit (e.g. 1000):"));
        limitField = new JTextField(10);
        p.add(limitField);
        add(p);

        JButton btn = new JButton("Calculate Sum");
        btn.addActionListener(e -> calc());
        add(btn);

        resLabel = new JLabel("Sum: ?", SwingConstants.CENTER);
        resLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(resLabel);

        setVisible(true);
    }

    private void calc() {
        try {
            int upTo = Integer.parseInt(limitField.getText());
            int output = 0;
            for (int i = 1; i < upTo; i++) {
                if (i % 3 == 0 || i % 5 == 0) { // Merged logic for optimization or keep strict?

                }
            }

            int multx = 3;
            int multy = 5;
            output = 0;
            for (int i = 1; i < upTo; i++) {
                if (i % multx == 0) {
                    output = output + i;
                } else if (i % multy == 0) {
                    output = output + i;
                }
            }
            resLabel.setText("Sum: " + output);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new MultiplesOf3And5());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("System: Application Start");
        Scanner kbreader = new Scanner(System.in);
        final int multx = 3;
        final int multy = 5;
        int output = 0;

        System.out.print("Enter limit (e.g. 10 or 1000): ");
        int upTo = kbreader.nextInt();
        System.out.println("DEBUG: calculating multiples of " + multx + " and " + multy + " below " + upTo);

        for (int i = 1; i < upTo; i++) {
            if (i % multx == 0) {
                output = output + i;
            } else if (i % multy == 0) {
                output = output + i;
            }
        }
        System.out.println("Sum of multiples: " + output);
        System.out.println("System: Application End");
    }
}
// Usage: Run. Calculates sum of multiples of 3 and 5 below input limit.
