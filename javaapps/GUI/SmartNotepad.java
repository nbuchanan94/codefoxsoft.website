// Nicholas Larkin Buchanan
// 2011

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;

public class SmartNotepad extends JFrame implements DocumentListener {

    private JTextArea textArea;
    private JLabel statusLabel;

    // Autocomplete Data
    private List<String> words;

    private enum Mode {
        INSERT, COMPLETION
    };

    private Mode mode = Mode.INSERT;
    private static final String COMMIT_ACTION = "commit";

    public SmartNotepad() {
        super("Smart Notepad");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Init Components
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        statusLabel = new JLabel("Type 'spark', 'special', or 'swing' to test autocomplete...");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Layout
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // Autocomplete Setup
        textArea.getDocument().addDocumentListener(this);

        InputMap im = textArea.getInputMap();
        ActionMap am = textArea.getActionMap();

        im.put(KeyStroke.getKeyStroke("ENTER"), "commitEnter");
        im.put(KeyStroke.getKeyStroke("TAB"), "commitTab");

        am.put("commitEnter", new CommitAction("\n"));
        am.put("commitTab", new CommitAction("\t"));

        // Initialize Dictionary
        words = new ArrayList<>();
        words.add("spark");
        words.add("special");
        words.add("spectacles");
        words.add("spectacular");
        words.add("swing");
        words.add("system");
        words.add("string");
        words.add("scanner");

        // Load additional words from smart.txt
        loadWordsFromFile("smart.txt");

        // Sort for binary search
        Collections.sort(words);

        setVisible(true);
    }

    private void loadWordsFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            // Try looking in current dir if not found (standard relative path behavior)
            // But also just silently fail if missing, as it's an enhancement
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String w = line.trim().toLowerCase();
                if (!w.isEmpty() && !words.contains(w)) {
                    words.add(w);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load " + filename + ": " + e.getMessage());
        }
    }

    // --- DocumentListener Implementation ---
    @Override
    public void insertUpdate(DocumentEvent ev) {
        if (ev.getLength() != 1)
            return;

        int pos = ev.getOffset();
        String content = null;
        try {
            content = textArea.getText(0, pos + 1);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        // Find start of current word
        int w;
        for (w = pos; w >= 0; w--) {
            if (!Character.isLetter(content.charAt(w))) {
                break;
            }
        }

        if (pos - w < 2)
            return; // Too short to autocomplete

        String prefix = content.substring(w + 1).toLowerCase();
        int n = Collections.binarySearch(words, prefix);

        if (n < 0 && -n <= words.size()) {
            String match = words.get(-n - 1);
            if (match.startsWith(prefix)) {
                // Determine completion string
                String completion = match.substring(pos - w);

                // Submit task to run later (avoid modifying document during listener
                // notification)
                SwingUtilities.invokeLater(new CompletionTask(completion, pos + 1));
            }
        } else {
            mode = Mode.INSERT;
        }
    }

    @Override
    public void removeUpdate(DocumentEvent ev) {
    }

    @Override
    public void changedUpdate(DocumentEvent ev) {
    }

    // --- Inner Classes ---

    private class CompletionTask implements Runnable {
        String completion;
        int position;

        CompletionTask(String completion, int position) {
            this.completion = completion;
            this.position = position;
        }

        public void run() {
            textArea.insert(completion, position);
            textArea.setCaretPosition(position + completion.length());
            textArea.moveCaretPosition(position); // Select the autocompleted part
            mode = Mode.COMPLETION;
        }
    }

    private class CommitAction extends AbstractAction {
        private String defaultStr;

        public CommitAction(String defaultStr) {
            this.defaultStr = defaultStr;
        }

        public void actionPerformed(ActionEvent ev) {
            if (mode == Mode.COMPLETION) {
                int pos = textArea.getSelectionEnd();
                textArea.insert(" ", pos); // Confirm completion with space
                textArea.setCaretPosition(pos + 1);
                mode = Mode.INSERT;
            } else {
                textArea.replaceSelection(defaultStr); // Normal typing
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new SmartNotepad();
        });
    }
}

/*
 * HOW TO USE PROGRAM:
 * 1. Run the program.
 * 2. Start typing words like "swing", "special", "spark".
 * 3. If a match is found, it effectively "suggests" the rest of the word
 * (highlighted).
 * 4. Press ENTER to accept the completion (adds a space).
 * 5. Or just keep typing to overwrite.
 */
