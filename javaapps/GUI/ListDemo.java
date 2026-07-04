
/*
 * Nicholas Larkin Buchanan
 *  October 3, 2009
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ListDemo extends JFrame {
    private JTextField inputField;
    private DefaultListModel<String> listModel;
    private ArrayList<String> stringList;

    public ListDemo() {
        super("ListDemo - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout(10, 10));

        stringList = new ArrayList<>();
        listModel = new DefaultListModel<>();

        // Clipboard/Yellow Theme
        Color itemBg = new Color(255, 255, 224); // Light Yellow
        getContentPane().setBackground(new Color(240, 230, 140)); // Khaki

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setOpaque(false);
        inputPanel.add(new JLabel("Add Item:"), BorderLayout.WEST);

        inputField = new JTextField();
        inputPanel.add(inputField, BorderLayout.CENTER);

        JButton addButton = new JButton("Add to List");
        addButton.setBackground(Color.ORANGE);
        inputPanel.add(addButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.NORTH);

        JList<String> displayList = new JList<>(listModel);
        displayList.setBackground(itemBg);
        JScrollPane scrollPane = new JScrollPane(displayList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current List"));
        add(scrollPane, BorderLayout.CENTER);

        JButton resetButton = new JButton("Clear List");
        resetButton.addActionListener(e -> {
            stringList.clear();
            listModel.clear();
        });
        add(resetButton, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addItem());
        inputField.addActionListener(e -> addItem());

        setVisible(true);
    }

    private void addItem() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            stringList.add(text);
            listModel.addElement(text);
            inputField.setText("");
            inputField.requestFocus();
        }
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(ListDemo::new);
        }
    }

    public static void runConsole(String[] args) {
        // Original Logic
        ArrayList<String> list = new ArrayList<String>();
        Scanner kb = new Scanner(System.in);
        int count = 0;

        System.out.println("Enter up to 10 strings. Type 'exit' to stop.");
        while (count < 10) {
            System.out.print("Enter string " + (count + 1) + ": ");
            String s = kb.nextLine();
            if (s.equalsIgnoreCase("exit"))
                break;
            list.add(s);
            count++;
        }

        System.out.println("\n--- Your List ---");
        for (String s : list) {
            System.out.println(s);
        }
    }
}
