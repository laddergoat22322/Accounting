package Import_Export;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import Add_Remove_Components.AddCategoryJOptionPane;
import Transactions.TransactionManager;
import mainGUI.ModifiableJOptionPane;

public class ImportTransactionsJOptionPane extends ModifiableJOptionPane {
	
	private JTable table;

	public ImportTransactionsJOptionPane() {
		super();
		displayGUI();
	}
	

	public void checkInput(int input) {
		
		if(JOptionPane.CANCEL_OPTION == input || input == JOptionPane.CLOSED_OPTION) {
			done = true;
			return ;
		}
		
		int dialogResult;
		int numRows = table.getRowCount();
		for(int i = 0; i < numRows; i++) {
			String cell = (String) table.getModel().getValueAt(i, 1);
			if (cell.equals("Uncategorized")) {
				dialogResult = JOptionPane.showConfirmDialog(null, 
						"Not all transactions are categorised.\n Do you wish to leave them uncategorised?",
						"Warning", 
						JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					setCategories();
					TransactionManager.analyseData();
					done = true;
					return ;
				}
				else {
					return ;
				}
			}
		}
		if (input == JOptionPane.OK_OPTION) {
			setCategories();
			TransactionManager.analyseData();
			done = true;
		}
	}

	public void displayGUI() {
    	while (!done) {
            int input = JOptionPane.showConfirmDialog(null,
                    getPanel(),
                    "Import New Transactions",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			checkInput(input);
    	}        
    }			


	public JPanel getPanel() {

		JPanel thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 3);
		
		JLabel headerLabel = createLabel("Imported Transactions", largeFont);
		thePanel.add(headerLabel, c);
		
		//Initialize JComboBox
		final JComboBox<String> combo = createCategoryCombobox();
		
		//Initialize JTable
		String[][] data = TransactionManager.getNewTransactions();
		String[] header = TransactionManager.getIndividualTransactionHeader();
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
		
		table.setRowHeight(table.getRowHeight() + 5);
		
		JScrollPane scrollPane=new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1500, 600));
		c.gridy = 2;
		c.weighty = 15;
		c.fill = GridBagConstraints.BOTH;
		thePanel.add(scrollPane, c);
		
		table.setSize(new Dimension(1000, 100));
		return thePanel;
    }
	
	private JComboBox<String> createCategoryCombobox() {
		String[] categories = TransactionManager.getCategories();
		final JComboBox<String> combo = new JComboBox<String>(categories);
		combo.addItem("Add New Category");
		combo.setFont(mediumFont);
		combo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String value = combo.getSelectedItem().toString();
				if(value.equals("Add New Category")){
					new AddCategoryJOptionPane();
					refreshTableCategories();
				}
			}
			
		});
		return combo;
	}


	private void setCategories() {
		int numRows = table.getRowCount();
		for(int i = 0; i < numRows; i++) {
			String cell = (String) table.getModel().getValueAt(i, 1);
			int categoryIndex = TransactionManager.getCategoryByIndex(cell);
			TransactionManager.getTempTransaction(i).setCategory(categoryIndex);
			TransactionManager.addTempTransaction(TransactionManager.getTempTransaction(i));
		}
	}
	
	private void refreshTableCategories() {
		final JComboBox<String> combo = createCategoryCombobox();
		TableColumn col = table.getColumnModel().getColumn(1);
		col.setCellEditor(new DefaultCellEditor(combo));
	}

}
