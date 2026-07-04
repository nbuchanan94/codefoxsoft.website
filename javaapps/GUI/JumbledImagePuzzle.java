// Nicholas Larkin Buchanan
// 2012

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.Random;

// The main logic component
class JumbledImageComponent extends Component {
    private int numlocs = 2; // 2x2 grid
    private int numcells = numlocs * numlocs;
    private int[] cells;
    private BufferedImage bi;
    int w, h, cw, ch;

    public JumbledImageComponent(URL imageSrc) {
        try {
            bi = ImageIO.read(imageSrc);
            w = bi.getWidth(null);
            h = bi.getHeight(null);
        } catch (IOException e) {
            System.out.println("Image could not be read");
            // Create a placeholder image if read fails
            w = 200;
            h = 200;
            bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.getGraphics();
            g.setColor(Color.RED);
            g.fillRect(0, 0, w, h);
            g.setColor(Color.WHITE);
            g.drawString("Img Load Fail", 50, 100);
        }
        cw = w / numlocs;
        ch = h / numlocs;
        cells = new int[numcells];
        for (int i = 0; i < numcells; i++) {
            cells[i] = i;
        }
    }

    void jumble() {
        Random rand = new Random();
        int ri;
        for (int i = 0; i < numcells; i++) {
            while ((ri = rand.nextInt(numlocs)) == i)
                ;

            int tmp = cells[i];
            cells[i] = cells[ri];
            cells[ri] = tmp;
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w, h);
    }

    // Fixed 'paint' to 'paintComponent' for Swing
    public void paint(Graphics g) {
        int dx, dy;
        for (int x = 0; x < numlocs; x++) {
            int sx = x * cw;
            for (int y = 0; y < numlocs; y++) {
                int sy = y * ch;
                int cell = cells[x * numlocs + y];
                dx = (cell / numlocs) * cw;
                dy = (cell % numlocs) * ch;
                g.drawImage(bi,
                        dx, dy, dx + cw, dy + ch,
                        sx, sy, sx + cw, sy + ch,
                        null);
            }
        }
    }
}

// The Main Application Window
public class JumbledImagePuzzle extends JFrame {
    // Default image if none provided
    static String imageFileName = "https://images.dog.ceo/breeds/labrador/n02099712_7418.jpg";
    private URL imageSrc;
    private JumbledImageComponent jumbledImage;

    public JumbledImagePuzzle() {
        super("Jumbled Image Puzzle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            imageSrc = new URL(imageFileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        buildUI();
        pack();
        setVisible(true);
    }

    public void buildUI() {
        final JumbledImageComponent ji = new JumbledImageComponent(imageSrc);
        add(ji, BorderLayout.CENTER);

        JButton jumbleButton = new JButton("Jumble");
        jumbleButton.addActionListener(e -> {
            ji.jumble();
            ji.repaint();
        });

        add(jumbleButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JumbledImagePuzzle());
    }
}

/*
 * HOW TO USE PROGRAM:
 * 1. Run the application.
 * 2. It loads an image (default: a dog from the web) and displays it cut into
 * tiles.
 * 3. Click "Jumble" to scramble the tiles.
 */
