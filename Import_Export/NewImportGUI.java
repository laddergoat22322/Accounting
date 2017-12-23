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

import java.text.ParseException;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewImportGUI{
	private JFrame frame;
	private JPanel thePanel;
	private Font defaultFont;
	private TransactionManager tm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new NewImportGUI();
			}
		});
	}

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	public NewImportGUI() {
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

	private void createGUIComponents() {
		
		thePanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
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
		String[][] data = tm.getAllNewTransactions();
		String[] header = tm.getIndividualTransactionHeader();
		JTable table = new JTable(data,header){
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
		int rows = table.getColumnCount();
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		for(int i = 1; i < rows; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(200);
		}
		
		JScrollPane scrollPane=new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 1000));
		c.gridy = 2;
		c.weighty = 15;
		c.fill = c.BOTH;
		thePanel.add(scrollPane, c);
		
		JButton doneButton = new JButton("Done");
		doneButton.setFont(defaultFont);
		doneButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int numRows = table.getRowCount();
				for(int i = 0; i < numRows; i++) {
					String cell = (String) table.getModel().getValueAt(i, 1);
					if (cell.equals("Choose...")) {
						JOptionPane.showMessageDialog(frame,
							    "All transactions must be categorised before continuing",
							    "Error",
							    JOptionPane.ERROR_MESSAGE);
						return ;
					}
				}
				
			}
			
		});
		c.gridy = 3;
		c.weighty = 1;
		c.fill = GridBagConstraints.NONE;
		thePanel.add(doneButton, c);
	}
	
//	private void tableInitialise() throws ParseException{
//		 cal.setFirstDayOfWeek(Calendar.MONDAY);
//	     String start = "25/12/2016";
//	     String end = "31/12/2017";
//	     SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
//	     Calendar scal=Calendar.getInstance();
//	     scal.setTime(dateFormat.parse(start));
//	     Calendar ecal=Calendar.getInstance();
//	     ecal.setTime(dateFormat.parse(end));
//
//	     ArrayList<Date> mondayDates=new ArrayList<>();
//
//	     while(!scal.equals(ecal)){
//	         scal.add(Calendar.DATE, 1);
//	         if(scal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
//	             mondayDates.add(scal.getTime());
//	         }
//	     }
//
//	     TOTALROWS = mondayDates.size();
//	     for(int i = 0; i < TOTALROWS; i++) {
//	    	 allData[i][0] = new SimpleDateFormat("dd/MM/yyyy").format(mondayDates.get(i));
//	     }
//	}

}
