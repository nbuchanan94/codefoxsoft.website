// Nicholas Buchanan
// May 2010

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JFrameExample extends JFrame implements ActionListener {
	public Button showMyName;
	public TextField nameField;
	public Label nameShowed;
	JRadioButton option1 = new JRadioButton("Option 1");
	JRadioButton option2 = new JRadioButton("Option 2");
	JRadioButton option3 = new JRadioButton("Option 3");

	public JFrameExample() {
		super("Interns Rule");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(6, 1));

		showMyName = new Button("Click ME");
		nameField = new TextField(20);
		nameShowed = new Label();

		add(nameField);
		add(showMyName);
		add(nameShowed);

		// Add Radio Buttons (Note: They aren't grouped, just added for display as per
		// original)
		add(option1);
		add(option2);
		add(option3);

		showMyName.addActionListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String output = nameField.getText();
		nameShowed.setText("Hello " + output);
	}

	public static void main(String[] args) {
		new JFrameExample();
	}
}

/*
 * PROGRAM DESCRIPTION:
 * This was created to show case the Java Gui, for reuse for other projects.
 * It was simply a fun little gismo it had no purpose other than showing off
 * my skills to create GUI from scratch.
 */