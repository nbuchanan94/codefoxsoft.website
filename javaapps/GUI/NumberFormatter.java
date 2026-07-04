
/*
 * Nicholas Larkin Buchanan
 * April 4, 2012
 *
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;

public class NumberFormatter extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;
    private JLabel demoLabel;

    public NumberFormatter() {
        super("NumberFormatter - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 250);
        setLayout(new BorderLayout(15, 15));

        // Gold Theme
        Color gold = new Color(255, 215, 0);
        getContentPane().setBackground(new Color(255, 250, 220));

        JLabel title = new JLabel("Decimal Format Demo", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(new Color(184, 134, 11));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        inputField = new JTextField("123456789.98765");
        JButton formatButton = new JButton("Format Number");
        formatButton.setBackground(gold);

        resultLabel = new JLabel("Formatted: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Monospaced", Font.BOLD, 18));

        centerPanel.add(inputField);
        centerPanel.add(formatButton);
        centerPanel.add(resultLabel);
        add(centerPanel, BorderLayout.CENTER);

        demoLabel = new JLabel("Pattern: #,###.####", SwingConstants.CENTER);
        add(demoLabel, BorderLayout.SOUTH);

        formatButton.addActionListener(e -> format());

        // Run once on start
        format();

        setVisible(true);
    }

    private void format() {
        try {
            double num = Double.parseDouble(inputField.getText());
            DecimalFormat f = new DecimalFormat("#,###.####");
            resultLabel.setText("Formatted: " + f.format(num));
        } catch (NumberFormatException e) {
            resultLabel.setText("Error: Invalid Number");
        }
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(NumberFormatter::new);
        }
    }

    public static void runConsole(String[] args) {
        DecimalFormat f = new DecimalFormat("#,###.####");
        double number = 123456789654.96874538426;
        System.out.println("Formatted Number: " + f.format(number));
    }
}
