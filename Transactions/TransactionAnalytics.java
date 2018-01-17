package Transactions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransactionAnalytics extends TransactionManager{

	private static ArrayList<ArrayList<Double>> accountOffsets;
	private static ArrayList<ArrayList<Double>> accountTotal;
	private static ArrayList<Double> bankTotal;
	private static ArrayList<Double> categoryTotal;
	private static ArrayList<ArrayList<Double>> categoryWeeklyTotal;
	private static ArrayList<Date> mondayDates;
	private static ArrayList<Double> weeklyTotal;

	/**
	 * Performs an analysis on all {@link Transactions.Transaction} in 
	 * {@link Transactions.TransactionManager#accounts}, calculating the weekly totals
	 * ({@link weeklyTotalUpdate}), category totals ({@link updateCategory}),
	 * bank totals ({@link updateBank}), and account totals 
	 * ({@link updateAccount})
	 */
	public static void analyseData() {
		tm = TransactionManager.getInstance();
		for(int i = 0; i < transactions.size(); i++) 
		{
			Transaction t = transactions.get(i);
			int account = t.getAccountID();
			int bank = t.getBankID();
			int category = t.getCategory();
			Calendar cal = t.getCalendar();
			double amount = t.getValueTransacted();

			int transactionWeek = cal.get(Calendar.WEEK_OF_YEAR) - 1;

			/** category totals update  */
			updateCategory(category, amount, transactionWeek);

			/** bankTotal update  */
			updateBank(bank, amount);

			/** accountTotal update  */
			updateAccount(account, bank, amount);

			/** weekly total update */
			weeklyTotalUpdate(amount, transactionWeek);
		}
	}

	/**
	 * Initialises the first day of week for the current year, listing in mondayDates
	 */
	private static void createMondayDates() {
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

		mondayDates = new ArrayList<>();

		while(!scal.equals(ecal)){
			scal.add(Calendar.DATE, 1);
			if(scal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
				mondayDates.add(scal.getTime());
			}
		}
	}

	/**
	 * Getter for the total of the specified account.
	 * @param bank Bank index in which the account is located
	 * @param account Accounts index
	 * @return The selected accounts current total
	 */
	public static String getAccountTotal(int bank, int account) {
		//account total
		double amount = accountTotal.get(bank).get(account);
		System.out.println(amount);
		//add offset
		amount = amount + accountOffsets.get(bank).get(account);
		System.out.println(amount);
		String strAmount = Double.toString(round(amount, 2));
		return strAmount;
	}

	/**
	 * Getter for a specified category total expenditure
	 * @param category Index of the category
	 * @return Categories total expenditure
	 */
	public static String getCategoryTotal(int category) {
		return Double.toString(Math.abs(round(categoryTotal.get(category), 2)));
	}

	
	/**
	 * Congregates the weekly total and category weekly total for printable use in 
	 * {@link mainGUI.MainGUI}, including the weeks date.
	 * @return String array of weekly totals
	 */
	public static String[][] getWeeklyTotals() {
		String[][] result;
		createMondayDates();
		int numberOfWeeks = mondayDates.size();
		result = new String[numberOfWeeks][categories.size() + 2];
		for(int i = 0; i < numberOfWeeks; i++) {
			result[i][0] = new SimpleDateFormat("dd/MM/yyyy").format(mondayDates.get(i));
			for(int j = 0; j < categories.size(); j++) {
				result[i][j+1] = "$" + Double.toString(round(categoryWeeklyTotal.get(i).get(j), 2));
			}
			result[i][categories.size() + 1] = "$" + Double.toString(round(weeklyTotal.get(i), 2));
		}
		return result;
	}

	/**
	 * Getter for JTable column headings in {@link mainGUI.MainGUI} containing categories
	 * date and total.
	 * @return JTable column headings
	 */
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

	/**
	 * Rounds a given value to the given decimal place
	 * @param value Value to be rounded
	 * @param places Decimal place to be rounded to
	 * @return Rounded value
	 */
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	/**
	 * Initialise variables within the class
	 */
	public static void setupAnalytics() {

		categoryWeeklyTotal = new ArrayList<ArrayList<Double>>();
		categoryTotal = new ArrayList<Double>();
		weeklyTotal = new ArrayList<Double>();

		accountOffsets = new ArrayList<ArrayList<Double>>();
		accountOffsets.add(new ArrayList<Double>());

		/** Initialize categoryTotal*/
		for(int i = 0; i < categories.size(); i++) {
			categoryTotal.add((double) 0);
		}

		/** Initialize categoryWeeklyTotal and weeklyTotal*/
		//Calculate the number of weeks in this year
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int weeksInYear = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
		for(int i = 0; i < weeksInYear+1; i++) {

			//Add week
			weeklyTotal.add((double) 0);

			//Add week of categories
			categoryWeeklyTotal.add(new ArrayList<Double>());
			for(int j = 0; j < categories.size(); j++) {

				//Add category in weekly total
				categoryWeeklyTotal.get(i).add((double) 0);
			}
		}

		/** Initialize bankTotal and accountTotal*/
		bankTotal = new ArrayList<Double>();
		accountTotal = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < banks.size(); i++) {
			bankTotal.add((double) 0);
			accountTotal.add(new ArrayList<Double>());
			accountOffsets.add(new ArrayList<Double>());
			for(int j = 0; j < accounts.get(i).size(); j++) {
				accountTotal.get(i).add((double) 0);
				accountOffsets.get(i).add((double) 0);
			}
		}		
	}

	/**
	 * Update a bank accounts current value.
	 * @param account Account index
	 * @param bank Bank index
	 * @param amount Value to be updated
	 */
	private static void updateAccount(int account, int bank, double amount) {
		double old = accountTotal.get(bank).get(account);
		accountTotal.get(bank).set(account, old + amount);
	}

	
	/**
	 * Update a banks current value
	 * @param bank Bank index
	 * @param amount Value to be updated
	 */
	private static void updateBank(int bank, double amount) {
		double old = bankTotal.get(bank);
		bankTotal.set(bank, old + amount);
	}

	private static void updateCategory(int category, double amount, int transactionWeek) {
		double oldTotal = categoryTotal.get(category);
		double oldWeekTotal = categoryWeeklyTotal.get(transactionWeek).get(category);

		/** Set category total*/
		categoryTotal.set(category, oldTotal + amount);

		/** Set category weekly total*/
		categoryWeeklyTotal.get(transactionWeek).set(category, oldWeekTotal + amount);
	}

	/**
	 * Updates the weekly totals stored in {@link weeklyTotal}
	 * @param amount The transacted amount to be updated in weekly totals
	 * @param transactionWeek The week in which the transaction took place
	 */
	private static void weeklyTotalUpdate(double amount, int transactionWeek) {
		double oldWeekTotal = weeklyTotal.get(transactionWeek);
		weeklyTotal.set(transactionWeek, oldWeekTotal + amount);
	}

	public TransactionAnalytics()
	{
		setupAnalytics();
	}

	public static void updateInitialBalance(int bankID, int accountID, String amount) {
		double accountBalance = Double.parseDouble(amount);
		double oldBalance = accountTotal.get(bankID).get(accountID);
		accountOffsets.get(bankID).set(accountID, accountBalance-oldBalance);
	}
}
