/**
 * @author Matthew
 *
 */
package Import_Export;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Transactions.Transaction;
import Transactions.TransactionManager;


@SuppressWarnings("unused")
public class UserDataExport {
	
	private Document doc;
	private String fileDir;
	private String fileLoc;
	private double numTrans;
	private TransactionManager tm;
	
	public UserDataExport() {
		setup();
		export();
	}
	
	private Element createElement(String name, String data) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(data));
        return element;		
	}

	private Element createTransactionElement(int day, int month, int year, int bank, int account, int category,
			double transaction, String description, double amount, boolean internal) {
		
				// Single Transaction element
		         Element tran = doc.createElement("transaction");
		         
		         
		         //Date element
		         tran.appendChild(createElement("transactionID", Double.toString(transaction)));
		         Element date = doc.createElement("date");
		         tran.appendChild(date);
		         date.appendChild(createElement("day"  , Integer.toString(day)));
		         date.appendChild(createElement("month", Integer.toString(month)));
		         date.appendChild(createElement("year" , Integer.toString(year)));
		         
		         tran.appendChild(createElement("bankID"     , Integer.toString(bank)));
		         tran.appendChild(createElement("accountID"  , Integer.toString(account)));
		         tran.appendChild(createElement("categoryID" , Integer.toString(category)));
		         tran.appendChild(createElement("amount"     , Double.toString(amount)));
		         tran.appendChild(createElement("internal"   , Boolean.toString(internal)));
		         tran.appendChild(createElement("description", description));
		         return tran;
	}
	
	private void export() {
		 try {
	         DocumentBuilderFactory dbFactory =
	         DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         doc = dBuilder.newDocument();
	         
	         // root element
	         Element rootElement = doc.createElement("programdata");
	         doc.appendChild(rootElement);
	         
	         //User data
	         Element userData = doc.createElement("user_information");
	         rootElement.appendChild(userData);
	         
	         // UserName element
	         String name = TransactionManager.getUserName();
	         if (!name.isEmpty() && name != null) {
		         userData.appendChild(createElement("username", name));
	         }
	         
	         //User categories
	         Element categories = doc.createElement("categories");
	         userData.appendChild(categories);
	         int numCategories = TransactionManager.getNumberOfCategories();
	         for(int i = 0; i < numCategories; i++) {
	        	 categories.appendChild(createElement("category", TransactionManager.getCategory(i)));
	         }
	         
	         //User Banks
	         Element banks = doc.createElement("banks");
	         userData.appendChild(banks);
	         int numBanks = TransactionManager.getNumberOfBanks();
	         for(int i = 0; i < numBanks; i++) {
		         Element bank = doc.createElement("bank");
		         bank.setAttribute("name", TransactionManager.getBankName(i));
		         banks.appendChild(bank);
		         
	        	 String[] accounts = TransactionManager.getAccounts(i);
	        	 int numAccounts = accounts.length;
	        	 for(int j = 0; j < numAccounts; j++) {
	        		 bank.appendChild(createElement("account", accounts[j]));
	        	 }
	         }
	         
	         // Transactions element
	         Element transactions = doc.createElement("transactions");
	         rootElement.appendChild(transactions);
	         
	         for(int i = 0; i < numTrans; i++) {
	        	Transaction t = TransactionManager.getTransaction(i);
	        	int day = t.getDay();
				int month = t.getMonth();
				int year = t.getYear();
				int bank = t.getBankID();
				int account = t.getAccountID();
				int category = t.getCategory();
				String description = t.getDescription();
				double amount = t.getValueTransacted();
				double transactionID = t.getTransactionNumber();
				boolean internal = t.isInternal();
				Element transaction = createTransactionElement(day, month, year, bank, account, category,
						transactionID, description, amount, internal);
				transactions.appendChild(transaction);
	         }

	         //check if directory and file exists
	         File dir = new File(fileDir);
	         dir.mkdir();
	         
	         
	         // write the content into xml file
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         DOMSource source = new DOMSource(doc);
	         StreamResult result = new StreamResult(new File(fileLoc));
	         transformer.transform(source, result);
	         
	         // Output to console for testing
	         //StreamResult consoleResult = new StreamResult(System.out);
	         //transformer.transform(source, consoleResult);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	
	private void setup() {
		fileLoc = "C:/Accounting Program/Data.xml";
		fileDir = "C:/Accounting Program";
		tm = TransactionManager.getInstance();
		numTrans = tm.getNumberOfTransactions();
	}
}
