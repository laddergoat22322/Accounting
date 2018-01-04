package Add_Remove_Components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import Import_Export.ImportTransactions;
import Import_Export.ImportTransactionsGUI;
import Transactions.TransactionManager;
import mainGUI.GUI;

public class AddNewAccountGUI extends GUI{
	
	private JFrame frame;
	private int bankID;
	private int accountID;
	
	public AddNewAccountGUI() {
		initialise();
	}
	
	private void initialise() {
		frame = new JFrame();
		frame.setSize(800,350);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("New Account");
		
		JPanel panel = createPanelWithComponents();
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	private JPanel createPanelWithComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 3);
		String[] accounts = tm.getAccounts(0);
		
		JLabel headerLabel = createLabel("Add New Bank Account", largeFont);
		panel.add(headerLabel, c);
		
		JLabel bankLabel = createLabel("Bank", mediumFont);
		c.gridwidth = 1;
		c.gridy++;
		c.ipadx = 20;
		c.anchor = GridBagConstraints.EAST;
		panel.add(bankLabel, c);
		
		String[] banks = tm.getAllBanks();
		JComboBox<String> cb1 = new JComboBox<String>(banks);
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
		
		JTextField tf = new JTextField();
	    tf.setPreferredSize(preferredSize);
		tf.setFont(mediumFont);
		c.gridx++;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(tf, c);
		
//		JButton findFileButton = new JButton("Select...");
//		findFileButton.setPreferredSize(new Dimension(80, 30));
//		c.gridx++;
//		c.anchor = GridBagConstraints.WEST;
//		c.fill = GridBagConstraints.NONE;
//		panel.add(findFileButton, c);
//		findFileButton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//				fc.setPreferredSize(new Dimension(700, 500));
//				int returnValue = fc.showOpenDialog(null);
//
//				if (returnValue == JFileChooser.APPROVE_OPTION) {
//					File selectedFile = fc.getSelectedFile();
//					tf.setText(selectedFile.getAbsolutePath());
//				}
//			}
//			
//		});
		
		JButton enterButton = new JButton("Done");
		enterButton.setPreferredSize(new Dimension(120, 45));
		c.gridy++;
		c.gridx = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 3;
		panel.add(enterButton, c);
		enterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = tf.getText();
				if (text.isEmpty()) {
					JOptionPane.showMessageDialog(frame,
						    "Please Enter An Account Name",
						    "Incomplete",
						    JOptionPane.ERROR_MESSAGE);
				}
				else {
					bankID = cb1.getSelectedIndex();
					tm.addAccount(bankID, text);
					frame.dispose();
				}
				
			}
			
		});
		return panel;
	}
}
