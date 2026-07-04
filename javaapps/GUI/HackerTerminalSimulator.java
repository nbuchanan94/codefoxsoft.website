
//Nicholas Larkin Buchanan
//2011-04-01
//pd 8
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class HackerTerminalSimulator extends Frame implements KeyListener {
   // States
   private static final int STATE_SETUP_USER = 0;
   private static final int STATE_SETUP_PASS = 1;
   private static final int STATE_LOGIN_USER = 2;
   private static final int STATE_LOGIN_PASS = 3;
   private static final int STATE_SHELL = 4;

   private int currentState;
   private StringBuilder inputBuffer = new StringBuilder();
   private ArrayList<String> terminalHistory = new ArrayList<>();

   // Config
   private String savedUser = "admin";
   private String savedPass = "password";
   private File configFile = new File("terminal_config.txt");
   private boolean isSetupMode = false;

   // Login temps
   private String tempUser = "";

   // Graphics
   private Image offScreenImage;
   private Graphics offScreenGraphics;

   public HackerTerminalSimulator() {
      // Full Screen Setup
      setBackground(Color.black);
      setForeground(Color.green);
      setUndecorated(true);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      setSize(screenSize);
      addKeyListener(this);

      // Font
      setFont(new Font("Courier New", Font.BOLD, 18)); // Monospaced is key for terminal feel

      // Logic Init
      loadConfig();

      if (isSetupMode) {
         terminalHistory.add("SYSTEM BOOT sequence initiated...");
         terminalHistory.add("ERROR: No configuration found.");
         terminalHistory.add("SETUP REQUIRED.");
         terminalHistory.add("Enter Desired Username:");
         currentState = STATE_SETUP_USER;
      } else {
         terminalHistory.add("SYSTEM BOOT sequence initiated...");
         terminalHistory.add("SECURE TERMINAL V1.0");
         terminalHistory.add("Enter Username:");
         currentState = STATE_LOGIN_USER;
      }

      setVisible(true);
   }

   private void loadConfig() {
      if (!configFile.exists()) {
         isSetupMode = true;
         return;
      }
      try {
         Scanner sc = new Scanner(configFile);
         if (sc.hasNextLine())
            savedUser = sc.nextLine();
         if (sc.hasNextLine())
            savedPass = sc.nextLine();
         sc.close();
         isSetupMode = false;
      } catch (Exception e) {
         isSetupMode = true; // Fallback
      }
   }

   private void saveConfig(String u, String p) {
      try {
         FileWriter fw = new FileWriter(configFile);
         fw.write(u + "\n");
         fw.write(p + "\n");
         fw.close();
         savedUser = u;
         savedPass = p;
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   // Double Buffering to prevent flicker
   public void update(Graphics g) {
      if (offScreenImage == null) {
         offScreenImage = createImage(getWidth(), getHeight());
         offScreenGraphics = offScreenImage.getGraphics();
      }
      offScreenGraphics.setColor(Color.black);
      offScreenGraphics.fillRect(0, 0, getWidth(), getHeight());
      offScreenGraphics.setColor(Color.green);
      paint(offScreenGraphics);
      g.drawImage(offScreenImage, 0, 0, this);
   }

   public void paint(Graphics g) {
      int y = 40;
      int lineHeight = 25;

      // Draw History
      // If history is too long, we simulate scrolling by only drawing last N lines
      int maxLines = (getHeight() - 100) / lineHeight;
      int startLine = Math.max(0, terminalHistory.size() - maxLines);

      for (int i = startLine; i < terminalHistory.size(); i++) {
         g.drawString(terminalHistory.get(i), 20, y);
         y += lineHeight;
      }

      // Draw Input Line (Except in Shell where input is part of history usually, but
      // let's separate it for clarity)
      String prompt = "> ";
      String displayInput = inputBuffer.toString();

      if (currentState == STATE_LOGIN_PASS || currentState == STATE_SETUP_PASS) {
         // Mask password
         displayInput = "";
         for (int i = 0; i < inputBuffer.length(); i++)
            displayInput += "*";
      }

      g.drawString(prompt + displayInput + "_", 20, y); // Blinking cursor cursor implied
   }

   public void keyPressed(KeyEvent e) {
      int code = e.getKeyCode();

      if (code == KeyEvent.VK_ESCAPE) {
         System.exit(0);
      } else if (code == KeyEvent.VK_ENTER) {
         processInput(inputBuffer.toString());
         inputBuffer.setLength(0); // Clear buffer
      } else if (code == KeyEvent.VK_BACK_SPACE) {
         if (inputBuffer.length() > 0) {
            inputBuffer.deleteCharAt(inputBuffer.length() - 1);
         }
      } else if (code != KeyEvent.VK_SHIFT && code != KeyEvent.VK_CONTROL && code != KeyEvent.VK_ALT) {
         char c = e.getKeyChar();
         if (Character.isDefined(c)) {
            inputBuffer.append(c);
         }
      }
      repaint();
   }

   private void processInput(String input) {
      // Echo input to history (if not password)
      if (currentState != STATE_LOGIN_PASS && currentState != STATE_SETUP_PASS) {
         terminalHistory.add("> " + input);
      } else {
         terminalHistory.add("> *****");
      }

      switch (currentState) {
         case STATE_SETUP_USER:
            tempUser = input;
            terminalHistory.add("Enter Desired Password:");
            currentState = STATE_SETUP_PASS;
            break;
         case STATE_SETUP_PASS:
            saveConfig(tempUser, input);
            terminalHistory.add("CONFIGURATION SAVED.");
            terminalHistory.add("Booting Shell...");
            terminalHistory.add("Type 'help' for commands.");
            currentState = STATE_SHELL;
            break;
         case STATE_LOGIN_USER:
            tempUser = input;
            terminalHistory.add("Enter Password:");
            currentState = STATE_LOGIN_PASS;
            break;
         case STATE_LOGIN_PASS:
            if ((tempUser.equals(savedUser) && input.equals(savedPass)) ||
                  (tempUser.equals("admin") && input.equals("password"))) {
               terminalHistory.add("ACCESS GRANTED.");
               terminalHistory.add("Welcome, " + tempUser);
               terminalHistory.add("Type 'help' for commands.");
               currentState = STATE_SHELL;
            } else {
               terminalHistory.add("ACCESS DENIED.");
               terminalHistory.add("Enter Username:");
               currentState = STATE_LOGIN_USER;
            }
            break;
         case STATE_SHELL:
            processCommand(input);
            break;
      }
   }

   private void processCommand(String cmd) {
      String[] parts = cmd.split(" ");
      String base = parts[0].toLowerCase();

      if (base.equals("help")) {
         terminalHistory.add("AVAILABLE COMMANDS:");
         terminalHistory.add("  time    - Display system time");
         terminalHistory.add("  date    - Display system date");
         terminalHistory.add("  clear   - Clear terminal");
         terminalHistory.add("  hack    - Initiate brute-force attack");
         terminalHistory.add("  trace   - Trace IP address");
         terminalHistory.add("  matrix  - Wake up, Neo");
         terminalHistory.add("  nuke    - Launch protocol");
         terminalHistory.add("  whoami  - Current user");
         terminalHistory.add("  exit    - Shutdown");
      } else if (base.equals("clear")) {
         terminalHistory.clear();
      } else if (base.equals("exit")) {
         System.exit(0);
      } else if (base.equals("time")) {
         terminalHistory.add("SYSTEM TIME: " + new Date().toString().substring(11, 19));
      } else if (base.equals("date")) {
         terminalHistory.add("SYSTEM DATE: " + new Date().toString());
      } else if (base.equals("whoami")) {
         terminalHistory.add("User: " + tempUser + " (UID 0)");
      } else if (base.equals("hack")) {
         String target = (parts.length > 1) ? parts[1] : "NSA Mainframe";
         terminalHistory.add("Initializing attack on " + target + "...");
         terminalHistory.add("[*] Bypassing firewall...");
         terminalHistory.add("[*] Injecting payload...");
         terminalHistory.add("[*] Root access obtained.");
         terminalHistory.add("SUCCESS. Data downloaded to /dev/null.");
      } else if (base.equals("trace")) {
         String ip = (parts.length > 1) ? parts[1] : "192.168.0.1";
         terminalHistory.add("Tracing " + ip + "...");
         terminalHistory.add("HOP 1: 10.0.0.1 (Local)");
         terminalHistory.add("HOP 2: 45.33.22.11 (ISP)");
         terminalHistory.add("HOP 3: [REDACTED] (Langley, VA)");
         terminalHistory.add("Target located.");
      } else if (base.equals("matrix")) {
         terminalHistory.add("01100101 01101110 01110100 01100101 01110010 00100000 01110100 01101000 01100101");
         terminalHistory.add("01100001 01101001 01101110 01100110 01110010 01100001 01101101 01100101 00100000");
         terminalHistory.add("01101101 01100001 01110100 01110010 01101001 01111000 00101110 00101110 00101110");
         terminalHistory.add("Wake up, Neo...");
         terminalHistory.add("The Matrix has you...");
         terminalHistory.add("Follow the white rabbit.");
      } else if (base.equals("nuke")) {
         String target = (parts.length > 1) ? parts[1] : "all targets";
         terminalHistory.add("AUTHENTICATION REQUIRED: RED-LEVEL");
         terminalHistory.add("[*] Biometric scan... ACCEPTED.");
         terminalHistory.add("Targeting " + target + "...");
         terminalHistory.add("Arming warheads... 3... 2... 1...");
         terminalHistory.add("LAUNCH DETECTED.");
         terminalHistory.add("Impact in 14 minutes.");
      } else {
         terminalHistory.add("Unknown command: " + base);
      }
   }

   public void keyReleased(KeyEvent e) {
   }

   public void keyTyped(KeyEvent e) {
   }

   public static void main(String[] args) {
      try {
         // Check if user specified backdoor args? No, stick to internal logic.
      } catch (Exception e) {
      }
      new HackerTerminalSimulator();
   }
}
// Usage: Full screen terminal.
// Setup: Prompts for user/pass on first run.
// Login: Use saved creds or 'admin'/'password'.
// Commands: help, hack, trace, time, date.
// Exit: ESC or 'exit'.