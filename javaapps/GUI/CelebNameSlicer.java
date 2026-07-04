
// Nicholas Larkin Buchanan
// October 12, 2010
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CelebNameSlicer extends JFrame {

    private JPanel stage;

    public CelebNameSlicer() {
        initGUI();
    }

    private void initGUI() {
        setTitle("CelebNameSlicer - Nick Buchanan");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Hollywood Theme (Gold/Black)
        getContentPane().setBackground(Color.BLACK);

        JLabel title = new JLabel("HOLLYWOOD NAME SLICER", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(255, 215, 0)); // Gold
        add(title, BorderLayout.NORTH);

        stage = new JPanel(new GridLayout(3, 1, 10, 10));
        stage.setBackground(Color.BLACK);
        stage.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addCelebRow("Richard Williams");
        addCelebRow("John Wayne");
        addCelebRow("Gregory Peck");

        add(stage, BorderLayout.CENTER);

        JButton btn = new JButton("SLICE NAMES (Remove first 2, last 3)");
        btn.setBackground(new Color(255, 215, 0));
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.addActionListener(e -> sliceAll());
        add(btn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addCelebRow(String name) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setOpaque(false);
        p.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JLabel l = new JLabel(name);
        l.setFont(new Font("Arial", Font.PLAIN, 20));
        l.setForeground(Color.WHITE);
        p.add(l);

        JLabel arrow = new JLabel(" >>> ");
        arrow.setForeground(Color.YELLOW);
        p.add(arrow);

        JLabel res = new JLabel("???");
        res.setFont(new Font("Arial", Font.ITALIC, 20));
        res.setForeground(Color.GRAY);
        res.putClientProperty("orig", name); // Store for logic
        p.add(res);

        stage.add(p);
    }

    private void sliceAll() {
        for (Component c : stage.getComponents()) {
            if (c instanceof JPanel) {
                JPanel row = (JPanel) c;
                JLabel resLabel = (JLabel) row.getComponent(2); // 3rd item
                String orig = (String) resLabel.getClientProperty("orig");
                if (orig != null && orig.length() > 5) {
                    String cut = orig.substring(2, orig.length() - 3);
                    resLabel.setText(cut);
                    resLabel.setForeground(Color.GREEN);
                }
            }
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new CelebNameSlicer());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("Processing Celebrity Names (Removing first 2 and last 3 chars)...");

        String s1 = "Richard Williams";
        String s2 = "John Wayne";
        String s3 = "Gregory Peck";

        System.out.print(s1 + " >>> ");
        String smallpart = s1.substring(2, s1.length() - 3);
        System.out.println(smallpart);

        System.out.print(s2 + " >>> ");
        String smallpart2 = s2.substring(2, s2.length() - 3);
        System.out.println(smallpart2);

        System.out.print(s3 + " >>> ");
        String smallpart3 = s3.substring(2, s3.length() - 3);
        System.out.println(smallpart3);
    }
}
// Run to see substring operations on celebrity names.
