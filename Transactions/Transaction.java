/**
 * The Transaction class containing the information of a single transaction.
 * @author Matthew Janssen
 */

package Transactions;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
	private double amount = 0;
	private String description;
	private int category;
	private boolean internal;
	private Calendar date;
	private int bank;
	
	public Transaction(double amount, String description, int category, Calendar date, int bank) {
		this.amount = amount;
		this.description = description;
		this.category = category;
		this.date = date;
		this.internal = false;
		this.bank = bank;
	}
	
	public double getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public Date getDate() {
		return date.getTime();
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
}
