
//Nicholas Larkin Buchanan
//2013-05-15
//pd 8
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class PhoneNumberConverter extends JFrame {

    private JTextField inputF;
    private JLabel resultL;

    public PhoneNumberConverter() {
        initGUI();
    }

    private void initGUI() {
        setTitle("PhoneNumberConverter - Nick Buchanan");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Yellow Pages Theme
        Color ypYellow = new Color(255, 225, 0); // Classic Yellow
        Color ypBlack = Color.BLACK;

        getContentPane().setBackground(ypYellow);
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input Panel
        JPanel inputP = new JPanel(new GridLayout(2, 1, 5, 5));
        inputP.setOpaque(false);

        JLabel promptL = new JLabel(
                "<html><center>ENTER PHONE NUMBER:<br><span style='font-size:12px; font-weight:normal'>(e.g. 1-800-FLOWERS)</span></center></html>",
                SwingConstants.CENTER);
        promptL.setFont(new Font("Arial", Font.BOLD, 16));
        promptL.setForeground(ypBlack);
        inputP.add(promptL);

        inputF = new JTextField(15);
        inputF.setFont(new Font("Monospaced", Font.PLAIN, 18));
        inputF.setHorizontalAlignment(JTextField.CENTER);
        inputF.setBorder(BorderFactory.createLineBorder(ypBlack, 2));
        inputF.addActionListener(e -> convert());
        inputP.add(inputF);

        add(inputP, BorderLayout.NORTH);

        // Convert Button (Middle)
        JButton btn = new JButton("CONVERT NOW");
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.YELLOW);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.addActionListener(e -> convert());

        JPanel btnP = new JPanel();
        btnP.setOpaque(false);
        btnP.add(btn);
        add(btnP, BorderLayout.CENTER);

        // Result Label (Bottom)
        resultL = new JLabel("Waiting...", SwingConstants.CENTER);
        resultL.setFont(new Font("Courier New", Font.BOLD, 28));
        resultL.setForeground(ypBlack);
        add(resultL, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void convert() {
        String input = inputF.getText();
        StringBuilder digits = new StringBuilder();

        // 1. Convert convertable chars and strip others
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                digits.append(c);
            } else if (Character.isLetter(c)) {
                digits.append(getNumber(c));
            }
        }

        String raw = digits.toString();

        // 2. Format
        if (raw.length() == 10) {
            String f = String.format("(%s) %s-%s",
                    raw.substring(0, 3),
                    raw.substring(3, 6),
                    raw.substring(6, 10));
            resultL.setText(f);
        } else if (raw.length() == 11 && raw.startsWith("1")) {
            String f = String.format("1 (%s) %s-%s",
                    raw.substring(1, 4),
                    raw.substring(4, 7),
                    raw.substring(7, 11));
            resultL.setText(f);
        } else {
            // Fallback if not 10 digits
            resultL.setText(raw);
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new PhoneNumberConverter());
        }
    }

    public static int getNumber(char letter) {
        letter = Character.toLowerCase(letter);
        int i = 0;

        if (letter >= 'a' && letter <= 'c')
            i = 2;
        else if (letter >= 'd' && letter <= 'f')
            i = 3;
        else if (letter >= 'g' && letter <= 'i')
            i = 4;
        else if (letter >= 'j' && letter <= 'l')
            i = 5;
        else if (letter >= 'm' && letter <= 'o')
            i = 6;
        else if (letter >= 'p' && letter <= 's')
            i = 7;
        else if (letter >= 't' && letter <= 'v')
            i = 8;
        else if (letter >= 'w' && letter <= 'z')
            i = 9;

        return i;
    }

    public static void runConsole(String[] args) {
        System.out.println("System: Application Start");
        Scanner kb = new Scanner(System.in);

        System.out.println("Enter alphanumeric phone number (e.g. 1-800-FLOWERS):");
        String input = kb.nextLine();

        System.out.print("Converted Number: ");
        for (int i = 0; i < input.length(); i++) {
            char original = input.charAt(i);
            if (Character.isLetter(original)) {
                System.out.print(getNumber(original));
            } else {
                // Return original char if not a letter (digits, dashes)
                System.out.print(original);
            }
        }
        System.out.println(); // Newline result
        System.out.println("System: Application End");
    }
}
// Usage: Run. Input "1-800-FLOWERS". Output "1-800-3569377".
