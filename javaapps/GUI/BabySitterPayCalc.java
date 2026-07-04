// nick buchanan
// 1-2-10
//pd 8

import java.util.*;
import java.text.*;
import javax.swing.*;
import java.awt.*;

public class BabySitterPayCalc extends JFrame {

    private JTextField n1, h1, k1;
    private JTextField n2, h2, k2;
    private JTextArea resultArea;

    public BabySitterPayCalc() {
        initGUI();
    }

    private void initGUI() {
        setTitle("BabySitterPayCalc - Nick Buchanan");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Playful Theme (Pink/Blue)
        getContentPane().setBackground(Color.WHITE);

        JPanel main = new JPanel(new GridLayout(1, 2, 10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Sitter 1 (Pink)
        JPanel p1 = new JPanel(new GridLayout(4, 2));
        p1.setBackground(new Color(255, 192, 203)); // Pink
        p1.setBorder(BorderFactory.createTitledBorder("Sitter 1"));
        p1.add(new JLabel("Name:"));
        n1 = new JTextField();
        p1.add(n1);
        p1.add(new JLabel("Hours:"));
        h1 = new JTextField();
        p1.add(h1);
        p1.add(new JLabel("Kids:"));
        k1 = new JTextField();
        p1.add(k1);
        main.add(p1);

        // Sitter 2 (Blue)
        JPanel p2 = new JPanel(new GridLayout(4, 2));
        p2.setBackground(new Color(173, 216, 230)); // Light Blue
        p2.setBorder(BorderFactory.createTitledBorder("Sitter 2"));
        p2.add(new JLabel("Name:"));
        n2 = new JTextField();
        p2.add(n2);
        p2.add(new JLabel("Hours:"));
        h2 = new JTextField();
        p2.add(h2);
        p2.add(new JLabel("Kids:"));
        k2 = new JTextField();
        p2.add(k2);
        main.add(p2);

        add(main, BorderLayout.CENTER);

        JPanel bot = new JPanel(new BorderLayout());
        JButton calcBtn = new JButton("Calculate Pay ($2.50/kid/hr)");
        calcBtn.addActionListener(e -> calculate());
        bot.add(calcBtn, BorderLayout.NORTH);

        resultArea = new JTextArea(5, 40);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setEditable(false);
        bot.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(bot, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void calculate() {
        try {
            DecimalFormat money = new DecimalFormat("$#,###.00");

            // Sitter 1
            String name1 = n1.getText();
            double hr1 = Double.parseDouble(h1.getText());
            int kid1 = Integer.parseInt(k1.getText());
            double pay1 = 2.5 * kid1 * hr1;

            // Sitter 2
            String name2 = n2.getText();
            double hr2 = Double.parseDouble(h2.getText());
            int kid2 = Integer.parseInt(k2.getText());
            double pay2 = 2.5 * kid2 * hr2;

            String sb = "Sitter\tHours\tKids\tTotal Made\n";
            sb += name1 + "\t" + hr1 + "\t" + kid1 + "\t" + money.format(pay1) + "\n";
            sb += name2 + "\t" + hr2 + "\t" + kid2 + "\t" + money.format(pay2) + "\n";

            resultArea.setText(sb);

        } catch (Exception e) {
            resultArea.setText("Invalid Input");
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new BabySitterPayCalc());
        }
    }

    public static void runConsole(String[] args) {
        // Display Program Header
        System.out.println("=====================");
        System.out.println("+++BabySitter.java+++");
        System.out.println("=====================");

        // Display the base pay rate to the user
        System.out.println("Payrate: $2.50 per kid per hour");
        System.out.println();

        // Initialize formatting for currency
        DecimalFormat money = new DecimalFormat("$#,###.00");
        // Initialize scanner for user input
        Scanner kbReader = new Scanner(System.in);

        // --- FIRST BABYSITTER INPUT ---
        System.out.print("Sitters Name: ");
        String name = kbReader.next();

        System.out.print("Hours worked: ");
        double hoursWorked = kbReader.nextDouble();

        System.out.print("Kids Watched: ");
        int kidsWatched = kbReader.nextInt();

        // Rate per kid per hour
        double amountPay = 2.5;
        // Calculate total pay: Rate * Kids * Hours
        double amountPayed = amountPay * kidsWatched * hoursWorked;

        // --- SECOND BABYSITTER INPUT ---
        System.out.println(); // Add spacing
        System.out.print("Sitters Name: ");
        String name1 = kbReader.next();

        System.out.print("Hours worked: ");
        double hoursWorked1 = kbReader.nextDouble();

        System.out.print("Kids Watched: ");
        int kidsWatched1 = kbReader.nextInt();

        double amountPay1 = 2.5;
        // Calculate total pay for second sitter
        double amountPayed1 = amountPay1 * kidsWatched1 * hoursWorked1;

        // --- OUTPUT TABLE ---
        System.out.println("\nSitter\t\tHours\t\tKids\t\tTotal Made");
        // Print details for first sitter
        System.out.println(name + "\t\t" + hoursWorked + "\t\t" + kidsWatched + "\t\t" + money.format(amountPayed));
        // Print details for second sitter
        System.out.println(name1 + "\t\t" + hoursWorked1 + "\t\t" + kidsWatched1 + "\t\t" + money.format(amountPayed1));

        // Close scanner
        kbReader.close();
    }
}

/*
 * PROGRAM DESCRIPTION:
 * This program calculates the total pay for two babysitters based on their
 * hours worked and the number of kids watched.
 */
