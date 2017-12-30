/**
 * The Transaction class containing the information of a single transaction.
 * @author Matthew Janssen
 */

package Transactions;

import java.util.Calendar;
import java.util.Date;


/**
* Transaction Object.
* 
* <P>Various attributes of a Transaction
*  
* @author Matthew Janssen
* @version 1.0
*/
public class Transaction {
	private int accountID;
	private int bankID;
	private int categoryID;
	private Calendar date;
	private String description;
	private boolean internal;
	private boolean newImport;
	private double transactionNumber;
	private double valueTransacted;
	
	
	  /**
	  * Constructor.
	  * 
	  * @param transactionNumber Index at where the transaction is defined
	  * @param valueTransacted Transactions value Transacted, positive or negative
	  * @param description Description of the transaction
	  * @param category Index for the category's name in {@link Transactions.TransactionManager}
	  * @param date Transactions date, stored as {@link java.util.Date}
	  * @param bank Index for the Bank's name in {@link Transactions.TransactionManager}
	  * @param accountID Index for the account's name in {@link Transactions.TransactionManager}
	  * @param newImport If the transaction has been categorized
	  * @param internal If the transaction is between the personal accounts
	  */
	public Transaction(double transactionNumber, double valueTransacted, String description, 
			int category, Calendar date, int bank, int accountID, boolean newImport, boolean internal) {
		this.transactionNumber = transactionNumber;
		this.valueTransacted = valueTransacted;
		this.description = description;
		this.categoryID = category;
		this.date = date;
		this.internal = internal;
		this.bankID = bank;
		this.accountID = accountID;
		this.newImport = newImport;
		
	}
	
	
	/** 
	  * Get the {@link Transactions.Transaction} account index for {@link Transactions.TransactionManager#accounts}.
	  * 
	  * @return {@link Transactions.Transaction} account index
	  */
	public int getAccountID() {
		return accountID;
	}
	
	
	/** 
	  * Get the {@link Transactions.Transaction} bank index for {@link Transactions.TransactionManager#banks}.
	  * 
	  * @return {@link Transactions.Transaction} bank index
	  */
	public int getBankID() {
		return bankID;
	}
	
	/** 
	  * Get the {@link Transactions.Transaction} category index for {@link Transactions.TransactionManager#categories}.
	  * 
	  * @return {@link Transactions.Transaction} category index
	  */
	public int getCategory() {
		return categoryID;
	}
	
	
	/** 
	  * Get the {@link java.util.Date} of the {@link Transactions.Transaction}
	  * 
	  * @return {@link java.util.Date} of {@link Transactions.Transaction}
	  */
	public Date getDate() {
		return date.getTime();
	}
	
	  /** 
	    * Get the day in month of the {@link Transactions.Transaction}
	    * 
	    * @return Day in month of {@link Transactions.Transaction}
	    */
	  public int getDay() {
		return date.get(Calendar.DAY_OF_MONTH);
	}
	
	
	/** Return the {@link Transactions.Transaction} description. 
	 * 
	 * @return {@link Transactions.Transaction} description
	 */ 
	public String getDescription() {
		return description;
	}
	
	
	/** 
	  * Get the month in year of the {@link Transactions.Transaction}
	  * 
	  * @return Month in year of {@link Transactions.Transaction}
	  */
	public int getMonth() {
		return date.get(Calendar.MONTH) + 1;
	}
	
	
	/** 
	  * Get the {@link Transactions.Transaction}'s index for {@link Transactions.TransactionManager#transactions}.
	  * 
	  * @return {@link Transactions.Transaction}'s index
	  */
	public double getTransactionNumber() {
		return transactionNumber;
	}

	public double getValueTransacted() {
		return valueTransacted;
	}

	public int getYear() {
		return date.get(Calendar.YEAR);
	}

	public boolean isInternal() {
		return internal;
	}

	public boolean isNewImport() {
		return newImport;
	}
	
	/** 
	  * Set the {@link Transactions.Transaction} category index for {@link Transactions.TransactionManager#categories}.
	  * 
	  * @param category new {@link Transactions.Transaction} category's index in {@link Transactions.TransactionManager#categories}
	  */
	public void setCategory(int category) {
		this.categoryID = category;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	/** 
	 * Set the {@link Transactions.Transaction} description.
	 * 
	 * @param description new {@link Transactions.Transaction} description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setInternal(boolean internal) {
		this.internal = internal;
	}
	
	public void setNewImport(boolean b) {
		this.newImport = b;
	}
	
	/** 
	 * Set the value transacted.
	 * 
	 * @param valueTransacted the new {@link Transactions.Transaction} value Transacted
	 */
	public void setValueTransacted(double valueTransacted) {
		this.valueTransacted = valueTransacted;
	}
}
