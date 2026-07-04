
//Nicholas Larkin Buchanan
//2009-11-15
 import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddThis extends JFrame {
    private JTextField numberField;
    private JLabel resultLabel;
    private JButton addButton;

    public AddThis() {
        setTitle("Add This");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        numberField = new JTextField(10);
        JTextField numberField2 = new JTextField(10);

        addButton = new JButton("Add");
        resultLabel = new JLabel("Result: ");

        add(new JLabel("Num 1:"));
        add(numberField);
        add(new JLabel("Num 2:"));
        add(numberField2);
        add(addButton);
        add(resultLabel);

        addButton.addActionListener(e -> {
            try {
                int num1 = Integer.parseInt(numberField.getText());
                int num2 = Integer.parseInt(numberField2.getText());
                int answer = num1 + num2;
                resultLabel.setText("Result: " + answer);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Error: Invalid Number");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AddThis().setVisible(true);
        });
    }
}
// Usage: Swing GUI. Adds two numbers.
