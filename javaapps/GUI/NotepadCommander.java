
/*
 * Nicholas Larkin Buchanan
 * April 14, 2012
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;

public class NotepadCommander extends JFrame implements DocumentListener {
    private JTextPane mainArea; // Displays file content with highlighting
    private JTextField commandBar; // For commands like :w, :q...
    private ArrayList<String> buffer;
    private JLabel statusLabel;
    private JLabel suggestionLabel; // For displaying auto-complete suggestion

    // Resources
    private ArrayList<String> dictionary = new ArrayList<>();
    private ArrayList<String> highlights = new ArrayList<>();

    // Autocomplete Logic
    private String currentCompletion = null;

    // VIM Mode
    private boolean insertMode = false;

    public NotepadCommander() {
        super("NotepadCommander - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLayout(new BorderLayout());

        // Load resources
        loadResources();

        // Retro Theme (Black background, Gray Text)
        Color bg = Color.BLACK;
        Color fg = Color.LIGHT_GRAY;

        // Top Command List
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.DARK_GRAY);
        JLabel cmdList = new JLabel(
                "COMMANDS: :i(nsert) | :w(rite) [file] | :c(lear) | :u(pper) | :l(ower) | :s(ort) | :d(el) [line] | :q(uit) | TAB to complete");
        cmdList.setForeground(Color.CYAN);
        cmdList.setFont(new Font("Consolas", Font.BOLD, 12));
        topPanel.add(cmdList);
        add(topPanel, BorderLayout.NORTH);

        mainArea = new JTextPane();
        mainArea.setBackground(bg);
        mainArea.setForeground(fg);
        mainArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        mainArea.setEditable(false);

        buffer = new ArrayList<>();

        add(new JScrollPane(mainArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(bg);

        // Status Line Panel (Status Left, Suggestion Right)
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(bg);

        statusLabel = new JLabel("-- COMMAND MODE --");
        statusLabel.setForeground(Color.GREEN);
        statusLabel.setFont(new Font("Consolas", Font.BOLD, 12));
        statusPanel.add(statusLabel, BorderLayout.WEST);

        // Suggestion Label (Right aligned)
        suggestionLabel = new JLabel("");
        suggestionLabel.setForeground(Color.YELLOW);
        suggestionLabel.setFont(new Font("Consolas", Font.ITALIC, 14));
        statusPanel.add(suggestionLabel, BorderLayout.EAST);

        bottomPanel.add(statusPanel, BorderLayout.NORTH);

        commandBar = new JTextField();
        commandBar.setBackground(Color.DARK_GRAY);
        commandBar.setForeground(Color.WHITE);
        commandBar.setCaretColor(Color.WHITE);
        commandBar.setFont(new Font("Consolas", Font.PLAIN, 14));

        // Autocomplete Setup
        commandBar.getDocument().addDocumentListener(this);
        commandBar.setFocusTraversalKeysEnabled(false);

        InputMap im = commandBar.getInputMap();
        ActionMap am = commandBar.getActionMap();

        im.put(KeyStroke.getKeyStroke("ENTER"), "commitEnter");
        im.put(KeyStroke.getKeyStroke("TAB"), "commitTab");

        am.put("commitEnter", new CommitAction(true));
        am.put("commitTab", new CommitAction(false));

        bottomPanel.add(commandBar, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
        commandBar.requestFocus();
    }

    // --- Inner Classes ---

    private class CommitAction extends AbstractAction {
        private boolean isEnter;

        public CommitAction(boolean isEnter) {
            this.isEnter = isEnter;
        }

        public void actionPerformed(ActionEvent ev) {
            if (currentCompletion != null) {
                // Apply completion
                try {
                    String text = commandBar.getText();
                    if (text.endsWith(" ")) {
                        // Should not happen if logic is correct, but safety check
                    }

                    int caret = commandBar.getCaretPosition();
                    String content = commandBar.getText(0, caret);
                    int w;
                    for (w = caret - 1; w >= 0; w--) {
                        if (!Character.isLetter(content.charAt(w))) {
                            break;
                        }
                    }
                    // w is index of space or -1. Start of word is w+1.
                    String prefix = content.substring(0, w + 1);
                    String suffix = content.substring(caret);

                    // Add a trailing space for convenience
                    String replacement = prefix + currentCompletion + " " + suffix;
                    commandBar.setText(replacement);

                    // Move caret to the end of the inserted word + space
                    int newPos = (prefix + currentCompletion + " ").length();
                    // Or if we want to be safe and just go to end if suffix is empty:
                    if (suffix.isEmpty()) {
                        commandBar.setCaretPosition(replacement.length());
                    } else {
                        commandBar.setCaretPosition(newPos);
                    }

                    currentCompletion = null;
                    suggestionLabel.setText("");

                } catch (BadLocationException e) {
                    e.printStackTrace();
                }

                if (isEnter) {
                    NotepadCommander.this.processInput();
                }
            } else {
                if (isEnter) {
                    NotepadCommander.this.processInput();
                }
            }
        }
    }

    // --- DocumentListener Implementation ---
    @Override
    public void insertUpdate(DocumentEvent ev) {
        if (ev.getLength() != 1)
            return;

        try {
            int pos = ev.getOffset();
            String content = commandBar.getText(0, pos + 1);

            int w;
            for (w = pos; w >= 0; w--) {
                if (!Character.isLetter(content.charAt(w))) {
                    break;
                }
            }
            if (pos - w < 2) {
                currentCompletion = null;
                suggestionLabel.setText("");
                return;
            }

            String prefix = content.substring(w + 1).toLowerCase();
            int n = Collections.binarySearch(dictionary, prefix);

            if (n < 0 && -n <= dictionary.size()) {
                String match = dictionary.get(-n - 1);
                if (match.startsWith(prefix)) {
                    currentCompletion = match; // Store the full word match
                    suggestionLabel.setText(match + " = TAB");
                } else {
                    currentCompletion = null;
                    suggestionLabel.setText("");
                }
            } else {
                currentCompletion = null;
                suggestionLabel.setText("");
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        currentCompletion = null;
        suggestionLabel.setText("");
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    private void loadResources() {
        try {
            boolean dictLoaded = false;
            boolean highlightLoaded = false;

            File f = new File("dictionary.txt");
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                while ((line = br.readLine()) != null)
                    dictionary.add(line.trim().toLowerCase());
                br.close();
                dictLoaded = true;
            }
            File f2 = new File("prgmHighlights.txt");
            if (f2.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f2));
                String line;
                while ((line = br.readLine()) != null)
                    highlights.add(line.trim());
                br.close();
                highlightLoaded = true;
            }

             Collections.sort(dictionary);

            if (!dictLoaded || !highlightLoaded) {

            }

        } catch (Exception e) {
            statusLabel.setText("Error loading resources: " + e.getMessage());
        }
    }

    private void processInput() {
        String input = commandBar.getText().trim();
        commandBar.setText("");

        if (insertMode) {
            if (input.equals(".")) {
                insertMode = false;
                statusLabel.setText("-- COMMAND MODE --");
            } else {
                buffer.add(input);
                refreshMainArea();
            }
        } else {
            // Command Mode - Enforce Colon
            if (input.isEmpty())
                return;

            if (!input.startsWith(":")) {
                statusLabel.setText("Commands must start with ':' (e.g., :q)");
                return;
            }

            String cmdStr = input.substring(1); // Remove colon
            if (cmdStr.isEmpty())
                return;

            char cmd = cmdStr.charAt(0);
            String arg = (cmdStr.length() > 1) ? cmdStr.substring(1).trim() : "";

            switch (cmd) {
                case 'i':
                    insertMode = true;
                    statusLabel.setText("-- INSERT MODE (Type '.' to exit) --");
                    if (!arg.isEmpty()) {
                        buffer.add(arg);
                        refreshMainArea();
                    } else {
                     }
                    break;
                case 'c':
                    buffer.clear();
                    refreshMainArea();
                    statusLabel.setText("Buffer Cleared");
                    break;
                case 'w': // Write
                    saveFile(arg);
                    break;
                case 'u': // Uppercase
                    for (int i = 0; i < buffer.size(); i++) {
                        buffer.set(i, buffer.get(i).toUpperCase());
                    }
                    refreshMainArea();
                    statusLabel.setText("Converted to Uppercase");
                    break;
                case 'l': // Lowercase
                    for (int i = 0; i < buffer.size(); i++) {
                        buffer.set(i, buffer.get(i).toLowerCase());
                    }
                    refreshMainArea();
                    statusLabel.setText("Converted to Lowercase");
                    break;
                case 's': // Sort
                    java.util.Collections.sort(buffer);
                    refreshMainArea();
                    statusLabel.setText("Buffer Sorted");
                    break;
                case 'd': // Delete
                    try {
                        if (arg.isEmpty()) {
                            if (!buffer.isEmpty()) {
                                buffer.remove(buffer.size() - 1);
                                statusLabel.setText("Deleted last line");
                            }
                        } else {
                            int lineNum = Integer.parseInt(arg) - 1;
                            if (lineNum >= 0 && lineNum < buffer.size()) {
                                buffer.remove(lineNum);
                                statusLabel.setText("Deleted line " + (lineNum + 1));
                            } else {
                                statusLabel.setText("Invalid line number");
                            }
                        }
                        refreshMainArea();
                    } catch (NumberFormatException e) {
                        statusLabel.setText("Invalid line number");
                    }
                    break;
                case 'q':
                    System.exit(0);
                    break;
                default:
                    statusLabel.setText("Unknown Command: :" + cmd);
                    break;
            }
        }
    }

    private void refreshMainArea() {
        mainArea.setText(""); // Clear
        javax.swing.text.StyledDocument doc = mainArea.getStyledDocument();

        // Styles
        javax.swing.text.SimpleAttributeSet normal = new javax.swing.text.SimpleAttributeSet();
        javax.swing.text.StyleConstants.setForeground(normal, Color.LIGHT_GRAY);
        javax.swing.text.StyleConstants.setFontFamily(normal, "Consolas");
        javax.swing.text.StyleConstants.setFontSize(normal, 14);

        javax.swing.text.SimpleAttributeSet purple = new javax.swing.text.SimpleAttributeSet();
        javax.swing.text.StyleConstants.setForeground(purple, new Color(180, 80, 255)); // Purple-ish
        javax.swing.text.StyleConstants.setBold(purple, true);
        javax.swing.text.StyleConstants.setFontFamily(purple, "Consolas");
        javax.swing.text.StyleConstants.setFontSize(purple, 14);

        try {
            // Pre-compile regex if highlights exist
            java.util.regex.Pattern pattern = null;
            if (!highlights.isEmpty()) {
                StringBuilder patternSb = new StringBuilder("\\b(");
                for (int k = 0; k < highlights.size(); k++) {
                    patternSb.append(java.util.regex.Pattern.quote(highlights.get(k)));
                    if (k < highlights.size() - 1)
                        patternSb.append("|");
                }
                patternSb.append(")\\b");
                // CASE_INSENSITIVE
                pattern = java.util.regex.Pattern.compile(patternSb.toString(),
                        java.util.regex.Pattern.CASE_INSENSITIVE);
            }

            for (int i = 0; i < buffer.size(); i++) {
                String line = buffer.get(i);
                doc.insertString(doc.getLength(), (i + 1) + " | ", normal);

                if (pattern == null) {
                    doc.insertString(doc.getLength(), line, normal);
                } else {
                    java.util.regex.Matcher m = pattern.matcher(line);
                    int lastIdx = 0;
                    while (m.find()) {
                        // Append text before match
                        doc.insertString(doc.getLength(), line.substring(lastIdx, m.start()), normal);
                        // Append match in purple
                        doc.insertString(doc.getLength(), m.group(), purple);
                        lastIdx = m.end();
                    }
                    // Append remaining
                    doc.insertString(doc.getLength(), line.substring(lastIdx), normal);
                }

                doc.insertString(doc.getLength(), "\n", normal);
            }
        } catch (javax.swing.text.BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void saveFile(String filename) {
        if (filename.isEmpty())
            filename = "DOSNotepad.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (String s : buffer) {
                pw.println(s);
            }
            statusLabel.setText("Saved " + buffer.size() + " lines to " + filename);
        } catch (IOException e) {
            statusLabel.setText("Error saving: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(NotepadCommander::new);
        }
    }

    public static void runConsole(String[] args) {

        System.out.println("::: WELCOME TO DOS NOTEPAD (CLI) :::");
        // ...
    }
}
