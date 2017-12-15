package Import_Export;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Transactions.Transaction;
import Transactions.TransactionManager;

public class Import {
	private ArrayList<Transaction> transactions;
	private String fileLoc;
	private String bank;
	
	public Import(String location, String bank, TransactionManager tm) {
		transactions = new ArrayList<>();
		this.fileLoc = location;
		this.bank = bank;
		if (this.bank.equals("ANZ")) {
			importANZ();
		}
	}
	
	private void importANZ() {
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {

            br = new BufferedReader(new FileReader(fileLoc));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] newImport = line.split(cvsSplitBy);
                
                System.out.println(newImport[0] + ", " + newImport[1] + ", " + newImport[2] + ", " + newImport[3] + ", ");

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	
		
		
		
	
	
	
	
	
	
	
	
	
	
		private void InitialiseDates() throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
	    String start = "25/12/2016";
	    String end = "31/12/2017";
	    SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
	    Calendar scal=Calendar.getInstance();
	    scal.setTime(dateFormat.parse(start));
	    Calendar ecal=Calendar.getInstance();
	    ecal.setTime(dateFormat.parse(end));

	    ArrayList<Date> mondayDates = new ArrayList<>();
	    while(!scal.equals(ecal)){
	        scal.add(Calendar.DATE, 1);
	        if(scal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
	            mondayDates.add(scal.getTime());
	        }
	    }

	    int TOTALROWS = mondayDates.size();
	    String[][] allData = new String[100][100];
	    for(int i = 0; i < TOTALROWS; i++) {
	   	 
		allData[i][0] = new SimpleDateFormat("dd/MM/yyyy").format(mondayDates.get(i));
	    }
	}
}
