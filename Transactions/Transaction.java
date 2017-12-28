/**
 * The Transaction class containing the information of a single transaction.
 * @author Matthew Janssen
 */

package Transactions;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
	private double transactionNumber;
	private Calendar date;
	private String description;
	private int bankID;
	private int accountID;
	private int categoryID;
	private double amount;
	private boolean internal;
	private boolean newImport;
	
	public Transaction(double transactionNumber, double amount, String description, int category, Calendar date, int bank, int accountID) {
		this.transactionNumber = transactionNumber;
		this.amount = amount;
		this.description = description;
		this.categoryID = category;
		this.date = date;
		this.internal = false;
		this.bankID = bank;
		this.accountID = accountID;
		this.newImport = true;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public int getCategory() {
		return categoryID;
	}
	
	public void setCategory(int category) {
		this.categoryID = category;
	}
	
	public Date getDate() {
		return date.getTime();
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public int getBankID() {
		return bankID;
	}

	public int getAccountID() {
		return accountID;
	}
	
	public boolean isNewImport() {
		return newImport;
	}
	
	public void setNewImport(boolean b) {
		this.newImport = b;
	}
	
	public int getDay() {
		return date.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getMonth() {
		return date.get(Calendar.MONTH) + 1;
	}
	
	public int getYear() {
		return date.get(Calendar.YEAR);
	}
	
	public double getTransactionNumber() {
		return transactionNumber;
	}
}
