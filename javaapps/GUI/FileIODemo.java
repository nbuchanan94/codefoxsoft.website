
//Nicholas Larkin Buchanan
//2012-11-20
//pd 8
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class FileIODemo extends JFrame {

    private JTextField fileF, nameF, dobF;
    private JTextArea logArea;

    public FileIODemo() {
        initGUI();
    }

    private void initGUI() {
        setTitle("FileIODemo - Nick Buchanan");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Office Theme
        getContentPane().setBackground(new Color(255, 250, 205)); // Lemon

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.setOpaque(false);

        form.add(new JLabel("Filename:"));
        fileF = new JTextField("data.txt");
        form.add(fileF);
        form.add(new JLabel("Name:"));
        nameF = new JTextField();
        form.add(nameF);
        form.add(new JLabel("DOB:"));
        dobF = new JTextField();
        form.add(dobF);

        JButton btn = new JButton("Execute IO (Write if new)");
        btn.addActionListener(e -> process());
        form.add(btn);

        JButton readBtn = new JButton("Read File");
        readBtn.addActionListener(e -> readFile());
        form.add(readBtn);

        add(form, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setBorder(BorderFactory.createTitledBorder("Log / File Content"));
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void process() {
        String fn = fileF.getText();
        File f = new File(fn);
        logArea.setText("Checking " + f.getAbsolutePath() + "\n");

        try {
            if (!f.exists()) {
                logArea.append("File not found. Creating new...\n");
                f.createNewFile();

                BufferedWriter w = new BufferedWriter(new FileWriter(f));
                w.write("NAME: " + nameF.getText());
                w.write("\nDOB: " + dobF.getText());
                w.close();
                logArea.append("Data wrote successfully.\n");
            } else {
                logArea.append("File exists. (Use 'Read File' to view content)\n");
                // Original logic read it here, but maybe user wants separation?
                // I'll keep the check simply reporting existence if we have a dedicated read
                // button
                // Or I can call readFile() here too?
                // Let's call readFile() to maintain original behavior of "Execute IO"
                readFile();
            }
        } catch (Exception e) {
            logArea.append("Error: " + e.getMessage());
        }
    }

    private void readFile() {
        String fn = fileF.getText();
        File f = new File(fn);
        logArea.append("Reading " + f.getAbsolutePath() + "...\n");

        if (!f.exists()) {
            logArea.append("Error: File does not exist.\n");
            return;
        }

        try {
            BufferedReader r = new BufferedReader(new FileReader(f));
            String line;
            while ((line = r.readLine()) != null) {
                logArea.append(line + "\n");
            }
            r.close();
            logArea.append("-- End of File --\n");
        } catch (Exception e) {
            logArea.append("Error reading: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new FileIODemo());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("System: Application Start");

        System.out.println("Enter name of file: ");

        try {

            BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));// sets keyboardInput as
                                                                                                // the
                                                                                                // class that gets input
                                                                                                // from
                                                                                                // keyboard
            String FileName = keyboardInput.readLine();
            System.out.println("DEBUG: Filename input: " + FileName);

            File f;
            f = new File(FileName);
            System.out.println("DEBUG: Checking existence of file...");

            if (!f.exists()) {
                System.out.println("DEBUG: File not found. Creating...");
                f.createNewFile();
                System.out.println("New file \"" + FileName + "\" has been created to the current directory");

                BufferedWriter write1 = new BufferedWriter(new FileWriter(f));

                System.out.print("Enter Name:");
                String name = keyboardInput.readLine();
                write1.write("NAME: " + name);

                System.out.print("Date of Birth?");

                String DOB = keyboardInput.readLine();
                write1.write("\n" + "DOB: " + DOB);
                write1.close();
                System.out.println("DEBUG: Data written and file closed.");

            } else {
                System.out.println("File exists. Continuing with that file...");
                System.out.println("DEBUG: Reading content...");
                BufferedReader is = new BufferedReader(new FileReader(f));
                System.out.println(is.readLine());
                System.out.println(is.readLine());

            }

        } catch (IOException e) {
            System.err.println("Unexpected IO ERROR: " + e);
        }

        System.out.println("System: Application End");

    }
}
// Usage: Run this file. Enter a filename. Writes Name/DOB if new, reads it if
// exists.
