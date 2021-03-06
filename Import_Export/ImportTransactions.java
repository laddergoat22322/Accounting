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
	private int accountID;
	private int bankID;
	private String fileLoc;
	private TransactionManager tm;
	public ImportTransactions(int bank, int account, String loc) {
		fileLoc = loc;
		this.bankID = bank;
		accountID = account;
		tm = TransactionManager.getInstance();
		importFile();
	}

	private void checkANZTransaction(String[] newImport) {
		Calendar cal = checkDate(newImport[0]);
		if(cal.get(Calendar.YEAR) == 2018) {
	        double amount = Double.parseDouble(newImport[1].replace("\"", ""));
	        if (!isDuplicateTransaction(amount, newImport[2], cal, bankID, accountID)){
	        	tm.addTempTransaction(amount, newImport[2], 0, cal, bankID, accountID, false);
	        }
		}
	}

	private void checkCommonwealthTransaction(String[] newImport) {
		Calendar cal = checkDate(newImport[0]);
		if(cal.get(Calendar.YEAR) == 2018) {
	        double amount = Double.parseDouble(newImport[1].replace("\"", ""));
	        if (!isDuplicateTransaction(amount, newImport[2], cal, bankID, accountID)){
	        	tm.addTempTransaction(amount, newImport[2], 0, cal, bankID, accountID, false);
	        }
		}
	}

	private Calendar checkDate(String dateString) {
		String[] dateCheck = dateString.split("/");
		int day = Integer.parseInt(dateCheck[0]);
		
		int month = Integer.parseInt(dateCheck[1]);
		
		int year = Integer.parseInt(dateCheck[2]);
		
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.YEAR, 0);
		c1.set(Calendar.DAY_OF_YEAR, 1);
		c1.set(Calendar.MONTH, month-1);
		c1.set(Calendar.DAY_OF_MONTH, day);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(c1.getTime());
		c2.set(Calendar.YEAR, year);
		return c2;
	}

	private void checkStGeorgeTransaction(String[] newImport) {
		if(!newImport[0].equals("Date")) {
			Calendar cal = checkDate(newImport[0]);
			if(cal.get(Calendar.YEAR) == 2018) {
	            double amount = Double.parseDouble(newImport[2].replace("\"", ""));
	            if (amount == 0) {
	            	amount = Double.parseDouble(newImport[3].replace("\"", ""));
	            }
	            else {
	            	amount = amount*-1;
	            }
	            if (!isDuplicateTransaction(amount, newImport[1], cal, bankID, accountID)){
	            	tm.addTempTransaction(amount, newImport[1], 0, cal, bankID, accountID, false);
	            }
			}
		}	
		
	}
	
	private void importFile() {
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
                		if(bankID == 0) {
                			checkCommonwealthTransaction(newImport);
                		}
                		else if (bankID == 1) {
                			checkStGeorgeTransaction(newImport);
                		}
                		else if (bankID == 2) {
                			checkANZTransaction(newImport);
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
}
