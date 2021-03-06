package Import_Export;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import Transactions.TransactionManager;
import mainGUI.ModifiableJOptionPane;

public class ImportSelectJOptionPane extends ModifiableJOptionPane {

	private int accountID;
	private int bankID;
	private JComboBox<String> cb1;
	private JComboBox<String> cb2;
	private JTextField tf;

	public ImportSelectJOptionPane() {
		super();
		displayGUI();
	}
	
	@Override
	public void checkInput(int input) {
		if(input == JOptionPane.CANCEL_OPTION || input == JOptionPane.CLOSED_OPTION) {
			done = true;
		    return ;
		}
		else {
			String locText = tf.getText();
			if (locText.isEmpty()) {
				JOptionPane.showMessageDialog(null,
					    "The file chosen cannot be imported",
					    "Incorrect File",
					    JOptionPane.ERROR_MESSAGE);
			}
			else {
				bankID = cb1.getSelectedIndex();
				accountID = cb2.getSelectedIndex();
				fileLoc = tf.getText();
				new ImportTransactions(bankID, accountID, fileLoc);
				new ImportTransactionsJOptionPane();
				done = true;
			}			
		}
	}

	@Override
	public void displayGUI() {
    	while (!done) {
            int input = JOptionPane.showConfirmDialog(null,
                    getPanel(),
                    "Import New Transactions",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			checkInput(input);
    	}        
    }

	@Override
	public JPanel getPanel() {
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 3);
		String[] accounts = TransactionManager.getAccounts(0);
		cb2 = new JComboBox<String>(accounts);
		
		JLabel headerLabel = createLabel("Select Bank", largeFont);
		JPanel thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());
		thePanel.add(headerLabel, c);
		
		JLabel bankLabel = createLabel("Bank", mediumFont);
		c.gridwidth = 1;
		c.gridy++;
		c.ipadx = 20;
		c.anchor = GridBagConstraints.EAST;
		thePanel.add(bankLabel, c);
		
		String[] banks = TransactionManager.getAllBanks();
		cb1 = new JComboBox<String>(banks);
	    cb1.setPreferredSize(tfDim);
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
		c.gridy = 10;
		c.ipadx = 20;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		thePanel.add(accountLabel, c);
		
	    cb2.setPreferredSize(tfDim);
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
		
		tf = new JTextField();
	    tf.setPreferredSize(tfDim);
		tf.setFont(mediumFont);
		c.gridx++;
		c.ipadx = 0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		//tf.setText("C:/Users/matth/Downloads/CSVData.csv");
		tf.setText("C:/Users/matth/Desktop/trans040118.csv");
		//tf.setText("C:/Users/matth/Downloads/ANZ.csv");
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
				JFileChooser fc = new JFileChooser();
				FileFilter filter = new FileFilter() {

				    public String getDescription() {
				        return "Comma Delimited Files (*.csv)";
				    }
				 
				    public boolean accept(File f) {
				        if (f.isDirectory()) {
				            return true;
				        } else {
				            return f.getName().toLowerCase().endsWith(".csv");
				        }
				    }
					
				};
				fc.setFileFilter(filter);
				Action details = fc.getActionMap().get("viewTypeDetails");
				details.actionPerformed(null);
				fc.setPreferredSize(new Dimension(700, 500));
				fc.setCurrentDirectory(new File  
						(System.getProperty("user.home") + System.getProperty("file.separator")+ "Downloads"));
				int returnValue = fc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					tf.setText(selectedFile.getAbsolutePath());
				}
			}
			
		});
		return thePanel;
	}
}
