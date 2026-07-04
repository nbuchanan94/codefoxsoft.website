
//Nicholas Larkin Buchanan
//2013-03-01
//pd 8
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class NamePrefixer extends JFrame {

    private JTextField inputField;
    private JTextArea outArea;

    public NamePrefixer() {
        initGUI();
    }

    private void initGUI() {
        setTitle("NamePrefixer - Nick Buchanan");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Formal Theme (Navy/Silver)
        getContentPane().setBackground(new Color(0, 0, 128)); // Navy

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.LIGHT_GRAY);
        top.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        top.add(new JLabel("Enter Names (Space Separated):"), BorderLayout.NORTH);
        inputField = new JTextField("Amy Smith Fred Jones");
        top.add(inputField, BorderLayout.CENTER);
        JButton btn = new JButton("Format Names");
        btn.addActionListener(e -> process());
        top.add(btn, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        outArea = new JTextArea();
        outArea.setEditable(false);
        outArea.setFont(new Font("Serif", Font.ITALIC, 18));
        outArea.setBackground(new Color(240, 248, 255)); // AliceBlue
        add(new JScrollPane(outArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void process() {
        String names = inputField.getText();
        StringTokenizer st = new StringTokenizer(names, " ");
        StringBuilder sb = new StringBuilder();
        String stt;

        while (st.hasMoreTokens()) {
            stt = st.nextToken();
            String lastName = "";
            if (st.hasMoreTokens()) {
                lastName = st.nextToken();
            }

            if ((stt.compareToIgnoreCase("amy") == 0) || (stt.compareToIgnoreCase("buffy") == 0)
                    || (stt.compareToIgnoreCase("cathy") == 0)) {
                sb.append("Ms. " + stt + " " + lastName + "\n");
            } else if ((stt.compareToIgnoreCase("elroy") == 0) || (stt.compareToIgnoreCase("fred") == 0)
                    || (stt.compareToIgnoreCase("graham") == 0)) {
                sb.append("Mr. " + stt + " " + lastName + "\n");

            } else {
                sb.append(stt + " " + lastName + "\n");
            }
        }
        outArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new NamePrefixer());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("System: Application Start");
        Scanner kbreader = new Scanner(System.in);
        System.out.println("Enter names separated by spaces (e.g. 'Amy Smith Fred Jones'):");
        String names = kbreader.nextLine();
        System.out.println("DEBUG: Input received: " + names);

        StringTokenizer st = new StringTokenizer(names, " ");
        String stt;

        while (st.hasMoreTokens()) {
            stt = st.nextToken();
            String lastName = "";
            if (st.hasMoreTokens()) {
                lastName = st.nextToken();
            } else {
                System.out.println("DEBUG: Warning - No last name for " + stt);
            }

            if ((stt.compareToIgnoreCase("amy") == 0) || (stt.compareToIgnoreCase("buffy") == 0)
                    || (stt.compareToIgnoreCase("cathy") == 0)) {
                System.out.println("Ms. " + stt + " " + lastName);
            } else if ((stt.compareToIgnoreCase("elroy") == 0) || (stt.compareToIgnoreCase("fred") == 0)
                    || (stt.compareToIgnoreCase("graham") == 0)) {
                System.out.println("Mr. " + stt + " " + lastName);

            } else {
                System.out.println(stt + " " + lastName);
            }
        }
        System.out.println("System: Application End");
    }
}
// Usage: Run this file. Enter Full Names. It adds Ms./Mr. to specific First
// Names.
