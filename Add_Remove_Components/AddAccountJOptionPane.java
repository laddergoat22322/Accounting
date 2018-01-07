package Add_Remove_Components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Transactions.TransactionManager;
import mainGUI.ModifiableJOptionPane;

public class AddAccountJOptionPane extends ModifiableJOptionPane {

	protected int bankID;
	private JComboBox<String> cb1;

	public AddAccountJOptionPane() {
		super();
		displayGUI();
	}
	
	@Override
	public void displayGUI() {
    	while (!done) {
            int input = JOptionPane.showConfirmDialog(null,
                    getPanel(),
                    "New Account",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			checkInput(input);
    	}
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
			bankID = cb1.getSelectedIndex();
			TransactionManager.addAccount(bankID, text);
			TransactionManager.analyseData();
			done = true;
		}
		
	}

	public JPanel getPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 3);
		
		JLabel headerLabel = createLabel("Add New Bank Account", largeFont);
		panel.add(headerLabel, c);
		
		JLabel bankLabel = createLabel("Bank", mediumFont);
		c.gridwidth = 1;
		c.gridy++;
		c.ipadx = 20;
		c.anchor = GridBagConstraints.EAST;
		panel.add(bankLabel, c);
		
		String[] banks = TransactionManager.getAllBanks();
		cb1 = new JComboBox<String>(banks);
		Dimension preferredSize = cb1.getPreferredSize();
	    preferredSize.height = 30;
	    preferredSize.width = 450;
	    cb1.setPreferredSize(preferredSize);
		cb1.setFont(mediumFont);
		c.gridx++;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(cb1, c);
		
		JLabel fileLabel = createLabel("Account Name", mediumFont);
		c.gridx = 0;
		c.gridy++;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		panel.add(fileLabel, c);
		
		tf = new JTextField();
	    tf.setPreferredSize(preferredSize);
		tf.setFont(mediumFont);
		c.gridx++;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(tf, c);
		
		return panel;
	}

}
