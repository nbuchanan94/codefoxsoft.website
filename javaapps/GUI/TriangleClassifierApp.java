import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.net.*;

//Nicholas Larkin Buchanan
//EPIC PROGRAM >:D
//10/11/2010
//pd3
public class TriangleClassifierApp extends JFrame implements ActionListener
// jframe creates the window. and actionlistener gives the button1 a function.
{

	private final int winheight = 200;// declares the height of the window;
	private final int winlength = 450;// declares the length of the window;
	public Button button1;// declares a new instance of the Button class as button1
	public TextArea output;// declares a new instance the TextArea class as output.
	public TextField a;// declares a new instance of the Textfield class that makes the input box 'a'
	public TextField b;// declares a new instance of the TextField class that makes the input box 'b'
	public TextField c;// declares a new instance of the TextField class that makes the input box 'c'
	public Label aa;// declares a new instance of the Label class that makes an actual label for box
					// 'a'
	public Label bb;// declares a new instance of the Label class that makes an actual label for box
					// 'b'
	public Label cc;// declares a new instance of the Label class that makes an actual label for box
					// 'c'

	public TriangleClassifierApp()// creates the TriangleClassifierApp class :D
	{
		super("Triangle Classifier"); // this names the window title "Triangle Classifier"
		setSize(winlength, winheight);// sets the window height and length using the public varibles above.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// handles the "X" button to close the window
		setLayout(new FlowLayout());// creates a flow layout for the window. so that it just adds the button in
									// sequential order
		button1 = new Button("Prove Triangle");// creates the button1 and sets its text to "prove triangle"
		a = new TextField(3);// sets the textfield a to render only 3 degits
		b = new TextField(3);// sets the textfield a to render only 3 degits
		c = new TextField(3);// sets the textfield a to render only 3 degits
		output = new TextArea(":::::TRIANGLE LOG:::::");// sets the output box to the text in parentheses
		aa = new Label("'a' value");// creates a label that will say a value
		bb = new Label("'b' value");// creates a label that will say b value
		cc = new Label("'c' value");// creates a label that will say c value
		add(aa);// add the label first to identify the textbox after it to the JFrame window

		add(a);// adds the input box next to the JFrame window
		add(bb);// add the label first to identify the textbox after it to the JFrame window

		add(b);// adds the input box next to the JFrame window
		add(cc);// add the label first to identify the textbox after itto the JFrame window

		add(c);// adds the input box next to the JFrame window

		add(button1);// adds button 1 to the JFrame window
		add(output);// adds the output text area to the JFrame window.

		button1.addActionListener(this);// This is the code that "Listens" to the button. so if
										// button1 is clicked it will use the actionPerformed method
										// take action.

		setVisible(true);// this simply tells the computer to show the JFrame window :D
		System.out.println("System: GUI Initialized and Visible");

	}

	public void actionPerformed(ActionEvent e)// this is the action code. this is where all the calculations
												// take place. this is referenced by button1.addActionlistener(this);
	{
		System.out.println("DEBUG: Button clicked");
		int atext = Integer.parseInt(a.getText());// converts the text from the textbox a to an int and names it to
													// atext
		int btext = Integer.parseInt(b.getText());// converts the text from the textbox b to an int and names it to
													// btext
		int ctext = Integer.parseInt(c.getText());// converts the text from the textbox c to an int and names it to
													// ctext

		System.out.println("DEBUG: Inputs parsed: a=" + atext + ", b=" + btext + ", c=" + ctext);

		if ((atext + btext > ctext) && (btext + ctext > atext) && (ctext + atext > btext))// basicly calculates if the
																							// triangle is classified as
																							// a triangle
		{
			System.out.println("DEBUG: Valid Triangle detected");
			output.append("\nYou have a triangle........");// output.append simply does a system.out.println but
															// inside the TextArea

			if ((atext == btext) && (btext == ctext) && (atext == ctext))// tells you if it is equalateral
			{
				System.out.println("DEBUG: Classified as Equilateral");
				output.append(
						"It is an Equilateral Triangle:D\n\nThe triangle has identical length of sides. Each angle measures to 60 \ndegrees. It is a type of normal polygon.");

			} else if ((atext != btext) && (btext != ctext) && (atext != ctext))// tells you that it is scalene
			{
				System.out.println("DEBUG: Classified as Scalene");
				output.append(
						"It is a Scalene Triangle :D\n\nThe triangle sides are unequal is normally known as scalene triangle. \nThese types of triangles are having unequal sides");

			} else {
				System.out.println("DEBUG: Classified as Isosceles");
				output.append(
						"It is an Isosceles Triangle :D\n\nThe triangle has two conflicting sides are parallel to each other. \nIt also has two one and the same angles. The base angles opposite\nto the equal sides are equal");

			}

		} else {
			System.out.println("DEBUG: Not a triangle");
			output.append("\nNO TRIANGLE D:");// adds the no triangle text to the output box if it does not make a
												// triangle

		}

	}

	public static void main(String[] args) {
		System.out.println("System: Application Start");
		new TriangleClassifierApp();// opens the TriangleClassifierApp class :D
	}
}

// Usage: Run this file to start the GUI. Enter 3 integers to classify a
// triangle.
