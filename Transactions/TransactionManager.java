package Transactions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class TransactionManager {
	
	protected static ArrayList<ArrayList<String>> accounts;
	protected static ArrayList<String> banks;
	protected static ArrayList<String> categories;
	protected static TransactionAnalytics ta;
	protected static ArrayList<Transaction> tempTransactions;
	protected static TransactionManager tm;
	protected static ArrayList<Transaction> transactions;
	private static String uName;
	
	
	/** 
	  * Add a new bank account to a bank in {@link #accounts}.
	  * 
	  * @param bankID Bank ID that a new account is being added to
	  * @param accountName New bank account name
	  */
	public static void addAccount(int bankID, String accountName) {
		if (accounts.size() < 1) accounts.add(new ArrayList<String>());
		if (accounts.size() <= bankID) 	accounts.add(new ArrayList<String>());
		accounts.get(bankID).add(accountName);
	}
	
	public static void updateAccount(int bankID, int accountID, String accountName) {
		accounts.get(bankID).set(accountID, accountName);
	}
	
	
	/** 
	  * Add a new bank to {@link #banks}.
	  * 
	  * @param bankName New bank's name
	  * @return True if the bank's name does not already exist
	  */
	public static boolean addBank(String bankName) {
		
		/** check the bank name does not exist*/
		for (int i = 0; i < banks.size(); i++) {
			if (banks.get(i).equals(bankName)) {
				return false;
			}
		}
		banks.add(bankName);
		return true;
	}
	
	
	/**
	 * Add a new category to {@link #categories}
	 * @param category new category name
	 * @return True if the category does not exist
	 */
	public static boolean addCategory(String category) {
		
		/**check category doesn't exist already*/
		for(int i = 0; i < categories.size(); i++) {
			if(category.equals(categories.get(i))) {
				return false;
			}
		}
		categories.add(category);
		return true;
	}
	
	
	public static void addTempTransaction(Transaction t) {
		Calendar cal = t.getCalendar();
		int bank = t.getBankID();
		int account = t.getAccountID();
		int category = t.getCategory();
		String description = t.getDescription();
		double amount = t.getValueTransacted();
		boolean internal = t.isInternal();
		
		addTransaction(amount, description, category, cal, bank, account, internal);
	}

	/**
	 * Add a transaction to {@link #transactions}
	 * 
	 * @param amount transacted value
	 * @param description A description of the {@link Transactions.Transaction}
	 * @param category Category index for {@link #categories}
	 * @param date {@link java.util.Calendar} with {@link java.util.Date} of {@link Transactions.Transaction}
	 * @param bank Bank index for {@link #banks}
	 * @param account account index for {@link #accounts}
	 * @param newImport True if the {@link Transactions.Transaction} is uncategorized, else false
	 * @param internal True if the {@link Transactions.Transaction} is an internal transfer between
	 * 		  personal accounts, else false
	 * @return True if the bank, category and account index exist within {@link #banks},
	 * {@link #categories}, {@link #accounts} respectively
	 */
	public static boolean addTransaction(double amount, String description, int category,
			Calendar date, int bank, int account, boolean internal) {
		
		if(category < 0 || category >= categories.size()) return false;
		if(bank < 0 || bank >= banks.size()) return false;
		
		double transactionNumber = transactions.size();
		Transaction newTrans = new Transaction(transactionNumber, amount, description, 
				category, date, bank, account, internal);
		transactions.add(newTrans);
		return true;
	}
	
	
	public static void analyseData() {
		TransactionAnalytics.setupAnalytics();
		TransactionAnalytics.analyseData();
	}
	
	
	public static boolean checkDuplicate(double amount, String description, Calendar cal, int bankID, int accountID) {
		for(int i = 0; i < transactions.size(); i++) {
			Calendar localDate = transactions.get(i).getCalendar();
			if(localDate.DAY_OF_YEAR == cal.DAY_OF_YEAR) {
				double localAmount = transactions.get(i).getValueTransacted();
				String localDescription = transactions.get(i).getDescription();
				if (localAmount == amount && localDescription.equals(description)) {
					return true;
				}
			}
		}
		return false;		
	}
	
	
	/**
	 * Returns a list of accounts within the bank from the given bankID
	 * 
	 * @param bankID {@link #banks} index
	 * @return Array of accounts within bank
	 */
	public static String[] getAccounts(int bankID) {
		int numAccounts = accounts.get(bankID).size();
		String[] result = new String[numAccounts];
		for (int i = 0; i < numAccounts; i++) {
			result[i] = accounts.get(bankID).get(i);
		}
		return result;
	}
	
	public static String getAccountTotal(int bank, int account) {
		return TransactionAnalytics.getAccountTotal(bank, account);
	}
	
	
	/**
	 * Lists the names of all banks
	 * 
	 * @return Array of bank names
	 */
	public static String[] getAllBanks() {
		int numBanks = banks.size();
		String[] b = new String[numBanks];
		for (int i = 0; i < numBanks; i++) {
			b[i] = banks.get(i);
		}
		return b;
	}
	
	/**
	 * Get the name of a given bankID
	 * @param bankID
	 * @return bank's name
	 */
	public static String getBankName(int bankID) {
		if (bankID < 0 || bankID > banks.size()) {
			return null;
		}
		return banks.get(bankID);
	}

	/**
	 * Creates and returns an array of category names
	 * @return All categories
	 */
	public static String[] getCategories() {
		return categories.toArray(new String[categories.size()]);
	}

	/**
	 * Get a category name by it's index
	 * @param index Category index
	 * @return
	 */
	public static String getCategory(int index) {
		return categories.get(index);
		
	}

	/**
	 * Get a categories index
	 * @param category Name of category
	 * @return Category index
	 */
	public static int getCategoryByIndex(String category) {
		for (int i = 0; i < categories.size(); i++) {
			if (category.equals(categories.get(i))) {
				return i;
			}
		}
		return 0;
	}
	
	public static String getCategoryTotal(int category) {
		return TransactionAnalytics.getCategoryTotal(category);
	}
	
	public static String[] getIndividualTransactionHeader() {
		String[] exportedCategories = new String[4];
		exportedCategories[0] = "Date";
		exportedCategories[1] = "Category";
		exportedCategories[2] = "Description";
		exportedCategories[3] = "Amount";
		return exportedCategories;
	}

	/**
	 * Initializes the class {@link Transactions.TransactionManager} if not already
	 * Initialized, keeping the usage of the class synchronized
	 * @return Synchronized class {@linkplain Transactions.TransactionManager}
	 */
	public synchronized static TransactionManager getInstance() {
		if (tm == null) {
			setup();
		}
		return tm;
	}
	
	public static String[][] getNewTransactions() {
		int totalTransactions = tempTransactions.size();
		String[][] exportedTransactions = new String[totalTransactions][5];
		for(int i = 0; i < totalTransactions; i++) {
			Date tempDate = tempTransactions.get(i).getDate();
			exportedTransactions[i][0] = new SimpleDateFormat("dd/MM/yyyy").format(tempDate);
			exportedTransactions[i][1] = categories.get(0);
			exportedTransactions[i][2] = tempTransactions.get(i).getDescription();
			exportedTransactions[i][3] = String.valueOf(tempTransactions.get(i).getValueTransacted());
		}
		return exportedTransactions;
	}

	public static int getNumberOfBanks() {
		return banks.size();
	}
	
	public static int getNumberOfCategories() {
		return categories.size();
	}

	public static Transaction getTempTransaction(int transactionIndex) {
		return tempTransactions.get(transactionIndex);
	}


	public static Transaction getTransaction(int transactionIndex) {
		return transactions.get(transactionIndex);
	}


	public static String getUserName(){
		return TransactionManager.uName;
	}


	public static String[][] getWeeklyTotals() {
		return TransactionAnalytics.getWeeklyTotals();
	}


	public static String[] getWeeklyTotalsHeader() {
		return TransactionAnalytics.getWeeklyTotalsHeader();
	}


	/**
	 * First time setup for {@link Transactions.TransactionManager} that initialises
	 * field variables.
	 */
	private static void setup() {
		tm = new TransactionManager();
		transactions = new ArrayList<Transaction>();
		tempTransactions = new ArrayList<Transaction>();
		categories = new ArrayList<String>();
		banks = new ArrayList<String>();
		accounts = new ArrayList<ArrayList<String>>();
		accounts.add(new ArrayList<String>());
		ta = new TransactionAnalytics();
	}


	public static void setUserName(String name){
		TransactionManager.uName = name;
	}


	public boolean addTempTransaction(double amount, String description, int category,
			Calendar cal, int bank, int account, boolean internal) {
		
		if(category < 0 || category >= categories.size()) return false;
		if(bank < 0 || bank >= banks.size()) return false;
		
		double transactionNumber = 0; //TODO
		Transaction newTrans = new Transaction(transactionNumber, amount, description, 
				category, cal, bank, account, internal);
		tempTransactions.add(newTrans);
		return true;
	}

	/**
	 * Creates the header for {@link mainGUI.MainGUI} with all catgeories, date and total
	 * @return Category header array
	 */
	public String[] getCategoriesHeader() {
		int arraySize = categories.size() + 2;
		String[] exportedCategories = new String[arraySize];
		exportedCategories[0] = "Date";
		for (int i = 0; i < categories.size(); i++) {
			exportedCategories[i+1] = categories.get(i);
		}
		exportedCategories[arraySize-1] = "Total";
		return exportedCategories;
	}


	public double getNumberOfTransactions() {
		return transactions.size();
	}

	public static void updateAccountTotal(int bankID, int accountID, String amount) {
		TransactionAnalytics.updateInitialBalance(bankID, accountID, amount);
		
	}
}