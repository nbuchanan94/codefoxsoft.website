
//Nicholas Larkin Buchanan
//2010-02-17
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProductTotalCalculator extends JFrame {

    private double priceTotal = 0.0;
    private JTextArea receiptArea;
    private JTextField priceField;
    private JTextField nameField; // Fixed: Class member
    private JTextField taxField;
    private JLabel totalLabel;

    public ProductTotalCalculator() {
        initGUI();
    }

    private void initGUI() {
        setTitle("ProductTotalCalculator - Nick Buchanan");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Shopping Notepad Theme
        Color paperYellow = new Color(255, 255, 220); // Light Notepad Yellow
        Color lineBlue = new Color(200, 200, 255); // Subtle blueish check hint

        getContentPane().setBackground(paperYellow);

        // Top Panel: Tax and Input
        JPanel topP = new JPanel(new GridLayout(2, 1));
        topP.setOpaque(false);

        // Tax Row
        JPanel taxP = new JPanel();
        taxP.setBackground(paperYellow);
        taxP.add(new JLabel("Tax Rate (empty=6%): "));
        taxField = new JTextField(5);
        taxField.addActionListener(e -> updateTotal());
        taxP.add(taxField);
        topP.add(taxP);

        // Input Row
        JPanel inP = new JPanel();
        inP.setBackground(paperYellow);

        inP.add(new JLabel("Item:"));
        nameField = new JTextField(8);
        inP.add(nameField);

        inP.add(new JLabel("Price: $"));
        priceField = new JTextField(6);

        ActionListener adder = e -> addPrice();
        nameField.addActionListener(adder);
        priceField.addActionListener(adder);

        inP.add(priceField);
        JButton addBtn = new JButton("Add");
        addBtn.setBackground(new Color(255, 200, 100)); // Orange-ish marker color
        addBtn.addActionListener(adder);
        inP.add(addBtn);
        topP.add(inP);

        add(topP, BorderLayout.NORTH);

        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 14)); // Handwriting style
        receiptArea.setBackground(paperYellow);
        receiptArea.setText("--- Shopping List ---\n");
        add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        totalLabel = new JLabel("Total: $0.00", SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        add(totalLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addPrice() {
        try {
            double p = Double.parseDouble(priceField.getText());
            priceTotal += p;

            String name = nameField.getText().trim();
            if (name.isEmpty())
                name = "Item";

            // Using tab separation
            receiptArea.append(String.format("%s\t$%.2f\n", name, p));
            updateTotal();

            priceField.setText("");
            nameField.setText(""); // Ready for next item
            nameField.requestFocus(); // Move focus back to name
        } catch (Exception e) {
        }
    }

    private void updateTotal() {
        double taxRate = 0.06; // Default
        try {
            String tStr = taxField.getText().trim();
            if (!tStr.isEmpty()) {
                double val = Double.parseDouble(tStr);
                if (val >= 1.0) {
                    taxRate = val / 100.0;
                } else {
                    taxRate = val;
                }
            }
        } catch (Exception e) {
        }

        double tax = priceTotal * taxRate;
        double total = priceTotal + tax;
        totalLabel.setText(String.format("Sub: $%.2f | Tax(%.1f%%): $%.2f | Tot: $%.2f",
                priceTotal, taxRate * 100, tax, total));
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new ProductTotalCalculator());
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Tax Rate (e.g. 6 for 6%) [Default 6%]: ");
        String taxIn = scanner.nextLine().trim();
        double taxRate = 0.06;
        if (!taxIn.isEmpty()) {
            try {
                double val = Double.parseDouble(taxIn);
                if (val >= 1.0)
                    taxRate = val / 100.0;
                else
                    taxRate = val;
            } catch (Exception e) {
                System.out.println("Invalid tax input. Using 6%.");
            }
        }

        System.out.print("How many products? ");
        int times = 0;
        try {
            times = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        double price = 0;

        System.out.println("--- Enter Items ---");
        for (int i = 1; i <= times; i++) {
            System.out.print("#" + i + " Name (Enter for 'Item'): ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty())
                name = "Item";

            System.out.print("#" + i + " Price: $");
            try {
                double p = Double.parseDouble(scanner.nextLine());
                price += p;
                System.out.printf("   -> Added: %s\t$%.2f\n", name, p);
            } catch (Exception e) {
            }
        }

        double tax = price * taxRate;
        double total = price + tax;

        System.out.println("\n--- Final Receipt ---");
        System.out.printf("Subtotal: $%.2f\n", price);
        System.out.printf("Tax (%.1f%%): $%.2f\n", taxRate * 100, tax);
        System.out.printf("Total:    $%.2f\n", total);
    }
}
// Usage: Sums product prices and applies 6% tax.
