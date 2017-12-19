package Transactions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransactionManager {
	
	private ArrayList<Transaction> allTransactions;
	private ArrayList<String> allCategories;
	private ArrayList<String> allBanks; 
	
	public TransactionManager() {
		allTransactions = new ArrayList<Transaction>();
		allCategories = new ArrayList<String>();
		allBanks = new ArrayList<String>();
		
		addCategory("Food");
		addCategory("Car");
		addCategory("Home");
		addCategory("Games");
		addCategory("Miscellaneous");
		addCategory("Going Away");
		allBanks.add("ANZ Bank");
	}
	
	public boolean addTransaction(double amount, String description, int category, Calendar date, int bank) {
		if(category < 0 || category >= allCategories.size()) {
			return false;
		}
		Transaction newTrans = new Transaction(amount, description, category, date, bank);
		allTransactions.add(newTrans);
		return true;
	}
	
	public boolean addCategory(String category) {
		//check category doesn't exist already
		for(int i = 0; i < allCategories.size(); i++) {
			if(category.equals(allCategories.get(i))) {
				return false;
			}
		}
		allCategories.add(category);
		return true;
	}
	
	public String[] exportIndividualTransactionHeader() {
		String[] exportedCategories = new String[4];
		exportedCategories[0] = "Date";
		exportedCategories[1] = "Category";
		exportedCategories[2] = "Description";
		exportedCategories[3] = "Amount";
		return exportedCategories;
	}
	
	public String[] exportCategoriesTransactionHeader() {
		int arraySize = allCategories.size() + 2;
		String[] exportedCategories = new String[arraySize];
		exportedCategories[0] = "Date";
		for (int i = 0; i < allCategories.size(); i++) {
			exportedCategories[i+1] = allCategories.get(i);
		}
		exportedCategories[arraySize-1] = "Total";
		return exportedCategories;
	}
	
	public String[] exportCategories() {
		int arraySize = allCategories.size();
		String[] exportedCategories = new String[arraySize];
		for (int i = 0; i < allCategories.size(); i++) {
			exportedCategories[i] = allCategories.get(i);
		}
		return exportedCategories;
	}
	
	public String[][] exportAllTransactions_Individual() {
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
}
