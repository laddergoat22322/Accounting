package mainGUI;
/**
 * @author      Matthew Janssen
 */

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;

import Import_Export.ImportSelectGUI;
import Import_Export.ImportUsersData;
import Transactions.TransactionManager;

import java.text.ParseException;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {
	private JFrame frame;
	private JPanel thePanel;
	private JMenu menu;  
    private JMenuItem i1, i2;
    private TransactionManager tm;
    private String fileLoc;

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	public MainGUI() {
		this.tm = TransactionManager.getInstance();
		fileLoc = "C:/Accounting Program/Data.xml";
		initialize();
	}
	
	public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		

		public void run() {
			File tmpDir = new File("C:/Accounting Program/Data.xml");
			boolean exists = tmpDir.exists();
			if (exists) {
				new MainGUI();
			}
			else {
				new FirstRunGUI();
				new MainGUI();
			}
		}
	});
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
		checkImportUserData();
		
		frame.add(thePanel);
		frame.setVisible(true);
	}

	private void checkImportUserData() {
		File tmpDir = new File(fileLoc);
		boolean exists = tmpDir.exists();
		if(!exists) {
			return ;
		}
		else {
			new ImportUsersData();
		}
		
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