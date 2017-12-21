/**
 * The Import class imports a list of transactions provided through the 
 * file location and type of bank that has generated the file, commonly
 * CSV files.
 * @author Matthew Janssen
 */

package Import_Export;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import Transactions.TransactionManager;

public class NewImport {
	private static String fileLoc;
	private static int bankID;
	private static int accountID;
	private static TransactionManager tm;
	private static NewImport newImport;
	
	public synchronized static NewImport getInstance() {
		if (newImport == null) {
			newImport = new NewImport();
			firstSetup();
		}
		return newImport;
	}
	
	private static void firstSetup() {
		fileLoc = null;
		bankID = -1;
		accountID = -1;
		tm = null;
	}
	
	public static void newSetup(int bank, int account, String loc) {
		bankID = bank;
		accountID = account;
		fileLoc = loc;
		
		switch (bank) {
		case 0:
			importANZ();
		}
		
	}

	private static void importANZ() {
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {

            br = new BufferedReader(new FileReader(fileLoc));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] newImport = line.split(cvsSplitBy);
                Calendar cal = checkDate(newImport[0]);
                double amount = Double.parseDouble(newImport[1].replace("\"", ""));
                System.out.println(newImport[0] + ", " + newImport[1] + ", " + newImport[2] + ", " + newImport[3]);
                tm = TransactionManager.getInstance();
                tm.addTransaction(amount, newImport[2], 0, cal, bankID, accountID);
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
	
		private static Calendar checkDate(String dateString) {
			String[] dateCheck = dateString.split("/");
			
			int day = Integer.parseInt(dateCheck[0]);
			
			int month = Integer.parseInt(dateCheck[1]);
			
			int year = Integer.parseInt(dateCheck[2]);
			
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, day);
			return cal;
		}

/*		private void InitialiseDates() throws ParseException{
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
	*/
}
