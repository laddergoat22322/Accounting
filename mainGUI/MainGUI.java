package mainGUI;
/**
 * @author      Matthew Janssen
 */

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.*;

import Import_Export.ImportSelectGUI;
import Import_Export.UserDataImport;
import Transactions.TransactionManager;

import java.text.ParseException;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {
	private JFrame frame;
	private JMenu menu;  
    private JMenuItem i1, i2;
    private TransactionManager tm;
    private String fileLoc;
    private Font font;

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	public MainGUI() {
		this.tm = TransactionManager.getInstance();
		fileLoc = "C:/Accounting Program/Data.xml";
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle(tm.getUserName() + "'s Accounting Program");
		
		frame.setLayout(new GridBagLayout());
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.BOTH, 2);
		font = new Font("Tahoma", Font.PLAIN, 15);
		
		JLabel headerLabel = new JLabel("Totals");
		headerLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		JPanel headerPanel = new JPanel();
		headerPanel.add(headerLabel);
		frame.add(headerPanel, c);
		
		JPanel accountTotalsPanel = createBankTotalsPanel();
		c.gridy++;
		c.weighty = 10;
		c.gridwidth = 1;
		frame.add(accountTotalsPanel, c);
		
		JPanel categoryTotalsPanel = createCategoryTotalsPanel();
		c.gridx++;
		frame.add(categoryTotalsPanel, c);
		
		JPanel savingsTotalPanel = createSavingsTotalsPanel();
		c.gridx++;
		frame.add(savingsTotalPanel, c);
		
		JPanel weeklyTotalsPanel = createWeeklyTotalsPanel();
		c.gridx = 0;
		c.gridy++;
		c.weighty = 100;
		frame.add(weeklyTotalsPanel, c);
		
		createMenu();
		
		
		frame.setVisible(true);
	}

	private JPanel createWeeklyTotalsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.BOTH, 2);
		
		JLabel headerLabel = createLabel("Weekly Totals", Font.BOLD, 25);
		panel.add(headerLabel, c);
		return panel;
	}

	private JPanel createSavingsTotalsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.BOTH, 2);
		
		JLabel headerLabel = createLabel("Savings Totals", Font.BOLD, 25);
		panel.add(headerLabel, c);
		return panel;
	}

	private JPanel createCategoryTotalsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.BOTH, 2);
		
		JLabel headerLabel = createLabel("Category Totals", Font.BOLD, 25);
		panel.add(headerLabel, c);
		for(int i = 0; i < tm.getNumberOfCategories(); i++) {
			
			//category
			c.gridx = 0;
			c.gridwidth = 1;
			c.gridy++;
			c.anchor = GridBagConstraints.WEST;
			c.fill = GridBagConstraints.NONE;
			JLabel categoryLabel = createLabel(tm.getCategory(i), Font.BOLD, 15);
			panel.add(categoryLabel, c);
			
			//total
			JLabel amountLabel = createLabel("$$$", Font.PLAIN, 10);
			c.gridx++;
			panel.add(amountLabel, c);
		}
		return panel;
	}

	private JPanel createBankTotalsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 1);
		
		JLabel headerLabel = createLabel("Bank Totals", Font.BOLD, 25);
		panel.add(headerLabel, c);
		for(int i = 0; i < tm.getNumberOfBanks(); i++) {
			c.gridx = 0;
			c.gridy++;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.WEST;
			JLabel bankLabel = createLabel(tm.getBankName(i), Font.BOLD, 15);
			panel.add(bankLabel, c);
			
			
			String[] accounts = tm.getAccounts(i);
			for(int j = 0; j < accounts.length; j++) {
				//Account
				JLabel accountLabel = createLabel(accounts[j], Font.PLAIN, 10);
				c.gridx = 0;
				c.gridy++;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.WEST;
				panel.add(accountLabel, c);
				
				//Total
				JLabel amountLabel = createLabel("$$$", Font.PLAIN, 10);
				c.gridx = 1;
				panel.add(amountLabel, c);
			}
		}
		return panel;
	}

	private JLabel createLabel(String labelName, int fontType, int fontSize) {
		JLabel label = new JLabel(labelName);
		label.setFont(new Font("Tahoma", fontType, fontSize));
		return label;
	}
	
	private GridBagConstraints setupGridBag(int anchor, int fill, int width) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = width;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = anchor;
		c.fill = fill;
		return c;
	}

	private void createMenu() {  
          JMenuBar mb=new JMenuBar();  
          menu=new JMenu("File"); 
          menu.setPreferredSize(new Dimension(80, 30));
          menu.setFont(new Font("Tahoma", Font.PLAIN, 18));
          i1 = createMenuItem("Import");
          i1.addActionListener(new ActionListener() {
        	  public void actionPerformed(ActionEvent e) {
        		  new ImportSelectGUI();
        	  }
          });;
          i2 = createMenuItem("Settings");
          menu.add(i1); menu.add(i2);
          mb.add(menu);  
          frame.setJMenuBar(mb);  
		
	}
	
	private JMenuItem createMenuItem(String name) {
		JMenuItem item=new JMenuItem(name);
        item.setFont(new Font("Tahoma", Font.PLAIN, 15));
        item.setPreferredSize(new Dimension(120, 30));
        return item;
	}
}