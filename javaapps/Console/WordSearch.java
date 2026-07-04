// Nicholas Buchanan
// October 2012

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// WordSearch class interface: solve word search puzzle
//
// CONSTRUCTION: with no initializer
// ******************PUBLIC OPERATIONS******************
// int solvePuzzle( )   --> Print all words found in the
//                          puzzle; return number of matches

public class WordSearch {
    /**
     * Constructor for WordSearch class.
     * Prompts for and reads puzzle and dictionary files.
     */
    public WordSearch() throws IOException {
        puzzleStream = openFile("Enter puzzle file");
        wordStream = openFile("Enter dictionary name");
        System.out.println("Reading files...");
        readPuzzle();
        readWords();
    }

    /**
     * Routine to solve the word search puzzle.
     * Performs checks in all eight directions.
     *
     * @return number of matches
     */
    public int solvePuzzle() {
        int matches = 0;

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < columns; c++)
                for (int rd = -1; rd <= 1; rd++)
                    for (int cd = -1; cd <= 1; cd++)
                        if (rd != 0 || cd != 0)
                            matches += solveDirection(r, c, rd, cd);

        return matches;
    }

    /**
     * Search the grid from a starting point and direction.
     *
     * @return number of matches
     */
    private int solveDirection(int baseRow, int baseCol, int rowDelta, int colDelta) {
        String charSequence = "";
        int numMatches = 0;
        int searchResult;

        charSequence += theBoard[baseRow][baseCol];

        for (int i = baseRow + rowDelta, j = baseCol + colDelta; i >= 0 && j >= 0 && i < rows
                && j < columns; i += rowDelta, j += colDelta) {

            charSequence += theBoard[i][j];
            searchResult = prefixSearch(theWords, charSequence);

            if (searchResult == theWords.length)
                break;
            if (!theWords[searchResult].startsWith(charSequence))
                break;

            if (theWords[searchResult].equals(charSequence)) {
                numMatches++;
                System.out.println("Found " + charSequence + " at " +
                        baseRow + " " + baseCol + " to " +
                        i + " " + j);
            }
        }

        return numMatches;
    }

    /**
     * Performs the binary search for word search.
     *
     * @param a the sorted array of strings.
     * @param x the string to search for.
     * @return last position examined;
     *         this position either matches x, or x is
     *         a prefix of the mismatch, or there is no
     *         word for which x is a prefix.
     */
    private static int prefixSearch(String[] a, String x) {
        int idx = Arrays.binarySearch(a, x);

        if (idx < 0)
            return -idx - 1;
        else
            return idx;
    }

    /**
     * Print a prompt and open a file.
     * Retry until open is successful.
     * Program exits if end of file is hit.
     */
    private BufferedReader openFile(String message) {
        String fileName = "";
        FileReader theFile;
        BufferedReader fileIn = null;

        do {
            System.out.println(message + ": ");

            try {
                fileName = in.readLine();
                if (fileName == null)
                    System.exit(0);
                fileName = fileName.trim(); // Trim whitespace
                theFile = new FileReader(fileName);
                fileIn = new BufferedReader(theFile);
            } catch (IOException e) {
                System.err.println("Cannot open " + fileName);
            }
        } while (fileIn == null);

        System.out.println("Opened " + fileName);
        return fileIn;
    }

    /**
     * Routine to read the grid.
     * Checks to ensure that the grid is rectangular.
     * Checks to make sure that capacity is not exceeded is omitted.
     */
    private void readPuzzle() throws IOException {
        String oneLine;
        List<String> puzzleLines = new ArrayList<>();

        if ((oneLine = puzzleStream.readLine()) == null)
            throw new IOException("No lines in puzzle file");

        columns = oneLine.length();
        puzzleLines.add(oneLine);

        while ((oneLine = puzzleStream.readLine()) != null) {
            if (oneLine.length() != columns)
                System.err.println("Puzzle is not rectangular; skipping row");
            else
                puzzleLines.add(oneLine);
        }

        // Close resource
        puzzleStream.close();

        rows = puzzleLines.size();
        theBoard = new char[rows][columns];
        Iterator<String> itr = puzzleLines.iterator();
        for (int r = 0; r < rows; r++) {
            String theLine = itr.next();
            theBoard[r] = theLine.toCharArray();
        }
    }

    /**
     * Routine to read the dictionary.
     * Error message is printed if dictionary is not sorted.
     */
    private void readWords() throws IOException {
        List<String> words = new ArrayList<>();

        String lastWord = null;
        String thisWord;

        while ((thisWord = wordStream.readLine()) != null) {
            if (lastWord != null && thisWord.compareTo(lastWord) < 0) {
                System.err.println("Dictionary is not sorted... skipping");
                continue;
            }
            words.add(thisWord);
            lastWord = thisWord;
        }

        // Close resource
        wordStream.close();

        // Convert list to String array
        theWords = words.toArray(new String[0]);
    }

    // Cheap main
    public static void main(String[] args) {
        WordSearch p = null;

        try {
            p = new WordSearch();
        } catch (IOException e) {
            System.out.println("IO Error: ");
            e.printStackTrace();
            return;
        }

        System.out.println("Solving...");
        p.solvePuzzle();
    }

    private int rows;
    private int columns;
    private char[][] theBoard;
    private String[] theWords; // Changed from Object[] to String[]
    private BufferedReader puzzleStream;
    private BufferedReader wordStream;
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
}

/*
 * 1. The program asks the user for a "puzzle file" (grid of characters) and a
 * "dictionary file" (list of words to find).
 * 2. It reads the specific puzzle grid into a 2D array.
 * 3. It reads the dictionary into a sorted array.
 * 4. It iterates through every cell in the grid.
 * 5. From each cell, it searches in all 8 directions (horizontal, vertical,
 * diagonal).
 * 6. It builds strings character by character and checks them against the
 * dictionary
 * using a binary prefix search.
 * 7. Matches are printed to the console with their start and end coordinates.
 */
