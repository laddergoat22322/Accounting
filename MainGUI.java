/**
 * @author      Matthew Janssen
 */

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;

import java.text.ParseException;
import java.awt.GridBagLayout;

public class MainGUI extends JFrame {
	private JFrame frame;
	private JPanel thePanel;
	private String userName;
	JMenu menu, submenu;  
    JMenuItem i1, i2, i3, i4, i5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//new FirstRunGUI();
				new MainGUI("Testing");
			}
		});
	}

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	public MainGUI(String uName) {
		this.userName = uName;
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle(userName + "'s Accounting Program");
		
		thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());
		
		createMenu();
		
		createPanelComponents();
		
		
		
		frame.add(thePanel);
		frame.setVisible(true);
	}

	private void createMenu() {  
          JMenuBar mb=new JMenuBar();  
          menu=new JMenu("File"); 
          menu.setPreferredSize(new Dimension(80, 30));
          menu.setFont(new Font("Tahoma", Font.PLAIN, 18));
          i1 = createMenuItem("Import"); 
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

	private void createPanelComponents() {
		// TODO Auto-generated method stub
		
	}
}