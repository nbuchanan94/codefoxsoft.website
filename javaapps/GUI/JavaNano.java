// Nicholas Buchanan
// December 2011

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class JavaNano extends JFrame {

    private static final String FILENAME = "notepad_data.txt";
    private static ArrayList<String> buffer = new ArrayList<>();
    private JTextArea textArea;

    public JavaNano() {
        initGUI();
    }

    private void initGUI() {
        setTitle("JavaNano - Nick Buchanan");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Terminal Theme
        textArea = new JTextArea();
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setCaretColor(Color.GREEN);
        JScrollPane sp = new JScrollPane(textArea);
        add(sp, BorderLayout.CENTER);

        JPanel menu = new JPanel(new GridLayout(1, 4));
        menu.setBackground(Color.DARK_GRAY);

        JButton sBtn = new JButton("Write (:w)");
        sBtn.addActionListener(e -> save());
        menu.add(sBtn);

        JButton lBtn = new JButton("Load (:r)");
        lBtn.addActionListener(e -> load());
        menu.add(lBtn);

        JButton xBtn = new JButton("Exit (:x)");
        xBtn.addActionListener(e -> System.exit(0));
        menu.add(xBtn);

        add(menu, BorderLayout.SOUTH);

        // Load on start
        load();

        setVisible(true);
    }

    private void save() {
        try {
            FileWriter fw = new FileWriter(FILENAME);
            PrintWriter output = new PrintWriter(fw);
            output.print(textArea.getText());
            output.close();
            fw.close();
            JOptionPane.showMessageDialog(this, "Wrote to " + FILENAME);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file.");
        }
    }

    private void load() {
        try {
            File file = new File(FILENAME);
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                StringBuilder sb = new StringBuilder();
                while (fileScanner.hasNextLine()) {
                    sb.append(fileScanner.nextLine() + "\n");
                }
                fileScanner.close();
                textArea.setText(sb.toString());
            }
        } catch (FileNotFoundException e) {
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new JavaNano());
        }
    }

    private static void loadFile() {
        try {
            File file = new File(FILENAME);
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    buffer.add(fileScanner.nextLine());
                }
                fileScanner.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading file.");
        }
    }

    private static void saveFile() {
        try {
            FileWriter fw = new FileWriter(FILENAME);
            PrintWriter output = new PrintWriter(fw);

            for (String line : buffer) {
                output.println(line);
            }

            output.close();
            fw.close();
            System.out.println(" [ Wrote " + buffer.size() + " lines ]");
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    private static void printInterface() {
        // "Clear" screen
        for (int i = 0; i < 3; i++)
            System.out.println();

        System.out.println("  FAUXCNANO             File: " + FILENAME);
        System.out.println("--------------------------------------------------------------------------------");

        if (buffer.isEmpty()) {
            System.out.println("  [ New File ]");
        } else {
            for (int i = 0; i < buffer.size(); i++) {
                System.out.println((i + 1) + "  " + buffer.get(i));
            }
        }

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(":G Get Help   :O WriteOut   :R Read File   :Y Prev Page   :K Cut Text    :C Cur Pos");
        System.out.println(":X Exit       :J Justify    :W Where Is    :V Next Page   :U UnCut Text  :T To Spell");
        System.out.println("\n(Simulation Note: Type ':w' to Save, ':x' to Exit, ':p' to Reprint View)");
    }

    public static void runConsole(String[] args) {
        loadFile();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to JavaNano via Console!");
        printInterface();

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase(":x") || input.equalsIgnoreCase(":exit")) {
                System.out.print("Save changes? (y/n): ");
                String save = scanner.nextLine();
                if (save.equalsIgnoreCase("y")) {
                    saveFile();
                }
                break;
            } else if (input.equalsIgnoreCase(":w") || input.equalsIgnoreCase(":save")) {
                saveFile();
                printInterface(); // Redraw
            } else if (input.equalsIgnoreCase(":p") || input.equalsIgnoreCase(":print")) {
                printInterface();
            } else {
                buffer.add(input);
                // Optional: Auto-save or just keep in memory. Nano keeps in memory.
            }
        }

        System.out.println("Exited JavaNano.");
        scanner.close();
    }
}
/*
 * 1. Persistent Storage: Automatically loads and saves to 'notepad_data.txt'.
 * 2. Line Buffer: Stores text in memory (ArrayList) during editing.
 */
