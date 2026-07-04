
//Nicholas Larkin Buchanan
//2011-01-13
//pd 5
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class MovingCostCalculator extends JFrame {

    private JTextField distField;
    private JTextArea logArea;

    public MovingCostCalculator() {
        initGUI();
    }

    private void initGUI() {
        setTitle("MovingCostCalculator - Nick Buchanan");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Logistics Theme
        getContentPane().setBackground(Color.LIGHT_GRAY);

        JPanel p = new JPanel();
        p.add(new JLabel("Distance (km):"));
        distField = new JTextField(10);
        p.add(distField);
        JButton btn = new JButton("Get Quote");
        btn.setBackground(Color.ORANGE);
        btn.addActionListener(e -> calc());
        p.add(btn);
        add(p, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBorder(BorderFactory.createTitledBorder("Quote Details"));
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void calc() {
        try {
            double traveled = Double.parseDouble(distField.getText());
            double total = 200;
            double km = 0.25;

            String log = "Base Fee: $200\nRate: $0.25/km\n\n";

            if (traveled < 50) {
                log += "Distance < 50. Applying local rate.\n";
                log += "Cost: " + (((traveled * .05) + traveled) * km + (traveled + total));
            } else if (traveled > 400) {
                log += "Distance > 400. Long distance.\n";
                log += "Cost: " + (total + (traveled * km * 1.5)) + "\n";
                log += "(Includes long-distance surcharge)";
            } else {
                log += "Standard distance (50-400).\n";
                log += "Cost: " + (total + (traveled * km));
            }
            logArea.setText(log);

        } catch (Exception e) {
            logArea.setText("Invalid Input");
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new MovingCostCalculator());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("System: Application Start");
        Scanner kbreader = new Scanner(System.in);

        System.out.print("Enter distance traveled (km): ");
        double traveled = kbreader.nextDouble();
        System.out.println(" Input received: " + traveled);

        double total = 200; // Base fee?
        double km = .25; // Rate per km?

        if (traveled < 50) {
            System.out.println("Distance < 50. Applying local rate.");
            System.out.println("Cost: " + (((traveled * .05) + traveled) * km + (traveled + total)));
        } else if (traveled > 400) {
            System.out.println("Distance > 400. Long distance.");
            // Placeholder logic for empty block
            System.out.println("Cost: " + (total + (traveled * km * 1.5)));
            System.out.println("(Includes long-distance surcharge)");
        } else {
            System.out.println("Standard distance (50-400).");
            // Placeholder logic for empty block
            System.out.println("Cost: " + (total + (traveled * km)));
        }
        System.out.println("System: Application End");
    }
}
// Usage: Run this file. Enter distance in km. Calculates moving cost.
