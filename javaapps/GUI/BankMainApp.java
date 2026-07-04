
/*
 * Nicholas Larkin Buchanan
 * 2010-09-15
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BankMainApp extends JFrame {
    private BankHelper bankAccount;
    private JTextField amountField;
    private JLabel balanceLabel;
    private JTextArea logArea;
    private JPanel actionPanel;
    private JPanel loginPanel;
    private JTextField nameField;

    public BankMainApp() {
        super("BankMainApp - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout(10, 10));

        // ATM Theme (Dark Green/White)
        getContentPane().setBackground(new Color(0, 100, 50));

        // Login Panel
        loginPanel = new JPanel(new FlowLayout());
        loginPanel.setOpaque(false);
        JLabel nameLabel = new JLabel("Enter Name:");
        nameLabel.setForeground(Color.WHITE);
        nameField = new JTextField(15);
        JButton startButton = new JButton("Open Account");
        startButton.setBackground(new Color(0, 200, 100));

        loginPanel.add(nameLabel);
        loginPanel.add(nameField);
        loginPanel.add(startButton);

        add(loginPanel, BorderLayout.NORTH);

        // Action Panel (Hidden initially)
        actionPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        actionPanel.setOpaque(false);
        actionPanel.setVisible(false);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setOpaque(false);
        JLabel amtLabel = new JLabel("Amount:");
        amtLabel.setForeground(Color.WHITE);
        amountField = new JTextField(10);
        inputPanel.add(amtLabel);
        inputPanel.add(amountField);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setOpaque(false);
        JButton depButton = new JButton("Deposit");
        JButton wdButton = new JButton("Withdraw");
        JButton balButton = new JButton("Check Balance");
        btnPanel.add(depButton);
        btnPanel.add(wdButton);
        btnPanel.add(balButton);

        balanceLabel = new JLabel("Balance: $0.0", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        balanceLabel.setForeground(Color.YELLOW);

        actionPanel.add(balanceLabel);
        actionPanel.add(inputPanel);
        actionPanel.add(btnPanel);

        add(actionPanel, BorderLayout.CENTER);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(logArea), BorderLayout.SOUTH);

        // Events
        startButton.addActionListener(e -> {
            String name = nameField.getText();
            if (!name.isEmpty()) {
                bankAccount = new BankHelper(name, 1000.0);
                loginPanel.setVisible(false);
                actionPanel.setVisible(true);
                updateDisplay("Account created for " + name + " with $1000.0");
                add(new JLabel("Welcome, " + name, SwingConstants.CENTER), BorderLayout.NORTH);
                revalidate();
            }
        });

        depButton.addActionListener(e -> {
            try {
                double val = Double.parseDouble(amountField.getText());
                bankAccount.deposit(val);
                updateDisplay("Deposited: $" + val);
            } catch (Exception ex) {
                log("Invalid Amount");
            }
        });

        wdButton.addActionListener(e -> {
            try {
                double val = Double.parseDouble(amountField.getText());
                bankAccount.withdraw(val);
                updateDisplay("Withdrew: $" + val);
            } catch (Exception ex) {
                log("Invalid Amount");
            }
        });

        balButton.addActionListener(e -> updateDisplay("Checked Balance"));

        setVisible(true);
    }

    private void updateDisplay(String msg) {
        log(msg);
        balanceLabel.setText("Balance: $" + bankAccount.balence);
        logArea.append(bankAccount.getBalanceString() + "\n");
    }

    private void log(String msg) {
        logArea.append("System: " + msg + "\n");
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(BankMainApp::new);
        }
    }

    public static void runConsole(String[] args) {
        // Original logic adapted
        java.util.Scanner kb = new java.util.Scanner(System.in);
        System.out.println("System: Application Start");
        System.out.print("What is your name? ");
        String nm = kb.nextLine();
        double balence = 1000;
        System.out.println("Your current balence is " + balence);

        BankHelper ba = new BankHelper(nm, balence);

        System.out.print("How much do you want to deposite? ");
        double dp = kb.nextDouble();
        ba.deposit(dp);
        ba.printnewbalence();

        System.out.print("How much do you want do withdraw? ");
        double wd = kb.nextDouble();
        ba.withdraw(wd);
        ba.printnewbalence();

        System.out.println("System: Application End");
    }
}
