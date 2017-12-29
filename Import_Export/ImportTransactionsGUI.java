package Import_Export;
/**
 * @author      Matthew Janssen
 */

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.table.TableColumn;

import Transactions.TransactionManager;
import Transactions.TransactionManager.TransactionAttribute;

import java.text.ParseException;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImportTransactionsGUI{
	private JFrame frame;
	private JPanel thePanel;
	private Font defaultFont;
	private TransactionManager tm;
	private JTable table;
	private Double[] transactionIndex;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ImportTransactionsGUI();
			}
		});
	}

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	public ImportTransactionsGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 */
	private void initialize() {
		
		//Initialize Transaction Manager
		this.tm = TransactionManager.getInstance();
		
		//default font
		defaultFont = new Font("Tahoma", Font.PLAIN, 15);
		
		//Initialize GUI
		frame = new JFrame();
		frame.setSize(1200,900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle("Accounting Program - By " + tm.getUserName());
		
		createGUIComponents();
		
		frame.add(thePanel);
		frame.setVisible(true);
	}

	@SuppressWarnings("serial")
	private void createGUIComponents() {
		
		thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 3;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		
		JLabel headerLabel = new JLabel("Imported Transactions");
		headerLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		thePanel.add(headerLabel, c);
		
		//Initialize JComboBox
		String[] categories = tm.getCategories();
		JComboBox<String> combo = new JComboBox<String>(categories);
		combo.setFont(defaultFont);		
		
		//Initialize JTable
		String[][] data = tm.getNewTransactions();
		String[] header = tm.getIndividualTransactionHeader();
		transactionIndex = tm.getNewTransactionIndexes();
		table = new JTable(data,header){
			  public boolean isCellEditable(int row,int column){
				    if(column < 1 || column > 1) return false;
				    return true;
			  }
		};
		table.setFont(defaultFont);
		table.setRowHeight(20);
		
		TableColumn col = table.getColumnModel().getColumn(1);
		col.setCellEditor(new DefaultCellEditor(combo));
		
		//Set width of columns
		table.getColumnModel().getColumn(0).setPreferredWidth(50); 	//Date
		table.getColumnModel().getColumn(1).setPreferredWidth(200);	//Category
		table.getColumnModel().getColumn(2).setPreferredWidth(650);	//Description
		table.getColumnModel().getColumn(3).setPreferredWidth(100);	//Amount
		
		JScrollPane scrollPane=new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 1000));
		c.gridy = 2;
		c.weighty = 15;
		c.fill = GridBagConstraints.BOTH;
		thePanel.add(scrollPane, c);
		
		JButton doneButton = new JButton("Done");
		doneButton.setFont(defaultFont);
		doneButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult;
				int numRows = table.getRowCount();
				for(int i = 0; i < numRows; i++) {
					String cell = (String) table.getModel().getValueAt(i, 1);
					if (cell.equals("Uncategorized")) {
						dialogResult = JOptionPane.showConfirmDialog(frame, 
								"Not all transactions are categorised.\n Do you still want to continue?",
								"Warning", 
								JOptionPane.YES_NO_OPTION);
						if (dialogResult == JOptionPane.YES_OPTION) {
							finaliseGUI();
							return ;
						}
						else return ;
					}
					finaliseGUI();
				}
			}			
		});
		c.gridy = 3;
		c.weighty = 1;
		c.fill = GridBagConstraints.NONE;
		thePanel.add(doneButton, c);
	}
	private void setCategories() {
		int numRows = table.getRowCount();
		for(int i = 0; i < numRows; i++) {
			String cell = (String) table.getModel().getValueAt(i, 1);
			int categoryIndex = tm.getCategoryByIndex(cell);
			tm.setTransaction(TransactionAttribute.ACCOUNT_ID, transactionIndex[i], categoryIndex);
		}
	}
	
	private void finaliseGUI() {
		setCategories();
		new UserDataExport();
		frame.dispose();
	}
}
