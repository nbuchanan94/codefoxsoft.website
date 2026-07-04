// Nicholas Larkin Buchanan
// 2011

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AnalogClock extends JFrame {

    private final ClockPanel clockPanel;

    public AnalogClock() {
        super("Analog Clock (24H)");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        clockPanel = new ClockPanel();
        add(clockPanel);

        // Update every second
        Timer timer = new Timer(1000, e -> clockPanel.repaint());
        timer.start();

        setVisible(true);
    }

    private class ClockPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Anti-aliasing for smooth lines
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dimensions
            int w = getWidth();
            int h = getHeight();
            int centerW = w / 2;
            int centerH = h / 2;
            int radius = Math.min(centerW, centerH) - 40;

            // Clear Background
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, w, h);

            // Draw Face
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(3));
            g2.drawOval(centerW - radius, centerH - radius, radius * 2, radius * 2);

            // Draw Numbers (12, 3, 6, 9)
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("12", centerW - 10, centerH - radius + 30);
            g2.drawString("6", centerW - 5, centerH + radius - 20);
            g2.drawString("3", centerW + radius - 30, centerH + 5);
            g2.drawString("9", centerW - radius + 15, centerH + 5);

            // Get Time
            LocalTime now = LocalTime.now();
            int second = now.getSecond();
            int minute = now.getMinute();
            int hour = now.getHour();

            // Angles (0 degrees is 3 o'clock, so subtract 90 degrees/pi/2)
            // Second: 6 degrees per second
            double secondAngle = Math.toRadians((second * 6) - 90);
            // Minute: 6 degrees per minute + adjustment for seconds
            double minuteAngle = Math.toRadians((minute * 6) - 90);
            // Hour: 30 degrees per hour + adjustment for minutes (use mod 12 for analog
            // position)
            double hourAngle = Math.toRadians(((hour % 12) * 30 + minute * 0.5) - 90);

            // Draw Seconds Hand (Red, Thin, Long)
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(1));
            drawHand(g2, centerW, centerH, secondAngle, radius - 10);

            // Draw Minute Hand (Black, Medium, Medium)
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(3));
            drawHand(g2, centerW, centerH, minuteAngle, radius - 30);

            // Draw Hour Hand (Black, Thick, Short)
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(6));
            drawHand(g2, centerW, centerH, hourAngle, radius - 60);

            // Draw Center Dot
            g2.fillOval(centerW - 5, centerH - 5, 10, 10);

            // Draw Digital Time (24H)
            g2.setColor(Color.BLUE);
            g2.setFont(new Font("Monospaced", Font.BOLD, 20));
            String timeText = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            FontMetrics fm = g2.getFontMetrics();
            int textW = fm.stringWidth(timeText);
            g2.drawString(timeText, centerW - (textW / 2), centerH + radius + 30);
        }

        private void drawHand(Graphics2D g2, int x, int y, double angle, int length) {
            int x2 = (int) (x + Math.cos(angle) * length);
            int y2 = (int) (y + Math.sin(angle) * length);
            g2.drawLine(x, y, x2, y2);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AnalogClock::new);
    }
}

/*
 * HOW TO USE PROGRAM:
 * 1. Run the program.
 * 2. View the current time on the analog face (hands) or digital display
 * (bottom).
 * 3. Supports 24-hour time format in the digital readout.
 */
