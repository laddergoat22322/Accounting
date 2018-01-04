package mainGUI;
/**
 * @author      Matthew Janssen
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import Import_Export.ImportSelectGUI;
import Transactions.TransactionAnalytics;
import Transactions.TransactionManager;

import java.text.ParseException;
import java.util.Arrays;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {
	private String fileLoc;
	private JFrame frame;
	private Font headerFont;  
    private JMenuItem i1, i2;
    private Font largeFont;
    private Font largeHeaderFont;
    private Font mediumFont;
    private JMenu menu;
    private Font smallFont;
    private TransactionAnalytics ta;
	private TransactionManager tm;

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	public MainGUI() {
		this.tm = TransactionManager.getInstance();
		fileLoc = "C:/Accounting Program/Data.xml";
		ta = new TransactionAnalytics();
		initialize();
	}

	private JPanel createBankTotalsPanel() {
		JPanel panel = createPanel();
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 2);
		
		JLabel headerLabel = createLabel("Bank Totals", headerFont);
		panel.add(headerLabel, c);
		for(int i = 0; i < tm.getNumberOfBanks(); i++) {
			c.gridx = 0;
			c.gridy++;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.WEST;
			JLabel bankLabel = createLabel(tm.getBankName(i), mediumFont);
			panel.add(bankLabel, c);
			
			
			String[] accounts = tm.getAccounts(i);
			for(int j = 0; j < accounts.length; j++) {
				//Account
				JLabel accountLabel = createLabel(accounts[j], smallFont);
				c.gridx = 0;
				c.gridy++;
				c.gridwidth = 1;
				c.anchor = GridBagConstraints.WEST;
				panel.add(accountLabel, c);
				
				//Total
				JLabel amountLabel = createLabel("$" + ta.getAccountTotal(i, j), smallFont);
				c.gridx = 1;
				panel.add(amountLabel, c);
			}
		}
		return panel;
	}

	private JPanel createCategoryTotalsPanel() {
		JPanel panel = createPanel();
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 2);
		
		JLabel headerLabel = createLabel("Category Totals", headerFont);
		panel.add(headerLabel, c);
		
		for(int i = 0; i < tm.getNumberOfCategories(); i++) {
			
			//category
			c.gridx = 0;
			c.gridwidth = 1;
			c.gridy++;
			c.anchor = GridBagConstraints.WEST;
			c.fill = GridBagConstraints.NONE;
			JLabel categoryLabel = createLabel(tm.getCategory(i), mediumFont);
			panel.add(categoryLabel, c);
			
			//total
			JLabel amountLabel = createLabel("$" + ta.getCategoryTotal(i), smallFont);
			c.gridx++;
			panel.add(amountLabel, c);
		}
		return panel;
	}

	private JLabel createLabel(String labelName, Font font) {
		JLabel label = new JLabel(labelName);
		label.setFont(font);
		return label;
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

	private JPanel createPanel() {
		JPanel panel = new JPanel();
		Border blackline = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		panel.setLayout(new GridBagLayout());
		panel.setBorder(blackline);
		return panel;
	}

	private JPanel createSavingsTotalsPanel() {
		JPanel panel = createPanel();
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 1);
		
		JLabel headerLabel = createLabel("Savings Totals", headerFont);
		panel.add(headerLabel, c);
		return panel;
	}

	private JPanel createWeeklyTotalsPanel() {
		JPanel panel = createPanel();
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 1);
		
		JLabel headerLabel = createLabel("Weekly Totals", headerFont);
		panel.add(headerLabel, c);
		
		//Initialize JTable
		String[][] data = ta.getWeeklyTotals();
		String[] header = ta.getWeeklyTotalsHeader();
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
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle(tm.getUserName() + "'s Accounting Program");
		
		frame.setLayout(new GridBagLayout());
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.BOTH, 3);
		setupFonts();
		
		JLabel headerLabel = createLabel("Totals", largeHeaderFont);
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
		c.weighty = 400;
		c.gridwidth = 3;
		frame.add(weeklyTotalsPanel, c);
		
		createMenu();
		
		
		frame.setVisible(true);
	}

	private void setupFonts() {
		largeHeaderFont = new Font("SansSerif", Font.BOLD, 35);
		headerFont = new Font("SansSerif", Font.BOLD, 25);
		largeFont = new Font("SansSerif", Font.BOLD, 25);
		mediumFont = new Font("SansSerif", Font.PLAIN, 20);
		smallFont = new Font("SansSerif", Font.PLAIN, 15);
		
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
}