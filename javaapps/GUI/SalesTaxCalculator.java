// Nicholas Buchanan
// 2/17/10

import java.util.Scanner;
import java.text.NumberFormat;
import javax.swing.*;
import java.awt.*;

public class SalesTaxCalculator extends JFrame {

    private DefaultListModel<Double> listModel;
    private JLabel totalLabel;
    private JTextField priceField;

    public SalesTaxCalculator() {
        initGUI();
    }

    private void initGUI() {
        setTitle("SalesTaxCalculator - Nick Buchanan");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Receipt Theme (White/Black)
        getContentPane().setBackground(Color.WHITE);

        JPanel top = new JPanel();
        top.setBackground(Color.WHITE);
        top.add(new JLabel("Price: $"));
        priceField = new JTextField(10);
        priceField.addActionListener(e -> addPrice());
        top.add(priceField);
        JButton addBtn = new JButton("Add Item");
        addBtn.addActionListener(e -> addPrice());
        top.add(addBtn);
        add(top, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        JList<Double> list = new JList<>(listModel);
        list.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel bot = new JPanel(new BorderLayout());
        bot.setBackground(Color.WHITE);
        JButton calcBtn = new JButton("Calculate Total");
        calcBtn.addActionListener(e -> calc());
        bot.add(calcBtn, BorderLayout.NORTH);

        totalLabel = new JLabel("<html>Sub: $0.00<br>Tax: $0.00<br>Total: $0.00</html>", SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bot.add(totalLabel, BorderLayout.SOUTH);

        add(bot, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addPrice() {
        try {
            double p = Double.parseDouble(priceField.getText());
            listModel.addElement(p);
            priceField.setText("");
        } catch (Exception e) {
        }
    }

    private void calc() {
        double sub = 0;
        for (int i = 0; i < listModel.size(); i++)
            sub += listModel.get(i);
        double tax = sub * 0.06;
        double total = sub + tax;

        NumberFormat c = NumberFormat.getCurrencyInstance();
        totalLabel.setText("<html>Sub:  " + c.format(sub) + "<br>Tax:  " + c.format(tax)
                + "<br>----------------<br>Total: " + c.format(total) + "</html>");
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new SalesTaxCalculator());
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

        System.out.print("How many products? ");
        int times = 0;
        try {
            times = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        double price = 0;
        for (int i = 1; i <= times; i++) {
            System.out.print("What is the price of your product? ");
            try {
                price += Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number ignored.");
                i--; // retry
            }
        }

        double taxAmount = price * 0.06;
        double total = price + taxAmount;

        System.out.println("Your total before tax " + currencyFormatter.format(price));
        System.out.println("Your tax is " + currencyFormatter.format(taxAmount));
        System.out.println("Your total is: " + currencyFormatter.format(total));

        scanner.close();
    }
}
/*
 * 1. Asks user for number of items.
 * 2. Uses a loop to accumulate the price of each item entered by the user.
 * 3. Calculates a 6% sales tax on the subtotal.
 * 4. Outputs the subtotal, tax amount, and final total using currency
 * formatting.
 */
