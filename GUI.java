import java.awt.EventQueue;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import org.eclipse.swt.widgets.DateTime;

import Transactions.TransactionManager;

import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.awt.Insets;
import java.awt.Font;

public class GUI extends JFrame {
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUI window = new GUI();
			}
		});
	}

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(1400,900);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle("Accounting Program - By Matthew Janssen");
		
		TransactionManager tm = new TransactionManager();
		String[][] allData = tm.exportAllTransactions_Individual();
		String[] categories = tm.exportIndividualTransactionHeader();
		         
		JTable table = new JTable(allData,categories);
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setRowHeight(20);
		
		
		//Set width of columns
		int rows = table.getColumnCount();
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		for(int i = 1; i < rows; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(200);
		}
		
		//Justify right
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		for(int i = 1; i < rows; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
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
