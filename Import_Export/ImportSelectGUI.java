package Import_Export;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import Transactions.TransactionManager;
import mainGUI.GUI;

public class ImportSelectGUI extends GUI {
	
	private JFrame frame;
	private JPanel thePanel;
	private int bankID;
	private int fileType;
	private int accountID;
	
	public ImportSelectGUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(700,300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Import new transactions");
		
		thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());
		
		createPanelComponents();
		
		frame.add(thePanel);
		frame.setVisible(true);
	}
	
	private void createPanelComponents() {
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 3);
		String[] accounts = TransactionManager.getAccounts(0);
		JComboBox<String> cb2 = new JComboBox<String>(accounts);
		
		JLabel headerLabel = createLabel("Select Bank", largeFont);
		thePanel.add(headerLabel, c);
		
		JLabel bankLabel = createLabel("Bank", mediumFont);
		c.gridwidth = 1;
		c.gridy++;
		c.ipadx = 20;
		c.anchor = GridBagConstraints.EAST;
		thePanel.add(bankLabel, c);
		
		String[] banks = TransactionManager.getAllBanks();
		JComboBox<String> cb1 = new JComboBox<String>(banks);
		Dimension preferredSize = cb1.getPreferredSize();
	    preferredSize.height = 30;
	    preferredSize.width = 450;
	    cb1.setPreferredSize(preferredSize);
		cb1.setFont(mediumFont);
		c.ipadx = 0;
		c.gridx++;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		cb1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] newAccounts = TransactionManager.getAccounts(cb1.getSelectedIndex());
				cb2.removeAllItems();
				for (String account : newAccounts) {
					cb2.addItem(account);
				}
			}
			
		});
		thePanel.add(cb1, c);
		
		JLabel accountLabel = createLabel("Account", mediumFont);
		c.gridx = 0;
		c.gridy++;
		c.ipadx = 20;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		thePanel.add(accountLabel, c);
		
	    cb2.setPreferredSize(preferredSize);
		cb2.setFont(mediumFont);
		c.gridx++;
		c.ipadx = 0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		thePanel.add(cb2, c);
		
		JLabel fileLabel = createLabel("File Location", mediumFont);
		c.gridx = 0;
		c.gridy++;
		c.ipadx = 20;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		thePanel.add(fileLabel, c);
		
		JTextField tf = new JTextField();
	    tf.setPreferredSize(preferredSize);
		tf.setFont(mediumFont);
		c.gridx++;
		c.ipadx = 0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		tf.setText("C:/Users/matth/Downloads/CSVData.csv");
		thePanel.add(tf, c);
		
		JButton findFileButton = new JButton("Select...");
		findFileButton.setPreferredSize(new Dimension(80, 30));
		c.gridx++;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		thePanel.add(findFileButton, c);
		findFileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				fc.setPreferredSize(new Dimension(700, 500));
				int returnValue = fc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					tf.setText(selectedFile.getAbsolutePath());
				}
			}
			
		});
		
		JButton enterButton = new JButton("Done");
		enterButton.setPreferredSize(new Dimension(120, 45));
		c.gridy++;
		c.gridx = 1;
		c.ipadx = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 3;
		thePanel.add(enterButton, c);
		enterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String locText = tf.getText();
				if (locText.isEmpty()) {
					JOptionPane.showMessageDialog(frame,
						    "The file chosen cannot be imported",
						    "Incorrect File",
						    JOptionPane.ERROR_MESSAGE);
				}
				else {
					bankID = cb1.getSelectedIndex();
					accountID = cb2.getSelectedIndex();
					fileLoc = tf.getText();
					new ImportTransactions(bankID, accountID, fileLoc);
					frame.dispose();
					new ImportTransactionsGUI();
				}
				
			}
			
		});
	}
}
