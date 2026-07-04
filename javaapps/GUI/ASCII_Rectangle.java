
//Nicholas Larkin Buchanan
//2011-11-10
//pd 8
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ASCII_Rectangle extends JFrame {

    private JTextField heightField, widthField;
    private JTextArea outputArea;

    public ASCII_Rectangle() {
        initGUI();
    }

    private void initGUI() {
        setTitle("ASCII_Rectangle - Nick Buchanan");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(70, 130, 180)); // Steel Blue

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(new Color(176, 196, 222));

        controlPanel.add(new JLabel("Height:"));
        heightField = new JTextField(5);
        controlPanel.add(heightField);

        controlPanel.add(new JLabel("Width:"));
        widthField = new JTextField(5);
        controlPanel.add(widthField);

        JButton drawBtn = new JButton("Draw Rectangle");
        drawBtn.setBackground(Color.ORANGE);
        drawBtn.addActionListener(e -> draw());
        controlPanel.add(drawBtn);

        add(controlPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.BOLD, 18));
        outputArea.setBackground(new Color(240, 248, 255));
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void draw() {
        try {
            int h = Integer.parseInt(heightField.getText());
            int w = Integer.parseInt(widthField.getText());
            StringBuilder sb = new StringBuilder();
            sb.append("Drawing " + h + " by " + w + "\n\n");
            for (int i = 1; i <= w; i++) {
                for (int j = 1; j < h; j++) {
                    sb.append("*");
                }
                sb.append("*\n");
            }
            outputArea.setText(sb.toString());
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new ASCII_Rectangle());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("System: Application Start");
        Scanner kbReader = new Scanner(System.in);
        System.out.println("Enter length:");
        int h = kbReader.nextInt();
        System.out.println("Enter width:");
        int w = kbReader.nextInt();

        System.out.println(
                "DEBUG: Starting generation of " + h + " by " + w + " rectangle (Note: loops may swap dimensions)");

        for (int i = 1; i <= w; i++) {

            for (int j = 1; j < h; j++) {
                System.out.print("*");

            }
            System.out.println("*");
        }
        System.out.println("System: Application End");
    }
}

// Usage: Prints an ASCII rectangle based on input dimensions.
