
/*
 * Nicholas Buchanan
 * 3/4/10
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class RandomAlphabet extends JFrame {
    private JTextArea outputArea;
    private JButton generateButton;

    public RandomAlphabet() {
        super("RandomAlphabet - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout(10, 10));

        // Shuffle Theme (Purple)
        Color bg = new Color(100, 0, 150);
        getContentPane().setBackground(bg);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(bg);

        generateButton = new JButton("Generate Permutation");
        generateButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        generateButton.setBackground(Color.YELLOW);
        generateButton.setForeground(Color.BLACK);
        topPanel.add(generateButton);
        add(topPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.BOLD, 24));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setForeground(Color.WHITE);
        outputArea.setBackground(new Color(50, 0, 80));
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        generateButton.addActionListener(e -> generate());

        setVisible(true);
    }

    private void generate() {
        outputArea.setText("");
        Random random = new Random();
        boolean[] used = new boolean[26];
        int count = 0;

        // Original Logic: Keep picking random characters until all 26 are found
        while (count < 26) {
            int r = random.nextInt(26); // 0-25
            if (!used[r]) {
                used[r] = true;
                char c = (char) ('A' + r);
                outputArea.append(c + " ");
                count++;
            }
        }
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(RandomAlphabet::new);
        }
    }

    public static void runConsole(String[] args) {
        Random random = new Random();
        boolean[] used = new boolean[26];
        int count = 0;

        while (count < 26) {
            int r = random.nextInt(26);
            if (!used[r]) {
                used[r] = true;
                char c = (char) ('A' + r);
                System.out.print(c + " ");
                count++;
            }
        }
        System.out.println();
    }
}
