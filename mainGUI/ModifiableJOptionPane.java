package mainGUI;

import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class ModifiableJOptionPane extends GUI {
	protected boolean done;
	protected JTextField tf;
	
	public ModifiableJOptionPane() {
		super();
		done = false;
	}
	
	
	public abstract void displayGUI();
	public abstract void checkInput(int input);
	public abstract JPanel getPanel();
}
