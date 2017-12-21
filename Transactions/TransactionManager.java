package Transactions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransactionManager {
	
	private static TransactionManager tm;
	private static ArrayList<Transaction> allTransactions;
	private static ArrayList<String> allCategories;
	private static ArrayList<String> allBanks;
	private static ArrayList<ArrayList<String>> allAccounts;
	
	public synchronized static TransactionManager getInstance() {
		if (tm == null) {
			tm = new TransactionManager();
			setup();
		}
		return tm;
	}
	
	private static void setup() {
		allTransactions = new ArrayList<Transaction>();
		allCategories = new ArrayList<String>();
		allBanks = new ArrayList<String>();
		allAccounts = new ArrayList<ArrayList<String>>();
		allAccounts.add(new ArrayList<String>());
		allAccounts.add(new ArrayList<String>());
		
		addCategory("Food");
		addCategory("Car");
		addCategory("Home");
		addCategory("Games");
		addCategory("Miscellaneous");
		addCategory("Going Away");
		allBanks.add("ANZ");
		allBanks.add("Commonwealth");
		allAccounts.get(0).add("Spendings");
		allAccounts.get(0).add("Low Interest");
		allAccounts.get(0).add("High Interest");
		allAccounts.get(0).add("Credit Card");
		allAccounts.get(1).add("Spending");
		allAccounts.get(1).add("Low Interest");
	}

	public boolean addTransaction(double amount, String description, int category, Calendar date, int bank, int account) {
		if(category < 0 || category >= allCategories.size()) {
			return false;
		}
		Transaction newTrans = new Transaction(amount, description, category, date, bank, account);
		allTransactions.add(newTrans);
		return true;
	}
	
	public String getBankName(int bankID) {
		if (bankID < 0 || bankID > allBanks.size()) {
			return null;
		}
		return allBanks.get(bankID);
	}
	
	public static boolean addCategory(String category) {
		//check category doesn't exist already
		for(int i = 0; i < allCategories.size(); i++) {
			if(category.equals(allCategories.get(i))) {
				return false;
			}
		}
		allCategories.add(category);
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
	
	public String[] getCategoriesTransactionHeader() {
		int arraySize = allCategories.size() + 2;
		String[] exportedCategories = new String[arraySize];
		exportedCategories[0] = "Date";
		for (int i = 0; i < allCategories.size(); i++) {
			exportedCategories[i+1] = allCategories.get(i);
		}
		exportedCategories[arraySize-1] = "Total";
		return exportedCategories;
	}
	
	public String[] getCategories() {
		int arraySize = allCategories.size();
		String[] exportedCategories = new String[arraySize];
		for (int i = 0; i < allCategories.size(); i++) {
			exportedCategories[i] = allCategories.get(i);
		}
		return exportedCategories;
	}
	
	public String[][] getAllNewTransactions() {
		int totalTransactions = allTransactions.size();
		String[][] exportedTransactions = new String[totalTransactions][5];
		for(int i = 0; i < totalTransactions; i++) {
			Date tempDate = allTransactions.get(i).getDate();
			exportedTransactions[i][0] = new SimpleDateFormat("dd/MM/yyyy").format(tempDate);
			exportedTransactions[i][2] = allTransactions.get(i).getDescription();
			exportedTransactions[i][3] = String.valueOf(allTransactions.get(i).getAmount());
			int categoryIndex = allTransactions.get(i).getCategory();
			if (categoryIndex < 0) exportedTransactions[i][1] = "Choose...";
			else exportedTransactions[i][1] = allCategories.get(categoryIndex);
		}
		return exportedTransactions;
	}

	public String[] getAllBanks() {
		int numBanks = allBanks.size();
		String[] banks = new String[numBanks];
		for (int i = 0; i < numBanks; i++) {
			banks[i] = allBanks.get(i);
		}
		return banks;
	}


	public String[] getAccounts(int bankID) {
		int numAccounts = allAccounts.get(bankID).size();
		String[] accounts = new String[numAccounts];
		for (int i = 0; i < numAccounts; i++) {
			accounts[i] = allAccounts.get(bankID).get(i);
		}
		return accounts;
	}

}