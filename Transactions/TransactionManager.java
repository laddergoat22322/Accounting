package Transactions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
		
		Date now = new Date();
		//Date test = now;
		//test.setTime(time);
		addTransaction(10, "test", 0, now);
		addTransaction(11, "test", 1, now);
		addTransaction(12, "test", 2, now);
		System.out.print(new SimpleDateFormat("dd/MM/yyyy").format(allTransactions.get(0).getDate()));
		
		System.out.print(allCategories.get(allTransactions.get(0).getCategory()));
		System.out.print(allTransactions.get(0).getDescription());
		System.out.print(String.valueOf(allTransactions.get(0).getAmount()));
	}
	
	public void addTransaction(double amount, String description, int category, Date date) {
		Transaction newTrans = new Transaction(amount, description, category, date);
		allTransactions.add(newTrans);
	}
	
	public void addCategory(String category) {
		allCategories.add(category);
	}
	
	public String[] exportTransactionsHeaderInformation() {
		String[] exportedCategories = new String[4];
		exportedCategories[0] = "Date";
		exportedCategories[1] = "Category";
		exportedCategories[2] = "Description";
		exportedCategories[3] = "Amount";
		return exportedCategories;
		
	}
	
	public String[][] exportTransactionsIndividual() {
		int totalTransactions = allTransactions.size();
		String[][] exportedTransactions = new String[totalTransactions][5];
		for(int i = 0; i < totalTransactions; i++) {
			exportedTransactions[i][0] = new SimpleDateFormat("dd/MM/yyyy").format(allTransactions.get(i).getDate());
			exportedTransactions[i][1] = allCategories.get(allTransactions.get(i).getCategory());
			exportedTransactions[i][2] = allTransactions.get(i).getDescription();
			exportedTransactions[i][3] = String.valueOf(allTransactions.get(i).getAmount());
		}
		return exportedTransactions;
	}
}