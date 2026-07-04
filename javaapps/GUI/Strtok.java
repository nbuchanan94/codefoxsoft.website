
/*
 * Nicholas Larkin Buchanan
 * 2011-02-05
 *
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.StringTokenizer;
import java.util.Scanner;

public class Strtok extends JFrame {
    private JTextField inputField;
    private DefaultListModel<String> listModel;

    public Strtok() {
        super("Strtok - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLayout(new BorderLayout(10, 10));

        // Parser Theme (Orange)
        Color accent = new Color(255, 140, 0); // Dark Orange
        ((JPanel) getContentPane()).setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, accent));

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel("Enter a sentence:");
        inputField = new JTextField();
        JButton tokenizeButton = new JButton("Tokenize");
        tokenizeButton.setBackground(accent);
        tokenizeButton.setForeground(Color.WHITE);

        topPanel.add(label, BorderLayout.NORTH);
        topPanel.add(inputField, BorderLayout.CENTER);
        topPanel.add(tokenizeButton, BorderLayout.EAST);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        add(topPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        JList<String> tokenList = new JList<>(listModel);
        tokenList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(tokenList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Tokens"));

        add(scrollPane, BorderLayout.CENTER);

        tokenizeButton.addActionListener(e -> tokenize());
        inputField.addActionListener(e -> tokenize());

        setVisible(true);
    }

    private void tokenize() {
        String text = inputField.getText();
        listModel.clear();

        // Original Logic used default delimiters (whitespace)
        StringTokenizer st = new StringTokenizer(text);
        while (st.hasMoreTokens()) {
            listModel.addElement(st.nextToken());
        }
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(Strtok::new);
        }
    }

    public static void runConsole(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter your sentence: ");
        String word = kb.nextLine();

        StringTokenizer st = new StringTokenizer(word);
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
    }
}
