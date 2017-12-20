package Import_Export;

import java.awt.EventQueue;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Transactions.TransactionManager;
import mainGUI.MainGUI;

public class SelectImportTypeGUI {
	
	private JFrame frame;
	private JPanel thePanel;
	private int bankID;
	private int fileType;
	
	public SelectImportTypeGUI() {
		initialize();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SelectImportTypeGUI();
			}
		});
	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(500,400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Accounting Program - By Matthew Janssen");
		
		thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());
		
		createPanelComponents();
		
		frame.add(thePanel);
		frame.setVisible(true);
	}
	
	private void createPanelComponents() {
		JComboBox cb1 = new JComboBox<String>();
	}
}
