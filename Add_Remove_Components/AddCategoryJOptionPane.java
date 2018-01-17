package Add_Remove_Components;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Transactions.TransactionManager;
import mainGUI.ModifiableJOptionPane;

public class AddCategoryJOptionPane extends ModifiableJOptionPane{
	
	private JTextField tf;

	public AddCategoryJOptionPane() {
		super();
		displayGUI();
	}
	

	public void checkInput(int input) {
		String text = tf.getText();
		if(input == JOptionPane.CANCEL_OPTION || input == JOptionPane.CLOSED_OPTION) {
			done = true;
		    return ;
		}
		if (text.isEmpty()) {
			JOptionPane.showMessageDialog(null,
				    "Please Enter An Account Name",
				    "Incomplete",
				    JOptionPane.ERROR_MESSAGE);
		}
		else {
			TransactionManager.addCategory(text);
			TransactionManager.analyseData();
			done = true;
		}
	}

	public void displayGUI() {
    	while (!done) {
            int input = JOptionPane.showConfirmDialog(null,
                    getPanel(),
                    "New Category",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			checkInput(input);
    	}        
    }


	public JPanel getPanel() {
    	JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 3);
		
		JLabel headerLabel = createLabel("Add New Category", largeFont);
		panel.add(headerLabel, c);
		
		JLabel fileLabel = createLabel("Category Name", mediumFont);
		c.gridwidth = 1;
		c.gridy++;
		c.ipadx = 20;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		panel.add(fileLabel, c);
		
		tf = new JTextField();
	    tf.setPreferredSize(tfDim);
		tf.setFont(mediumFont);
		c.gridx++;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(tf, c);
		
		return panel;
    }
}