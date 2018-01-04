package mainGUI;
/**
 * @author      Matthew Janssen
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import Add_Remove_Components.AddCategoryGUI;
import Add_Remove_Components.AddNewAccountGUI;
import Import_Export.ImportSelectGUI;
import Transactions.TransactionAnalytics;
import Transactions.TransactionManager;

@SuppressWarnings("serial")
public class MainGUI extends GUI {
	private JFrame frame;
	private JPanel categoryTotalsPanel;


	public MainGUI() {
		super();
		initialize();
		Thread t = new Thread() {
		    public void run() {
		    	int numCategories = TransactionManager.getNumberOfCategories();
		    	while (true) {
					if (numCategories != TransactionManager.getNumberOfCategories() || GUI.refreshMain) {
						refreshMain();
						System.out.println("refreshed");
						GUI.refreshMain = false;
					}
		    	}

		    }
		 };
		 t.start();
	}

	private void refreshMain() {
		categoryTotalsPanel.removeAll();
		createCategoryTotalsPanel();
		categoryTotalsPanel.revalidate();
		categoryTotalsPanel.repaint();
		frame.revalidate();
		frame.repaint();
	}

	private JPanel createBankTotalsPanel() {
		JPanel panel = createBorderedPanel();
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 2);
		
		JLabel headerLabel = createLabel("Bank Totals", headerFont);
		panel.add(headerLabel, c);
		for(int i = 0; i < TransactionManager.getNumberOfBanks(); i++) {
			c.gridx = 0;
			c.gridy++;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.WEST;
			JLabel bankLabel = createLabel(TransactionManager.getBankName(i), mediumFont);
			panel.add(bankLabel, c);
			
			
			String[] accounts = TransactionManager.getAccounts(i);
			for(int j = 0; j < accounts.length; j++) {
				//Account
				JLabel accountLabel = createLabel(accounts[j], smallFont);
				c.gridx = 0;
				c.gridy++;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.WEST;
				panel.add(accountLabel, c);
				
				//Total
				JLabel amountLabel = createLabel("$" + TransactionManager.getAccountTotal(i, j), smallFont);
				c.gridx = 1;
				panel.add(amountLabel, c);
			}
		}
		return panel;
	}

	private void createCategoryTotalsPanel() {
		categoryTotalsPanel = createBorderedPanel();
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 2);
		
		JLabel headerLabel = createLabel("Category Totals", headerFont);
		categoryTotalsPanel.add(headerLabel, c);
		
		for(int i = 0; i < TransactionManager.getNumberOfCategories(); i++) {
			
			//category
			c.gridx = 0;
			c.gridwidth = 1;
			c.gridy++;
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

	private void createMenuBar() {
		
		JMenu fileMenu, addMenu;
		JMenuItem newImport, settings, addAccount, addCategory;
		
		/** Create MenuBar*/
		JMenuBar mb=new JMenuBar();  
		fileMenu = createMenu("File", new Dimension(80, 30));
		  
		newImport = createMenuItem("Import");
		newImport.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new ImportSelectGUI();
		}});
		
		settings = createMenuItem("Settings");
		fileMenu.add(newImport); fileMenu.add(settings);
		
		addMenu = createMenu("Add/Remove", new Dimension(120, 30));
		addAccount = createMenuItem("Add Account");
		addAccount.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new AddNewAccountGUI();	
		}});
		
		addMenu.add(addAccount);
		
		addCategory = createMenuItem("Add Category");
		addCategory.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new AddCategoryGUI();
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

	private JPanel createWeeklyTotalsPanel() {
		JPanel panel = createBorderedPanel();
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 1);
		
		JLabel headerLabel = createLabel("Weekly Totals", headerFont);
		panel.add(headerLabel, c);
		
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
		panel.add(scrollPane, c);
		
		return panel;
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
		frame.add(headerPanel, c);
		
		JPanel accountTotalsPanel = createBankTotalsPanel();
		c.gridy++;
		c.weighty = 10;
		c.gridwidth = 1;
		frame.add(accountTotalsPanel, c);
		
		createCategoryTotalsPanel();
		c.gridx++;
		frame.add(categoryTotalsPanel, c);
		
		JPanel savingsTotalPanel = createSavingsTotalsPanel();
		c.gridx++;
		frame.add(savingsTotalPanel, c);
		
		JPanel weeklyTotalsPanel = createWeeklyTotalsPanel();
		c.gridx = 0;
		c.gridy++;
		c.weighty = 400;
		c.gridwidth = 3;
		frame.add(weeklyTotalsPanel, c);
		
		createMenuBar();
		
		
		frame.setVisible(true);
	}
}