// Nicholas Buchanan
// August 2012

import java.awt.Toolkit;

public class SystemBeep {

    public static void main(String[] args) {
        System.out.println("Initiating System Beep...");

        // Method 1: AWT Toolkit
        Toolkit.getDefaultToolkit().beep();

        // Method 2: ASCII Bell Character (Console dependent)
        System.out.print("\007");
        System.out.flush();

        System.out.println("Beep command sent.");
    }
}

/*
 * 1. Uses 'Toolkit.getDefaultToolkit().beep()', which is the standard Java AWT
 * method
 * for alerting the user.
 * 2. Prints the ASCII Bell character ('\007'), which triggers a beep in many
 * terminal emulators.
 */