
//Nicholas Larkin Buchanan
//2011-09-12
import javax.swing.*;
import java.awt.*;

public class Problem1GUI extends JFrame {
    private JTextField textField;
    private JLabel label;
    private JButton checkButton;

    public Problem1GUI() {
        setTitle("Problem 1: Easy Factors");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        textField = new JTextField(15);
        checkButton = new JButton("Check");
        label = new JLabel("Result will appear here");

        add(new JLabel("Enter Number:"));
        add(textField);
        add(checkButton);
        add(label);

        checkButton.addActionListener(e -> checkFactor());
    }

    private void checkFactor() {
        String input = textField.getText();
        int validDigits = 0;


        if (input.contains("2"))
            validDigits++;
        else if (input.contains("3"))
            validDigits++;
        else if (input.contains("4"))
            validDigits++;
        else if (input.contains("5"))
            validDigits++;
        else if (input.contains("6"))
            validDigits++;
        else if (input.contains("8"))
            validDigits++;
        else if (input.contains("9"))
            validDigits++;
        else
            validDigits = 0;

        if (validDigits == 1) {
            label.setText(input + " is easy");
        } else {
            label.setText(input + " is not easy");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Problem1GUI().setVisible(true);
        });
    }
}
// Usage: Swing GUI. Checks if a number string contains specific digits
// (2,3,4,5,6,8,9).
