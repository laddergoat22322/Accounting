package Import_Export;
/**
 * @author      Matthew Janssen
 */

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import Transactions.TransactionManager;

import java.text.ParseException;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class NewImportGIU extends JFrame {
	private JFrame frame;
	private Font defaultFont;
	private String userName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				NewImportGIU window = new NewImportGIU();
			}
		});
	}

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	public NewImportGIU() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 */
	private void initialize() {
		
		//Initialize GUI
		frame = new JFrame();
		frame.setSize(1400,900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle("Accounting Program - By " + userName);
		
		
		//default font
		defaultFont = new Font("Tahoma", Font.PLAIN, 15);
		
		//Initialize Transaction Manager
		TransactionManager tm = new TransactionManager();
		new Import("C:/Users/matth/Downloads/CSVData.csv", "ANZ", tm);
		String[][] allData = tm.exportAllTransactions_Individual();
		String[] header = tm.exportIndividualTransactionHeader();
		
		
		//Initialize JComboBox
		String[] categories = tm.exportCategories();
		JComboBox<String> combo = new JComboBox<String>(categories);
		combo.setFont(defaultFont);
		
		
		//Initialize JTable
		JTable table = new JTable(allData,header);
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
		frame.getContentPane().add(scrollPane);
		
		frame.setVisible(true);
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
