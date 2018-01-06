package Import_Export;
/**
 * @author      Matthew Janssen
 */

import java.awt.Dimension;
import javax.swing.*;
import javax.swing.table.TableColumn;

import Transactions.TransactionManager;
import Transactions.TransactionManager.TransactionAttribute;
import mainGUI.GUI;

import java.text.ParseException;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImportTransactionsGUI extends GUI{
	private JFrame frame;
	private JPanel thePanel;
	private Font defaultFont;
	private JTable table;
	private Double[] transactionIndex;

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
		//Initialize GUI
		frame = new JFrame();
		frame.setSize(1200,900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle(TransactionManager.getUserName() + "'s Accounting Program");
		
		createGUIComponents();
		
		frame.add(thePanel);
		frame.setVisible(true);
	}

	@SuppressWarnings("serial")
	private void createGUIComponents() {
		
		thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 3);
		
		JLabel headerLabel = createLabel("Imported Transactions", largeFont);
		thePanel.add(headerLabel, c);
		
		//Initialize JComboBox
		String[] categories = TransactionManager.getCategories();
		JComboBox<String> combo = new JComboBox<String>(categories);
		combo.setFont(mediumFont);
		
		//Initialize JTable
		String[][] data = TransactionManager.getNewTransactions();
		String[] header = TransactionManager.getIndividualTransactionHeader();
		transactionIndex = TransactionManager.getNewTransactionIndexes();
		table = new JTable(data,header){
			  public boolean isCellEditable(int row,int column){
				    if(column < 1 || column > 1) return false;
				    return true;
			  }
		};
		table.setFont(mediumFont);
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
			int categoryIndex = TransactionManager.getCategoryByIndex(cell);
			TransactionManager.setTransaction(TransactionAttribute.ACCOUNT_ID, transactionIndex[i], categoryIndex);
		}
	}
	
	private void finaliseGUI() {
		setCategories();
		frame.dispose();
	}
}
