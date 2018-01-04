package Transactions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransactionAnalytics extends TransactionManager{
	
	private static ArrayList<Double> categoryTotal;
	private static ArrayList<ArrayList<Double>> categoryWeeklyTotal;
	private static ArrayList<Double> bankTotal;
	private static ArrayList<ArrayList<Double>> accountTotal;
	
	public TransactionAnalytics() 
	{
		setupAnalytics();
		analyseData();
	}

	public void setupAnalytics() {		
		/** Initialise categoryTotal*/
		categoryTotal = new ArrayList<Double>();
		for(int i = 0; i < categories.size(); i++) {
			categoryTotal.add((double) 0);
		}
		
		/** Initialise categoryWeeklyTotal*/
		categoryWeeklyTotal = new ArrayList<ArrayList<Double>>();
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int weeksInYear = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
		for(int i = 0; i < weeksInYear+1; i++) {
			categoryWeeklyTotal.add(new ArrayList<Double>());
			for(int j = 0; j < categories.size(); j++) {
				categoryWeeklyTotal.get(i).add((double) 0);
			}
		}
		
		/** Initialise bankTotal and accountTotal*/
		bankTotal = new ArrayList<Double>();
		accountTotal = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < banks.size(); i++) {
			bankTotal.add((double) 0);
			accountTotal.add(new ArrayList<Double>());
			for(int j = 0; j < accounts.get(i).size(); j++) {
				accountTotal.get(i).add((double) 0);
			}
		}
		analyseData();
	}

	public static void analyseData() {
		tm = TransactionManager.getInstance();
		for(int i = 0; i < transactions.size(); i++) 
		{
			Transaction t = transactions.get(i);
			int account = t.getAccountID();
			int bank = t.getBankID();
			int category = t.getCategory();
			Calendar cal = t.getCalendar();
			boolean internal = t.isInternal();
			boolean newImport = t.isNewImport();
			double transID = t.getTransactionNumber();
			double amount = t.getValueTransacted();
			
			/** category totals update  */
			updateCategory(category, amount, cal);
			
			/** bankTotal update  */
			updateBank(bank, amount);
			
			/** accountTotal update  */
			updateAccount(account, bank, amount);
		}
	}

	private static void updateAccount(int account, int bank, double amount) {
		double old = accountTotal.get(bank).get(account);
		accountTotal.get(bank).set(account, old + amount);
	}

	private static void updateBank(int bank, double amount) {
		double old = bankTotal.get(bank);
		bankTotal.set(bank, old + amount);
	}

	private static void updateCategory(int category, double amount, Calendar cal) {
		
		/** Set category total*/
		double oldTotal = categoryTotal.get(category);
		categoryTotal.set(category, oldTotal + amount);
		
		/** Set category weekly total*/
		int transactionWeek = cal.get(Calendar.WEEK_OF_YEAR) - 1;
		double oldWeekTotal = categoryWeeklyTotal.get(transactionWeek).get(category);
		categoryWeeklyTotal.get(transactionWeek).set(category, oldWeekTotal + amount);
		//System.out.println(category + ", " + transactionWeek + ", " +categoryWeeklyTotal.get(transactionWeek).get(category));
	}

	public static String getCategoryTotal(int category) {
		return Double.toString(Math.abs(categoryTotal.get(category)));
	}

	public static String getAccountTotal(int bank, int account) {
		return Double.toString(accountTotal.get(bank).get(account));
	}

	public static String[][] getWeeklyTotals() {
		 String[][] result;
		 Calendar cal = Calendar.getInstance();
		 cal.setFirstDayOfWeek(Calendar.MONDAY);
	     String start = "31/12/2017";
	     String end = "31/12/2018";
	     SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
	     Calendar scal=Calendar.getInstance();
	     Calendar ecal=Calendar.getInstance();
	     try {
			 scal.setTime(dateFormat.parse(start));
		     ecal.setTime(dateFormat.parse(end));
		} catch (ParseException e) {
			e.printStackTrace();
		}

	     ArrayList<Date> mondayDates=new ArrayList<>();

	     while(!scal.equals(ecal)){
	         scal.add(Calendar.DATE, 1);
	         if(scal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
	             mondayDates.add(scal.getTime());
	         }
	     }
	     int numberOfWeeks = mondayDates.size();
	     result = new String[numberOfWeeks][categories.size() + 2];
	     for(int i = 0; i < numberOfWeeks; i++) {
	    	 result[i][0] = new SimpleDateFormat("dd/MM/yyyy").format(mondayDates.get(i));
	    	 for(int j = 0; j < categories.size(); j++) {
	    		 result[i][j+1] = "$" + Double.toString(categoryWeeklyTotal.get(i).get(j));
	    	 }
	     }
		return result;
	}
	
	public static String[] getWeeklyTotalsHeader() {
		int headerLength = categories.size() + 2;
		String[] result = new String[headerLength];
		result[0] = "Date";
		for(int i = 0; i < result.length-2; i++){
			result[i+1] = categories.get(i);
		}
		result[headerLength-1] = "Total";
		return result;
	}
}
