
// Nicholas Larkin Buchanan
// May 23, 2011
import java.util.Scanner;
import java.text.NumberFormat;
import javax.swing.*;
import java.awt.*;

public class CarpetPriceEstimator extends JFrame {

    private JTextField widthF, lengthF;
    private JTextArea reportArea;

    public CarpetPriceEstimator() {
        initGUI();
    }

    private void initGUI() {
        setTitle("CarpetPriceEstimator - Nick Buchanan");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Interior Design Theme (Beige/Purple)
        getContentPane().setBackground(new Color(245, 245, 220)); // Beige

        JPanel top = new JPanel(new GridLayout(3, 2, 5, 5));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createTitledBorder("Room Dimensions (Feet)"));

        top.add(new JLabel("Width:"));
        widthF = new JTextField();
        top.add(widthF);

        top.add(new JLabel("Length:"));
        lengthF = new JTextField();
        top.add(lengthF);

        JButton btn = new JButton("Get Estimate");
        btn.setBackground(new Color(147, 112, 219)); // Purple
        btn.setForeground(Color.WHITE);
        btn.addActionListener(e -> calculate());
        top.add(btn);

        add(top, BorderLayout.NORTH);

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        reportArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void calculate() {
        try {
            double width = Double.parseDouble(widthF.getText());
            double length = Double.parseDouble(lengthF.getText());

            double widthYards = width / 3.0;
            double lengthYards = length / 3.0;
            double areaSqFeet = width * length;
            double areaSqYards = widthYards * lengthYards;
            double cost = areaSqYards * 29.89;

            NumberFormat currency = NumberFormat.getCurrencyInstance();

            String r = "--- ESTIMATE REPORT ---\n";
            r += "Dims: " + width + "' x " + length + "'\n";
            r += "Area (Sq Ft): " + areaSqFeet + "\n";
            r += String.format("Area (Sq Yd): %.2f\n", areaSqYards);
            r += "Price/SqYd:   $29.89\n";
            r += "-----------------------\n";
            r += "TOTAL COST:   " + currency.format(cost);

            reportArea.setText(r);

        } catch (Exception e) {
            reportArea.setText("Invalid Input");
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new CarpetPriceEstimator());
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NumberFormat currency = NumberFormat.getCurrencyInstance();

        System.out.println("::: Karpet King - Area & Price Calculator :::");
        System.out.println("Calculating based on $29.89 per sq. yard.");

        System.out.print("\nEnter room width (feet): ");
        double width = scanner.nextDouble();

        System.out.print("Enter room length (feet): ");
        double length = scanner.nextDouble();

        // Calculate dimensions in yards
        double widthYards = width / 3.0;
        double lengthYards = length / 3.0;

        // Calculate areas
        double areaSqFeet = width * length;
        double areaSqYards = widthYards * lengthYards;

        // Calculate cost
        double cost = areaSqYards * 29.89;

        System.out.println("\n--- ESTIMATE REPORT ---");
        System.out.println("Dimensions:      " + width + "' x " + length + "'");
        System.out.println("Area (Sq. Feet): " + areaSqFeet);
        System.out.printf("Area (Sq. Yards): %.2f\n", areaSqYards);
        System.out.println("Total Cost:      " + currency.format(cost));
    }
}
// Run to estimate carpet costs. Input dimensions in feet.
