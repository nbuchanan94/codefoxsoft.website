
/*
 * Nicholas Buchanan
 * October 2012
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

public class WordSearch extends JFrame {
    private JTextArea puzzleArea; // User edits this to define puzzle
    private JTextArea wordsArea; // User edits this to define words
    private JTextArea resultArea; // Output

    public WordSearch() {
        super("WordSearch - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));

        // Theme: Grid/White
        getContentPane().setBackground(Color.WHITE);

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Puzzle Input
        JPanel pPanel = new JPanel(new BorderLayout());
        pPanel.add(new JLabel("Puzzle Grid (Rows):"), BorderLayout.NORTH);
        puzzleArea = new JTextArea(10, 20);
        puzzleArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        puzzleArea.setText("XDATAC\nYZTYPE\nABCDEF\nWORDXX"); // Demo
        pPanel.add(new JScrollPane(puzzleArea), BorderLayout.CENTER);

        // Words Input
        JPanel wPanel = new JPanel(new BorderLayout());
        wPanel.add(new JLabel("Dictionary (One per line, SORTED):"), BorderLayout.NORTH);
        wordsArea = new JTextArea(10, 10);
        wordsArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        wordsArea.setText("DATA\nTYPE\nWORD"); // Demo (Sorted)
        wPanel.add(new JScrollPane(wordsArea), BorderLayout.CENTER);

        inputPanel.add(pPanel);
        inputPanel.add(wPanel);

        add(inputPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        JButton solveButton = new JButton("SOLVE PUZZLE");
        solveButton.setBackground(Color.RED);
        solveButton.setForeground(Color.WHITE);
        solveButton.setFont(new Font("Arial", Font.BOLD, 18));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.BOLD, 14));

        centerPanel.add(solveButton, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        solveButton.addActionListener(e -> solve());

        setVisible(true);
    }

    private void solve() {
        resultArea.setText("");
        try {
            // Read Puzzle
            String[] puzzleLines = puzzleArea.getText().trim().split("\n");
            int rows = puzzleLines.length;
            if (rows == 0)
                return;
            int cols = puzzleLines[0].trim().length();
            if (cols == 0)
                return;

            char[][] board = new char[rows][cols];
            for (int i = 0; i < rows; i++) {
                String line = puzzleLines[i].trim();
                // Pad or trim if inconsistent? Original checks reqs.
                // We'll just take min length
                for (int j = 0; j < Math.min(line.length(), cols); j++) {
                    board[i][j] = line.charAt(j);
                }
            }

            // Read Words
            String[] wordsInput = wordsArea.getText().trim().split("\n");
            List<String> validWords = new ArrayList<>();
            for (String w : wordsInput) {
                if (!w.trim().isEmpty())
                    validWords.add(w.trim());
            }
            Collections.sort(validWords); // Ensure sorted for binary search
            String[] theWords = validWords.toArray(new String[0]);

            // Solve
            int matches = 0;
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < cols; c++)
                    for (int rd = -1; rd <= 1; rd++)
                        for (int cd = -1; cd <= 1; cd++)
                            if (rd != 0 || cd != 0)
                                matches += solveDirection(r, c, rd, cd, board, rows, cols, theWords);

            resultArea.append("\nTotal Matches: " + matches);

        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int solveDirection(int baseRow, int baseCol, int rowDelta, int colDelta, char[][] board, int rows,
            int columns, String[] theWords) {
        String charSequence = "";
        int numMatches = 0;
        int searchResult;

        charSequence += board[baseRow][baseCol];

        for (int i = baseRow + rowDelta, j = baseCol + colDelta; i >= 0 && j >= 0 && i < rows
                && j < columns; i += rowDelta, j += colDelta) {

            charSequence += board[i][j];
            searchResult = Arrays.binarySearch(theWords, charSequence);

            // Arrays.binarySearch returns exact match index >= 0
            // If not found, returns (-(insertion point) - 1).
            // We need prefix search logic from original?
            // "this position either matches x, or x is a prefix of the mismatch..."

            // Let's implement helper:
            int idx = Arrays.binarySearch(theWords, charSequence);
            int insertionPoint = (idx < 0) ? -idx - 1 : idx;

            // Check if prefix is valid for ANY word at insertion point
            boolean isPrefix = false;
            if (insertionPoint < theWords.length) {
                if (theWords[insertionPoint].startsWith(charSequence))
                    isPrefix = true;
            }

            if (!isPrefix)
                break; // Not a prefix of the next potential match

            if (idx >= 0) {
                // Found exact match
                resultArea.append(
                        "Found " + charSequence + " at " + baseRow + "," + baseCol + " to " + i + "," + j + "\n");
                numMatches++;
            }
        }
        return numMatches;
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            // runConsole(args); // Skipping CLI implementation for brevity in this specific
            // file as it needs files
        } else {
            SwingUtilities.invokeLater(WordSearch::new);
        }
    }
}
