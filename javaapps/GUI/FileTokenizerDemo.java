
//Nicholas Larkin Buchanan
//2013-01-05
//pd 8
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class FileTokenizerDemo extends JFrame {

    private JTextField fileF;
    private DefaultListModel<String> model;

    public FileTokenizerDemo() {
        initGUI();
    }

    private void initGUI() {
        setTitle("FileTokenizerDemo - Nick Buchanan");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Scanner Theme (Green Laser)
        getContentPane().setBackground(Color.BLACK);

        JPanel top = new JPanel();
        top.setBackground(Color.BLACK);
        JLabel l = new JLabel("File to Scan:");
        l.setForeground(Color.GREEN);
        top.add(l);
        fileF = new JTextField("test_io.txt", 15);
        top.add(fileF);
        JButton btn = new JButton("SCAN");
        btn.setBackground(Color.GREEN);
        btn.addActionListener(e -> scan());
        top.add(btn);
        add(top, BorderLayout.NORTH);

        model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        list.setBackground(Color.BLACK);
        list.setForeground(Color.GREEN);
        list.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(list), BorderLayout.CENTER);

        setVisible(true);
    }

    private void scan() {
        model.clear();
        String fn = fileF.getText();
        new Thread(() -> {
            try {
                File f = new File(fn);
                if (!f.exists()) {
                    SwingUtilities.invokeLater(() -> model.addElement("Error: File not found"));
                    return;
                }

                Scanner fileReader = new Scanner(f);
                while (fileReader.hasNextLine()) {
                    String line = fileReader.nextLine();
                    // Simulate delay for "Scanning" effect?
                    Thread.sleep(100);
                    StringTokenizer st = new StringTokenizer(line);
                    while (st.hasMoreTokens()) {
                        String t = st.nextToken();
                        SwingUtilities.invokeLater(() -> model.addElement("Token: " + t));
                    }
                }
                fileReader.close();
                SwingUtilities.invokeLater(() -> model.addElement("--- COMPLETE ---"));
            } catch (Exception e) {
            }
        }).start();
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new FileTokenizerDemo());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("System: Application Start");
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter name of file to tokenize (e.g., test_io.txt): ");
        String fileName = kb.nextLine();
        System.out.println("DEBUG: Input filename: " + fileName);

        File f = new File(fileName);
        if (!f.exists()) {
            System.out.println("Error: File not found.");
            return;
        }

        try {
            Scanner fileReader = new Scanner(f);
            System.out.println("DEBUG: File opened. Reading lines...");

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                System.out.println("DEBUG: Creating StringTokenizer for line: \"" + line + "\"");

                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens()) {
                    System.out.println("Token: " + st.nextToken());
                }
            }
            fileReader.close();
            System.out.println("DEBUG: File processing complete.");

        } catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("System: Application End");
    }
}
// Usage: Run this file. Enter a filename. It prints every word (token) in that
// file.
