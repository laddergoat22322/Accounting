package mainGUI;
/**
 * @author      Matthew Janssen
 */

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Add_Remove_Components.AccountManagementJOptionPane;
import Add_Remove_Components.AddAccountJOptionPane;
import Add_Remove_Components.AddCategoryJOptionPane;
import Import_Export.ImportSelectJOptionPane;
import Import_Export.UserDataExport;
import Transactions.TransactionManager;

@SuppressWarnings("serial")
public class MainGUI extends GUI {
	private JPanel bankTotalsPanel;
	private JPanel categoryTotalsPanel;
	private JFrame frame;
	private JPanel weeklyTotalsPanel;


	public MainGUI() {
		initialize();
	}
	
	private void createBankTotalsPanel() {
		bankTotalsPanel = createBorderedPanel();
		refreshBankTotalsPanel();
	}

	private void createCategoryTotalsPanel() {
		categoryTotalsPanel = createBorderedPanel();
		refreshCategoryTotalsPanel();
	}
	
	private void createMenuBar() {
		
		JMenu fileMenu, addMenu;
		JMenuItem newImport, save, addAccount, addCategory;
		
		/** Create MenuBar*/
		JMenuBar mb=new JMenuBar();  
		fileMenu = createMenu("File", new Dimension(80, 30));
		
		newImport = createMenuItem("Import");
		newImport.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new ImportSelectJOptionPane();
			refreshAll();
		}});
		
		save = createMenuItem("Save");
		save.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new UserDataExport();
		}});
		fileMenu.add(newImport); fileMenu.add(save);
		
		addMenu = createMenu("Add/Remove", new Dimension(120, 30));
		addAccount = createMenuItem("Manage accounts");
		addAccount.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new AccountManagementJOptionPane();
			refreshAll();
		}});
		
		addMenu.add(addAccount);
		
		addCategory = createMenuItem("Add Category");
		addCategory.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new AddCategoryJOptionPane();
			refreshAll();
		}});
		
		addMenu.add(addCategory);
		
		mb.add(fileMenu); 
		mb.add(addMenu);
		frame.setJMenuBar(mb);  
		}

	private JPanel createSavingsTotalsPanel() {
		JPanel panel = createBorderedPanel();
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 1);
		
		JLabel headerLabel = createLabel("Savings Totals", headerFont);
		panel.add(headerLabel, c);
		return panel;
	}

	private void createWeeklyTotalsPanel() {
		weeklyTotalsPanel = createBorderedPanel();
		refreshWeeklyTotalsPanel();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle(TransactionManager.getUserName() + "'s Accounting Program");
		
		frame.setLayout(new GridBagLayout());
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.BOTH, 3);
		
		JLabel headerLabel = createLabel("Totals", largeHeaderFont);
		JPanel headerPanel = new JPanel();
		headerPanel.add(headerLabel);
		frame.getContentPane().add(headerPanel, c);
		
		createBankTotalsPanel();
		c.gridy++;
		c.weighty = 10;
		c.gridwidth = 1;
		frame.getContentPane().add(bankTotalsPanel, c);
		
		createCategoryTotalsPanel();
		c.gridx++;
		frame.getContentPane().add(categoryTotalsPanel, c);
		
		JPanel savingsTotalPanel = createSavingsTotalsPanel();
		c.gridx++;
		frame.getContentPane().add(savingsTotalPanel, c);
		
		createWeeklyTotalsPanel();
		c.gridx = 0;
		c.gridy++;
		c.weighty = 400;
		c.gridwidth = 3;
		frame.getContentPane().add(weeklyTotalsPanel, c);
		
		createMenuBar();
		
		
		frame.setVisible(true);
	}
	
	private void refreshAll() {
		refreshBankTotalsPanel();
		refreshCategoryTotalsPanel();
		refreshWeeklyTotalsPanel();
		frame.invalidate();
		frame.revalidate();
	}
	
	private void refreshBankTotalsPanel() {
		bankTotalsPanel.removeAll();
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 2);
		
		JLabel headerLabel = createLabel("Bank Totals", headerFont);
		bankTotalsPanel.add(headerLabel, c);
		int numBanks = TransactionManager.getNumberOfBanks();
		for(int i = 0; i < numBanks; i++) {
			c.gridx = 0;
			c.gridy++;
			c.gridwidth = 1;
			c.weighty = 15;
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.WEST;
			JLabel bankLabel = createLabel(TransactionManager.getBankName(i), mediumFont);
			bankTotalsPanel.add(bankLabel, c);
			
			
			String[] accounts = TransactionManager.getAccounts(i);
			for(int j = 0; j < accounts.length; j++) {
				//Account
				JLabel accountLabel = createLabel(accounts[j], smallFont);
				c.gridx = 0;
				c.weighty = 1;
				c.gridy++;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.WEST;
				bankTotalsPanel.add(accountLabel, c);
				
				//Total
				JLabel amountLabel = createLabel("$" + TransactionManager.getAccountTotal(i, j), smallFont);
				c.gridx = 1;
				bankTotalsPanel.add(amountLabel, c);
			}
		}
		
	}

	private void refreshCategoryTotalsPanel() {
		categoryTotalsPanel.removeAll();
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 2);
		
		JLabel headerLabel = createLabel("Category Totals", headerFont);
		categoryTotalsPanel.add(headerLabel, c);
		for(int i = 0; i < TransactionManager.getNumberOfCategories(); i++) {
			if (i % 2 == 0) {
				c.gridx=0;
				c.gridy++;
			}
			else {
				c.gridx++;
			}
			
			//category
			c.gridwidth = 1;
			c.anchor = GridBagConstraints.WEST;
			c.fill = GridBagConstraints.NONE;
			JLabel categoryLabel = createLabel(TransactionManager.getCategory(i), mediumFont);
			categoryTotalsPanel.add(categoryLabel, c);
			
			//total
			JLabel amountLabel = createLabel("$" + TransactionManager.getCategoryTotal(i), smallFont);
			c.gridx++;
			categoryTotalsPanel.add(amountLabel, c);
		}
	}

	private void refreshWeeklyTotalsPanel() {
		weeklyTotalsPanel.removeAll();
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 1);
		
		JLabel headerLabel = createLabel("Weekly Totals", headerFont);
		weeklyTotalsPanel.add(headerLabel, c);
		
		//Initialize JTable
		String[][] data = TransactionManager.getWeeklyTotals();
		String[] header = TransactionManager.getWeeklyTotalsHeader();
		JTable table = new JTable(data,header){
			  public boolean isCellEditable(int row,int column){
				    return false;
			  }
		};
		
		JScrollPane scrollPane=new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 1000));
		c.gridy = 2;
		c.weighty = 15;
		c.fill = GridBagConstraints.BOTH;
		weeklyTotalsPanel.add(scrollPane, c);
	}

	private void updateBankTotalsPanel() {
		frame.getContentPane().invalidate();
		refreshBankTotalsPanel();
		frame.revalidate();
		}
	
	private void updateCategoryTotalsPanel() {
		frame.getContentPane().invalidate();
		refreshCategoryTotalsPanel();
		frame.revalidate();
		}
}