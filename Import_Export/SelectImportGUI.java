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
import mainGUI.MainGUI;

public class SelectImportGUI {
	
	private JFrame frame;
	private JPanel thePanel;
	private int bankID;
	private int fileType;
	private String fileLoc;
	private int accountID;
	private TransactionManager tm;
	
	public SelectImportGUI() {
		initialize();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SelectImportGUI();
			}
		});
	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(700,300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Accounting Program - By Matthew Janssen");
		
		thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());
		
		this.tm = TransactionManager.getInstance();
		createPanelComponents();
		
		frame.add(thePanel);
		frame.setVisible(true);
	}
	
	private void createPanelComponents() {
		GridBagConstraints c = new GridBagConstraints();
		Font font = new Font("Tahoma", Font.PLAIN, 15);
		String[] accounts = tm.getAccounts(0);
		JComboBox<String> cb2 = new JComboBox<String>(accounts);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 3;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		
		JLabel headerLabel = new JLabel("Select bank");
		headerLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		thePanel.add(headerLabel, c);
		
		JLabel bankLabel = new JLabel("Bank");
		bankLabel.setFont(font);
		c.gridwidth = 1;
		c.gridy++;
		c.ipadx = 20;
		c.anchor = GridBagConstraints.EAST;
		thePanel.add(bankLabel, c);
		
		String[] banks = tm.getAllBanks();
		JComboBox<String> cb1 = new JComboBox<String>(banks);
		Dimension preferredSize = cb1.getPreferredSize();
	    preferredSize.height = 30;
	    preferredSize.width = 450;
	    cb1.setPreferredSize(preferredSize);
		cb1.setFont(font);
		c.ipadx = 0;
		c.gridx = 2;
		c.anchor = GridBagConstraints.WEST;
		cb1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] newAccounts = tm.getAccounts(cb1.getSelectedIndex());
				cb2.removeAllItems();
				for (String account : newAccounts) {
					cb2.addItem(account);
				}
			}
			
		});
		thePanel.add(cb1, c);
		
		JLabel accountLabel = new JLabel("Account");
		accountLabel.setFont(font);
		c.gridx = 1;
		c.gridy++;
		c.ipadx = 20;
		c.anchor = GridBagConstraints.EAST;
		thePanel.add(accountLabel, c);
		
	    cb2.setPreferredSize(preferredSize);
		cb2.setFont(font);
		c.ipadx = 0;
		c.gridx = 2;
		c.anchor = GridBagConstraints.WEST;
		thePanel.add(cb2, c);
		
		JLabel fileLabel = new JLabel("File Location");
		fileLabel.setFont(font);
		c.gridx = 1;
		c.gridy++;
		c.ipadx = 20;
		c.anchor = GridBagConstraints.EAST;
		thePanel.add(fileLabel, c);
		
		JTextField tf = new JTextField();
	    tf.setPreferredSize(preferredSize);
		tf.setFont(font);
		c.gridx++;
		c.ipadx = 0;
		c.anchor = GridBagConstraints.WEST;
		thePanel.add(tf, c);
		
		JButton findFileButton = new JButton("Select...");
		findFileButton.setPreferredSize(new Dimension(80, 22));
		c.gridx = 3;
		c.anchor = GridBagConstraints.WEST;
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
					new NewImport(bankID, accountID, fileLoc);
					frame.dispose();
					new NewImportGUI();
				}
				
			}
			
		});
	}
}
