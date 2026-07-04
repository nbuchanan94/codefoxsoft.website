// Nicholas Buchanan
// 2011

import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;

public class AudioPlayerExample extends JFrame {

    private JLabel statusLabel;
    private JProgressBar visualizer;

    public AudioPlayerExample() {
        initGUI();
    }

    private void initGUI() {
        setTitle("AudioPlayerExample - Nick Buchanan");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Music Player Theme (Vinyl)
        getContentPane().setBackground(Color.DARK_GRAY);

        JLabel title = new JLabel("NOW PLAYING: Blip.wav", SwingConstants.CENTER);
        title.setForeground(Color.CYAN);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        JButton playBtn = new JButton("PLAY!!!");
        playBtn.setFont(new Font("SansSerif", Font.PLAIN, 40));
        playBtn.setBackground(Color.BLACK);
        playBtn.setForeground(Color.CYAN);
        playBtn.addActionListener(e -> play());
        center.add(playBtn);
        add(center, BorderLayout.CENTER);

        visualizer = new JProgressBar();
        visualizer.setIndeterminate(false);
        visualizer.setForeground(Color.CYAN);
        visualizer.setBackground(Color.BLACK);
        add(visualizer, BorderLayout.SOUTH);

        statusLabel = new JLabel("Stopped", SwingConstants.CENTER);
        statusLabel.setForeground(Color.LIGHT_GRAY);
        // add(statusLabel, BorderLayout.SOUTH); // Replaced by progress bar

        setVisible(true);
    }

    private void play() {
        new Thread(() -> {
            try {
                // Load the audio file (relative path)
                File soundFile = new File("Blip.wav");

                if (!soundFile.exists()) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "File 'Blip.wav' not found!"));
                    return;
                }

                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);

                SwingUtilities.invokeLater(() -> {
                    visualizer.setIndeterminate(true);
                    statusLabel.setText("Playing...");
                });

                clip.start();

                long durationMs = clip.getMicrosecondLength() / 1000;
                Thread.sleep(durationMs + 100);

                SwingUtilities.invokeLater(() -> visualizer.setIndeterminate(false));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new AudioPlayerExample());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("Attempting to play: Blip.wav");

        try {
            // Load the audio file (relative path)
            File soundFile = new File("Blip.wav");

            if (!soundFile.exists()) {
                System.out.println("Error: File 'Blip.wav' not found in current directory.");
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            // Get a sound clip resource
            Clip clip = AudioSystem.getClip();

            // Open audio clip and load samples from the audio input stream
            clip.open(audioIn);

            // Play
            clip.start();
            System.out.println("Playing...");

            // Wait for the sound to finish
            // (Clip runs in a separate daemon thread, so main thread must wait)
            long durationMs = clip.getMicrosecondLength() / 1000;
            Thread.sleep(durationMs + 100);

            System.out.println("Done.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/*
 * PROGRAM DESCRIPTION:
 *
 * This program demonstrates how to play a .wav audio file using the Java Sound
 * API.
 */
