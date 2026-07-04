
//Nicholas Larkin Buchanan
//2012-04-01
//pd 8
import java.awt.*;
import java.awt.event.*;

public class BSOD extends Frame implements KeyListener {
	private Thread killThread;

	public BSOD() {
		// Full Screen Setup
		setBackground(Color.blue);
		setForeground(Color.white);
		setUndecorated(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize);

		// Font
		setFont(new Font("Lucida Console", Font.PLAIN, 18));

		// Input
		addKeyListener(this);

		// Timer Logic
		killThread = new Thread(new Runnable() {
			public void run() {
				try {
					// Wait 2 minutes (120,000 ms)
					Thread.sleep(120000);

					// Execute Shutdown
					Runtime runtime = Runtime.getRuntime();
					// -s: shutdown, -t 0: immediately, -c: comment
					Process proc = runtime.exec("shutdown -s -t 0 -c \"This is why you don't use Windows\"");
					System.exit(0);
				} catch (InterruptedException e) {
					// Thread interrupted (User exited), do nothing
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		killThread.start();
		setVisible(true);
	}

	public void paint(Graphics g) {
		// Smooth text
		if (g instanceof Graphics2D) {
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}

		int y = 50;
		int x = 50;
		int spacing = 30;

		g.drawString("A problem has been detected and Windows has been shut down to prevent damage", x, y);
		y += spacing;
		g.drawString("to your computer.", x, y);

		y += spacing * 2;
		g.drawString("The problem seems to be caused by the following file: SPACETIME.SYS", x, y);

		y += spacing * 2;
		g.drawString("PAGE_FAULT_IN_NONPAGED_AREA", x, y);

		y += spacing * 2;
		g.drawString("If this is the first time you've seen this Stop error screen,", x, y);
		y += spacing;
		g.drawString("restart your computer. If this screen appears again, follow", x, y);
		y += spacing;
		g.drawString("these steps:", x, y);

		y += spacing * 2;
		g.drawString("Check to make sure any new hardware or software is properly installed.", x, y);
		y += spacing;
		g.drawString("If this is a new installation, ask your hardware or software manufacturer", x, y);
		y += spacing;
		g.drawString("for any Windows updates you might need.", x, y);

		y += spacing * 2;
		g.drawString("Technical Information:", x, y);
		y += spacing;
		g.drawString("*** STOP: 0x00000050 (0xFD3094C2, 0x00000001, 0xFBFE7617, 0x00000000)", x, y);
		y += spacing;
		g.drawString("*** SPACETIME.SYS - Address FBFE7617 base at FBFE5000, DateStamp 3d6dd67c", x, y);

		// Hide hint about ESCAPE key to keep it authentic, or add it per user request?
		// User didn't ask to hide it, but "FAKE BSOD" implies some hint.
		// I will keep it hidden to be "part of the fun" as requested,
		// relying on the user knowing the 2 min rule.
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// Abort
			if (killThread != null)
				killThread.interrupt();
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public static void main(String[] args) {
		new BSOD();
	}
}
// Usage: FAKE BSOD. Displays blue screen. Shuts down PC after exactly 2 minutes
// unless ESC is pressed.
