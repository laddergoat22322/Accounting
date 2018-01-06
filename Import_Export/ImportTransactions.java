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

public class ImportTransactions {
	private String fileLoc;
	private int bankID;
	private int accountID;
	private TransactionManager tm;
	public ImportTransactions(int bank, int account, String loc) {
		fileLoc = loc;
		bankID = bank;
		accountID = account;
		tm = TransactionManager.getInstance();
		importFile();
	}
	
	public void importFile() {
		
		switch (this.bankID) {
		case 0:
			importANZ();
		}
		
	}

	private void importANZ() {
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {

            br = new BufferedReader(new FileReader(this.fileLoc));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] newImport = line.split(cvsSplitBy);
                if (newImport.length > 0) {
                	if ( newImport[0] != null && !newImport[0].isEmpty()) {
	                    Calendar cal = checkDate(newImport[0]);
	                    double amount = Double.parseDouble(newImport[1].replace("\"", ""));
	                    if (!isDuplicateTransaction(amount, newImport[2], cal, bankID, accountID)){
	                    	tm.addTransaction(amount, newImport[2], 0, cal, bankID, accountID, true, false);
	                    }
	                    
                	}
                }
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
	
		private boolean isDuplicateTransaction(double amount, String description, Calendar cal, int bankID, int accountID) {
		
		return TransactionManager.checkDuplicate(amount, description, cal, bankID, accountID);
	}

		private Calendar checkDate(String dateString) {
			String[] dateCheck = dateString.split("/");
			
			int day = Integer.parseInt(dateCheck[0]);
			
			int month = Integer.parseInt(dateCheck[1]);
			
			int year = Integer.parseInt(dateCheck[2]);
			
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, day);
			return cal;
		}
}
