package Add_Remove_Components;

import javax.swing.JPanel;
import javax.swing.JTextField;

import mainGUI.GUI;

public abstract class ModifiableJOptionPane extends GUI {
	protected boolean done;
	protected JTextField tf;
	
	public ModifiableJOptionPane() {
		super();
		done = false;
	}
	
	
	abstract void displayGUI();
	abstract void checkInput(int input);
	abstract JPanel getPanel();
}
