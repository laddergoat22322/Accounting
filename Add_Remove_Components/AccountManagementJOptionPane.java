package Add_Remove_Components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Transactions.TransactionManager;
import mainGUI.ModifiableJOptionPane;

public class AccountManagementJOptionPane extends ModifiableJOptionPane{
	
	private JTextField tf;
	private JPanel panel;
	private ArrayList<JTextField> accountTextFields;
	private ArrayList<JTextField> amountTextFields;

	public AccountManagementJOptionPane() {
		super();
		accountTextFields = new ArrayList<JTextField>();
		amountTextFields = new ArrayList<JTextField>();
		panel = new JPanel();
		displayGUI();
		
	}
	
	@Override
	public void checkInput(int input) {
		if(input == JOptionPane.CANCEL_OPTION || input == JOptionPane.CLOSED_OPTION) {
			done = true;
		    return ;
		}
		
		for(JTextField tf : accountTextFields) {
			String accountName = tf.getText();
			String[] tfName = tf.getName().split(",");
			
			int bankID = Integer.parseInt(tfName[0]);
			int accountID = Integer.parseInt(tfName[1]);
			TransactionManager.updateAccount(bankID, accountID, accountName);
		}
		
		for(JTextField tf : amountTextFields) {
			String amount = tf.getText();
			String[] tfName = tf.getName().split(",");
			
			int bankID = Integer.parseInt(tfName[0]);
			int accountID = Integer.parseInt(tfName[1]);
			TransactionManager.updateAccountTotal(bankID, accountID, amount);
		}
		done = true;
		
	}

	@Override
	public void displayGUI() {
    	while (!done) {
    		getPanel();
            int input = JOptionPane.showConfirmDialog(null,
                    panel,
                    "Bank Account Management",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			checkInput(input);
    	}
		
	}

	@Override
	public JPanel getPanel() {
		panel.removeAll();
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 2);
		
		JLabel headerLabel = createLabel("Banks", largeHeaderFont);
		panel.add(headerLabel, c);
		int numBanks = TransactionManager.getNumberOfBanks();
		for(int i = 0; i < numBanks; i++) {
			c.gridx = 0;
			c.gridy++;
			c.ipady = 15;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.SOUTHWEST;
			JLabel bankLabel = createLabel(TransactionManager.getBankName(i), largeFont);
			panel.add(bankLabel, c);
			c.ipady = 0;
			
			
			String[] accounts = TransactionManager.getAccounts(i);
			for(int j = 0; j < accounts.length; j++) {
				
				/** Account */
				JTextField accountField = createTextField(accounts[j], mediumFont);
				c.gridx = 0;
				c.gridy++;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.WEST;
				
				String tf_ID = i + "," + j;
				accountField.setName(tf_ID);
				accountTextFields.add(accountField);
				panel.add(accountTextFields.get(accountTextFields.size()-1), c);
				
				/** Account Balance */
				JTextField amountField = createTextField(TransactionManager.getAccountTotal(i, j), mediumFont);
				
				amountField.setName(tf_ID);
				amountTextFields.add(amountField);
				c.gridx++;
				panel.add(amountTextFields.get(amountTextFields.size()-1), c);
			}
		}
		
		/** Add New Account Button */
		JButton btn = new JButton();
		btn.setFont(smallFont);
		btn.setSize(new Dimension(100, 20));
		btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddAccountJOptionPane();
			}
			
		});
		
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		panel.add(btn, c);
		
		return panel;
	}

}
