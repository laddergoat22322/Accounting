package mainGUI;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class ModifiableJOptionPane extends GUI {
	protected boolean done;
	
	public ModifiableJOptionPane() {
		super();
		done = false;
	}
	
	
	public abstract void checkInput(int input);
	public abstract void displayGUI();
	public abstract JPanel getPanel();
}
