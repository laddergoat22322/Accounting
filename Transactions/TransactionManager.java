package Transactions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransactionManager {
	
	private static TransactionManager tm;
	private static ArrayList<Transaction> transactions;
	private static ArrayList<String> categories;
	private static ArrayList<String> banks;
	private static ArrayList<ArrayList<String>> accounts;
	private static String uName;
	public enum TransactionAttribute {
		DATE,
		AMOUNT,
		BANK_ID,
		ACCOUNT_ID,
		CATEGORY_ID,
		TRANSACTION_ID,
		INTERNAL,
		NEWIMPORT,
		DESCRIPTION;
	}
	
	public synchronized static TransactionManager getInstance() {
		if (tm == null) {
			tm = new TransactionManager();
			setup();
		}
		return tm;
	}
	
	private static void setup() {
		transactions = new ArrayList<Transaction>();
		categories = new ArrayList<String>();
		banks = new ArrayList<String>();
		accounts = new ArrayList<ArrayList<String>>();
		accounts.add(new ArrayList<String>());
		
//		addCategory("Uncategorized");
//		addCategory("Food");
//		addCategory("Car");
//		addCategory("Home");
//		addCategory("Miscellaneous");
//		addCategory("Going Away");
		
//		addBank("ANZ");
//		addBank("Commonwealth");
		
//		addAccount(0, "Spendings");
//		addAccount(0, "Low Interest");
//		addAccount(0, "High Interest");
//		addAccount(0, "Credit Card");
//		addAccount(1, "Spendings");
//		addAccount(1, "Low Interest");
	}

	public String getUserName(){
		return TransactionManager.uName;
	}
	
	public void setUserName(String name){
		TransactionManager.uName = name;
	}

	public boolean addTransaction(double amount, String description, int category,
			Calendar date, int bank, int account) {
		
		if(category < 0 || category >= categories.size()) {
			return false;
		}
		
		double transactionNumber = transactions.size();
		Transaction newTrans = new Transaction(transactionNumber, amount, description, category, date, bank, account);
		transactions.add(newTrans);
		return true;
	}
	
	public String getBankName(int bankID) {
		if (bankID < 0 || bankID > banks.size()) {
			return null;
		}
		return banks.get(bankID);
	}
	
	public static boolean addCategory(String category) {
		//check category doesn't exist already
		for(int i = 0; i < categories.size(); i++) {
			if(category.equals(categories.get(i))) {
				return false;
			}
		}
		categories.add(category);
		return true;
	}
	
	public String[] getIndividualTransactionHeader() {
		String[] exportedCategories = new String[4];
		exportedCategories[0] = "Date";
		exportedCategories[1] = "Category";
		exportedCategories[2] = "Description";
		exportedCategories[3] = "Amount";
		return exportedCategories;
	}
	
	public String[] getCategories() {
		return categories.toArray(new String[categories.size()]);
	}
	
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
	
	public String[][] getNewTransactions() {
		int totalTransactions = transactions.size();
		String[][] exportedTransactions = new String[totalTransactions][5];
		for(int i = 0; i < totalTransactions; i++) {
			if (transactions.get(i).isNewImport()) {
				Date tempDate = transactions.get(i).getDate();
				exportedTransactions[i][0] = new SimpleDateFormat("dd/MM/yyyy").format(tempDate);
				exportedTransactions[i][1] = categories.get(transactions.get(i).getCategory());
				exportedTransactions[i][2] = transactions.get(i).getDescription();
				exportedTransactions[i][3] = String.valueOf(transactions.get(i).getAmount());
			}
		}
		return exportedTransactions;
	}

	public String[] getAllBanks() {
		int numBanks = banks.size();
		String[] b = new String[numBanks];
		for (int i = 0; i < numBanks; i++) {
			b[i] = banks.get(i);
		}
		return b;
	}

	public static void addAccount(int bankID, String accountName) {
		if (accounts.size() < 1) {
			accounts.add(new ArrayList<String>());
		}
		if (accounts.size() <= bankID) {
			accounts.add(new ArrayList<String>());
		}
		accounts.get(bankID).add(accountName);
	}

	public String[] getAccounts(int bankID) {
		int numAccounts = accounts.get(bankID).size();
		String[] result = new String[numAccounts];
		for (int i = 0; i < numAccounts; i++) {
			result[i] = accounts.get(bankID).get(i);
		}
		return result;
	}
	
	public double getNumberOfTransactions() {
		return transactions.size();
	}
	
	public int getNumberOfCategories() {
		return categories.size();
	}
	
	public Transaction getTransaction(int transactionIndex) {
		return transactions.get(transactionIndex);
	}

	public int getCategoryByIndex(String cell) {
		for (int i = 0; i < categories.size(); i++) {
			if (cell.equals(categories.get(i))) {
				return i;
			}
		}
		return 0;
	}
	
	public String getCategory(int index) {
		return categories.get(index);
		
	}

	public Double[] getNewTransactionIndexes() {
		int totalTransactions = transactions.size();
		ArrayList<Double> result = new ArrayList<Double>();
		for(int i = 0; i < totalTransactions; i++) {
			if (transactions.get(i).isNewImport()) {
				result.add(transactions.get(i).getTransactionNumber());
			}
		}
		return result.toArray(new Double[result.size()]);
	}

	public void setTransaction(TransactionAttribute attr, double accountIndex, Object value) {
		switch(attr) {
		case ACCOUNT_ID :
			transactions.get((int) accountIndex).setCategory((int) value);
			break;
			
		case AMOUNT:
			break;
			
		case BANK_ID:
			break;
			
		case CATEGORY_ID:
			break;
			
		case DATE:
			break;
			
		case DESCRIPTION:
			break;
			
		case INTERNAL:
			break;
			
		case NEWIMPORT:
			break;
			
		case TRANSACTION_ID:
			break;
			
		default:
			break;
		}
	}

	public int getNumberOfBanks() {
		return banks.size();
	}

	public static void addBank(String bankName) {
		banks.add(bankName);
	}

}