package Transactions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransactionManager {
	
	private ArrayList<Transaction> allTransactions;
	private ArrayList<String> allCategories;
	
	public TransactionManager() {
		allTransactions = new ArrayList<Transaction>();
		allCategories = new ArrayList<String>();
		
		addCategory("Food");
		addCategory("Car");
		addCategory("Home");
		addCategory("Games");
		addCategory("Miscellaneous");
		addCategory("Going Away");
		
		Calendar now = Calendar.getInstance();
		//Date test = now;
		//test.setTime(time);
		addTransaction(10, "test", 0, now);
		//System.out.print(new SimpleDateFormat("dd/MM/yyyy").format(allTransactions.get(0).getTime.getDate()));
		
		
		System.out.print(allCategories.get(allTransactions.get(0).getCategory()));
		System.out.print(allTransactions.get(0).getDescription());
		System.out.print(String.valueOf(allTransactions.get(0).getAmount()));
	}
	
	public void addTransaction(double amount, String description, int category, Calendar date) {
		Transaction newTrans = new Transaction(amount, description, category, date);
		allTransactions.add(newTrans);
	}
	
	public void addCategory(String category) {
		allCategories.add(category);
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
	
	public String[][] exportAllTransactions_Individual() {
		int totalTransactions = allTransactions.size();
		String[][] exportedTransactions = new String[totalTransactions][5];
		for(int i = 0; i < totalTransactions; i++) {
			Date tempDate = allTransactions.get(i).getDate();
			exportedTransactions[i][0] = new SimpleDateFormat("dd/MM/yyyy").format(tempDate);
			exportedTransactions[i][1] = allCategories.get(allTransactions.get(i).getCategory());
			exportedTransactions[i][2] = allTransactions.get(i).getDescription();
			exportedTransactions[i][3] = String.valueOf(allTransactions.get(i).getAmount());
		}
		return exportedTransactions;
	}
}
