
//Nicholas Larkin Buchanan
//2012-05-20

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DecimalConverterGUI extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;

    private JRadioButton optDecToBin, optBinToDec;
    private JRadioButton optDecToOct, optOctToDec;
    private JRadioButton optBinToOct, optOctToBin;
    private JRadioButton optDecToHex, optHexToDec;
    private JRadioButton optBinToHex, optHexToBin;

    public DecimalConverterGUI() {
        setTitle("Number System Converter");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        inputField = new JTextField(15);
        JButton convertButton = new JButton("Convert");
        resultLabel = new JLabel("Result: ");

        ButtonGroup group = new ButtonGroup();
        Box radioBox = Box.createVerticalBox();

        optDecToBin = addRadio(group, radioBox, "Decimal to Binary");
        optBinToDec = addRadio(group, radioBox, "Binary to Decimal");
        optDecToOct = addRadio(group, radioBox, "Decimal to Octal");
        optOctToDec = addRadio(group, radioBox, "Octal to Decimal");
        optBinToOct = addRadio(group, radioBox, "Binary to Octal");
        optOctToBin = addRadio(group, radioBox, "Octal to Binary");
        optDecToHex = addRadio(group, radioBox, "Decimal to Hex");
        optHexToDec = addRadio(group, radioBox, "Hex to Decimal");
        optBinToHex = addRadio(group, radioBox, "Binary to Hex");
        optHexToBin = addRadio(group, radioBox, "Hex to Binary");

        optDecToBin.setSelected(true); // Default

        add(new JLabel("Enter Value:"));
        add(inputField);
        add(convertButton);
        add(radioBox);
        add(resultLabel);

        convertButton.addActionListener(e -> convert());
    }

    private JRadioButton addRadio(ButtonGroup g, Box b, String text) {
        JRadioButton r = new JRadioButton(text);
        g.add(r);
        b.add(r);
        return r;
    }

    private void convert() {
        String input = inputField.getText();
        try {
            if (optDecToBin.isSelected()) {
                int val = Integer.parseInt(input);
                resultLabel.setText("Binary: " + Integer.toBinaryString(val));
            } else if (optBinToDec.isSelected()) {
                resultLabel.setText("Decimal: " + Integer.parseInt(input, 2));
            } else if (optDecToOct.isSelected()) {
                int val = Integer.parseInt(input);
                resultLabel.setText("Octal: " + Integer.toOctalString(val));
            } else if (optOctToDec.isSelected()) {
                resultLabel.setText("Decimal: " + Integer.parseInt(input, 8));
            } else if (optBinToOct.isSelected()) {
                long val = Long.parseLong(input, 2);
                resultLabel.setText("Octal: " + Long.toOctalString(val));
            } else if (optOctToBin.isSelected()) {
                long val = Long.parseLong(input, 8);
                resultLabel.setText("Binary: " + Long.toBinaryString(val));
            } else if (optDecToHex.isSelected()) {
                int val = Integer.parseInt(input);
                resultLabel.setText("Hex: " + Integer.toHexString(val).toUpperCase());
            } else if (optHexToDec.isSelected()) {
                resultLabel.setText("Decimal: " + Integer.parseInt(input, 16));
            } else if (optBinToHex.isSelected()) {
                long val = Long.parseLong(input, 2);
                resultLabel.setText("Hex: " + Long.toHexString(val).toUpperCase());
            } else if (optHexToBin.isSelected()) {
                int val = Integer.parseInt(input, 16);
                resultLabel.setText("Binary: " + Integer.toBinaryString(val));
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("Error: Invalid Input format");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DecimalConverterGUI().setVisible(true);
        });
    }
}
// Usage: Swing GUI for Number System Conversion.
