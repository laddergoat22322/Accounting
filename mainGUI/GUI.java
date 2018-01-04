package mainGUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import Transactions.TransactionAnalytics;
import Transactions.TransactionManager;

public class GUI {
	protected String fileLoc;
    protected Font largeHeaderFont, headerFont, largeFont, mediumFont, smallFont, menuFont;
	protected TransactionAnalytics ta;
	protected TransactionManager tm;
	
	public GUI() {
		tm = TransactionManager.getInstance();
		fileLoc = "C:/Accounting Program/Data.xml";
		ta = new TransactionAnalytics();
		setupFonts();
		setupUIManager();
	}
	
	private void setupUIManager() {
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
		
	}

	protected JMenuItem createMenuItem(String name) {
		JMenuItem item=new JMenuItem(name);
	    item.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    item.setPreferredSize(new Dimension(120, 30));
	    return item;
	}
	
	protected JLabel createLabel(String labelName, Font font) {
		JLabel label = new JLabel(labelName);
		label.setFont(font);
		return label;
	}
	
	protected JMenu createMenu(String name, Dimension dimension) {
		JMenu m = new JMenu(name);
		m.setPreferredSize(dimension);
		m.setFont(menuFont);
		return m;
	}
	
	private void setupFonts() {
		largeHeaderFont = new Font("SansSerif", Font.BOLD, 35);
		headerFont = new Font("SansSerif", Font.BOLD, 25);
		largeFont = new Font("SansSerif", Font.BOLD, 25);
		mediumFont = new Font("SansSerif", Font.PLAIN, 20);
		smallFont = new Font("SansSerif", Font.PLAIN, 15);
		menuFont = new Font("SansSerif", Font.PLAIN, 17);
	}
	
	protected JPanel createBorderedPanel() {
		JPanel panel = new JPanel();
		Border blackline = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		panel.setLayout(new GridBagLayout());
		panel.setBorder(blackline);
		return panel;
	}
	
	protected GridBagConstraints setupGridBag(int anchor, int fill, int width) {
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
