/**
 * The Transaction class containing the information of a single transaction.
 * @author Matthew Janssen
 */

package Transactions;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
	private String description;
	private boolean internal;  //TODO
	private Calendar date;
	private int bankID;        //TODO
	private int bankNumberID;  //TODO
	private int categoryID;
	private double amount = 0;
	
	public Transaction(double amount, String description, int category, Calendar date, int bank) {
		this.amount = amount;
		this.description = description;
		this.categoryID = category;
		this.date = date;
		this.internal = false;
		this.setBankID(bank);
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

	public void setBankID(int bankID) {
		this.bankID = bankID;
	}

	public int getBankNumberID() {
		return bankNumberID;
	}

	public void setBankNumberID(int bankNumberID) {
		this.bankNumberID = bankNumberID;
	}
}
