package mainGUI;
/**
 * @author      Matthew Janssen
 */

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;

import Import_Export.SelectImportGUI;
import Transactions.TransactionManager;

import java.text.ParseException;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {
	private JFrame frame;
	private JPanel thePanel;
	private JMenu menu;  
    private JMenuItem i1, i2;
    private TransactionManager tm;

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	public MainGUI(String uName) {
		this.tm = new TransactionManager();
		tm.setUserName(uName);
		initialize();
		
		
		
	}

	private void initialize() {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle(tm.getUserName() + "'s Accounting Program");
		
		thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());
		
		createMenu();
		
		frame.add(thePanel);
		frame.setVisible(true);
	}

	private void createMenu() {  
          JMenuBar mb=new JMenuBar();  
          menu=new JMenu("File"); 
          menu.setPreferredSize(new Dimension(80, 30));
          menu.setFont(new Font("Tahoma", Font.PLAIN, 18));
          i1 = createMenuItem("Import");
          i1.addActionListener(new ActionListener() {
        	  public void actionPerformed(ActionEvent e) {
        		  new SelectImportGUI();
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