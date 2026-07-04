// Nicholas Buchanan
// 2010

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlanetaryWeightConverter extends JFrame {

    private JTextField weightField;
    private JLabel resultLabel;
    private JRadioButton rVoltar, rKrypton, rFertos, rServontos;

    public PlanetaryWeightConverter() {
        initGUI();
    }

    private void initGUI() {
        setTitle("PlanetaryWeightConverter - Nick Buchanan");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sci-Fi Theme (Dark Blue/Starfield)
        getContentPane().setBackground(new Color(10, 10, 40)); // Deep Space Blue

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel l1 = new JLabel("Enter Earth Weight:");
        l1.setFont(new Font("Orbitron", Font.BOLD, 16)); // Fallback to Sans if Orbitron missing
        l1.setForeground(Color.CYAN);
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(l1);

        weightField = new JTextField(10);
        weightField.setMaximumSize(new Dimension(150, 30));
        weightField.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(Box.createVerticalStrut(10));
        center.add(weightField);

        ButtonGroup bg = new ButtonGroup();
        JPanel radioP = new JPanel(new GridLayout(4, 1, 5, 5));
        radioP.setOpaque(false);
        radioP.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.CYAN), "Select Destination"));
        ((javax.swing.border.TitledBorder) radioP.getBorder()).setTitleColor(Color.CYAN);

        rVoltar = createRadio("Voltar (x0.091)", bg);
        rKrypton = createRadio("Krypton (x0.72)", bg);
        rFertos = createRadio("Fertos (x0.865)", bg);
        rServontos = createRadio("Servontos (x4.612)", bg);

        radioP.add(rVoltar);
        radioP.add(rKrypton);
        radioP.add(rFertos);
        radioP.add(rServontos);
        radioP.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(Box.createVerticalStrut(20));
        center.add(radioP);

        JButton btn = new JButton("CONVERT");
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.CYAN);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(e -> convert());
        center.add(Box.createVerticalStrut(20));
        center.add(btn);

        add(center, BorderLayout.CENTER);

        resultLabel = new JLabel("Weight: ???", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        resultLabel.setForeground(Color.YELLOW);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(resultLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JRadioButton createRadio(String text, ButtonGroup bg) {
        JRadioButton r = new JRadioButton(text);
        r.setForeground(Color.WHITE);
        r.setOpaque(false);
        bg.add(r);
        return r;
    }

    private void convert() {
        try {
            int weight = Integer.parseInt(weightField.getText());
            double factor = 0;
            String planet = "";

            if (rVoltar.isSelected()) {
                factor = 0.091;
                planet = "Voltar";
            } else if (rKrypton.isSelected()) {
                factor = 0.72;
                planet = "Krypton";
            } else if (rFertos.isSelected()) {
                factor = 0.865;
                planet = "Fertos";
            } else if (rServontos.isSelected()) {
                factor = 4.612;
                planet = "Servontos";
            } else {
                resultLabel.setText("Select a Planet!");
                return;
            }

            resultLabel.setText("On " + planet + ": " + (weight * factor));

        } catch (Exception e) {
            resultLabel.setText("Invalid Weight");
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new PlanetaryWeightConverter());
        }
    }

    public static void runConsole(String[] args) {
        Scanner kbReader = new Scanner(System.in);

        // Conversion Factors
        double voltarFactor = 0.091;
        double kryptonFactor = 0.72;
        double fertosFactor = 0.865;
        double servontosFactor = 4.612;

        System.out.println("Weight Conversion Throughout the Universe!");
        System.out.print("Your weight? ");

        if (!kbReader.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer weight.");
            kbReader.close();
            return;
        }

        int weight = kbReader.nextInt();

        System.out.println("\t 1) Voltar ");
        System.out.println("\t 2) Krypton");
        System.out.println("\t 3) Fertos");
        System.out.println("\t 4) Servontos");
        System.out.println("\t 5) Exit");
        System.out.print("Selection: ");

        int choice = kbReader.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Your weight on Voltar is: " + (weight * voltarFactor));
                break;
            case 2:
                System.out.println("Your weight on Krypton is: " + (weight * kryptonFactor));
                break;
            case 3:
                System.out.println("Your weight on Fertos is: " + (weight * fertosFactor));
                break;
            case 4:
                System.out.println("Your weight on Servontos is: " + (weight * servontosFactor));
                break;
            case 5:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("That is not a choice!");
        }

        kbReader.close();
    }
}
/*
 * This program calculates a user's weight on various fictional planets
 * (Voltar, Krypton, Fertos, Servontos) using specific conversion factors.
 */
