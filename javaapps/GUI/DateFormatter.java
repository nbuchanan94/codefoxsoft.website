
//Nicholas Larkin Buchanan
//2011-02-20
//pd 8
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class DateFormatter extends JFrame {

    private JTextField inputField;
    private JLabel resultLabel;

    public DateFormatter() {
        initGUI();
    }

    private void initGUI() {
        setTitle("DateFormatter - Nick Buchanan");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Calendar Theme
        JPanel top = new JPanel();
        top.setBackground(Color.RED);
        JLabel title = new JLabel("JULY 17", SwingConstants.CENTER); // Icon-like
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        top.add(title);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new FlowLayout());
        center.add(new JLabel("Date (MM DD YY):"));
        inputField = new JTextField(10);
        center.add(inputField);
        JButton btn = new JButton("Format");
        btn.addActionListener(e -> format());
        center.add(btn);
        add(center, BorderLayout.CENTER);

        resultLabel = new JLabel("Wait for input...", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.ITALIC, 20));
        add(resultLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void format() {
        String m = inputField.getText();
        StringTokenizer st = new StringTokenizer(m);

        if (st.countTokens() < 3) {
            resultLabel.setText("Need 3 parts (MM DD YY)");
            return;
        }

        String mm = st.nextToken();
        String dd = st.nextToken();
        String yy = st.nextToken();

        String monthName = "";

        switch (mm) {
            case "1":
                monthName = "JANUARY";
                break;
            case "2":
                monthName = "FEBRUARY";
                break;
            case "3":
                monthName = "MARCH";
                break;
            case "4":
                monthName = "APRIL";
                break;
            case "5":
                monthName = "MAY";
                break;
            case "6":
                monthName = "JUNE";
                break;
            case "7":
                monthName = "JULY";
                break;
            case "8":
                monthName = "AUGUST";
                break;
            case "9":
                monthName = "SEPTEMBER";
                break;
            case "10":
                monthName = "OCTOBER";
                break;
            case "11":
                monthName = "NOVEMBER";
                break;
            case "12":
                monthName = "DECEMBER";
                break;
            default:
                monthName = "INVALID";
        }

        resultLabel.setText(monthName + ", " + dd + ", 20" + yy);
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new DateFormatter());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("System: Application Start");
        Scanner kb = new Scanner(System.in);
        System.out.print("enter a date in mm dd yy (include spaces): ");

        String m = kb.nextLine();
        System.out.println("DEBUG: User Input: " + m);

        StringTokenizer st = new StringTokenizer(m);

        if (st.countTokens() < 3) {
            System.out.println("Error: Please enter 3 numbers separated by spaces.");
            return;
        }

        String mm = st.nextToken();
        String dd = st.nextToken();
        String yy = st.nextToken();

        System.out.println("DEBUG: Parsed Month: " + mm + ", Day: " + dd + ", Year: " + yy);

        String monthName = "";

        if (mm.equals("1")) {
            monthName = "JANUARY";
        } else if (mm.equals("2")) {
            monthName = "FEBRUARY";
        } else if (mm.equals("3")) {
            monthName = "MARCH";
        } else if (mm.equals("4")) {
            monthName = "APRIL";
        } else if (mm.equals("5")) {
            monthName = "MAY";
        } else if (mm.equals("6")) {
            monthName = "JUNE";
        } else if (mm.equals("7")) {
            monthName = "JULY";
        } else if (mm.equals("8")) {
            monthName = "AUGUST";
        } else if (mm.equals("9")) {
            monthName = "SEPTEMBER";
        } else if (mm.equals("10")) {
            monthName = "OCTOBER";
        } else if (mm.equals("11")) {
            monthName = "NOVEMBER";
        } else if (mm.equals("12")) {
            monthName = "DECEMBER";
        } else {
            System.out.println("Invalid Month Number");
            return;
        }

        System.out.println("System: Outputting formatted date");
        System.out.println(monthName + ", " + dd + ", 20" + yy);

        System.out.println("System: Application End");
    }
}
// Usage: Run this file and input a date (e.g., '1 25 10'). Returns formatted
// date string.
