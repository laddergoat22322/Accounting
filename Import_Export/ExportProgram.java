/**
 * @author Matthew
 *
 */
package Import_Export;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Transactions.Transaction;
import Transactions.TransactionManager;

import java.io.File;


@SuppressWarnings("unused")
public class ExportProgram {
	
	private String fileLoc;
	private String fileDir;
	private Document doc;
	private TransactionManager tm;
	private double numTrans;
	
	public ExportProgram() {
		setup();
		export();
	}
	
	private void setup() {
		fileLoc = "C:/Accounting Program/Data.xml";
		fileDir = "C:/Accounting Program";
		tm = TransactionManager.getInstance();
		numTrans = tm.getNumberOfTransactions();
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
	         String name = tm.getUserName();
	         if (!name.isEmpty() && name != null) {
		         userData.appendChild(createElement("username", name));
	         }
	         
	         //User categories
	         Element categories = doc.createElement("categories");
	         userData.appendChild(categories);
	         int numCategories = tm.getNumberOfCategories();
	         for(int i = 0; i < numCategories; i++) {
	        	 categories.appendChild(createElement("category", tm.getCategory(i)));
	         }
	         
	         //User Banks
	         Element banks = doc.createElement("banks");
	         userData.appendChild(banks);
	         int numBanks = tm.getNumberOfBanks();
	         for(int i = 0; i < numBanks; i++) {
		         Element bank = doc.createElement("bank");
		         bank.setAttribute("name", tm.getBankName(i));
		         banks.appendChild(bank);
		         
	        	 String[] accounts = tm.getAccounts(i);
	        	 int numAccounts = accounts.length;
	        	 for(int j = 0; j < numAccounts; j++) {
	        		 bank.appendChild(createElement("account", accounts[j]));
	        	 }
	         }
	         
	         // Transactions element
	         Element transactions = doc.createElement("transactions");
	         rootElement.appendChild(transactions);
	         
	         for(int i = 0; i < numTrans; i++) {
	        	Transaction t = tm.getTransaction(i);
	        	int day = t.getDay();
				int month = t.getMonth();
				int year = t.getYear();
				int bank = t.getBankID();
				int account = t.getAccountID();
				int category = t.getCategory();
				double amount = t.getAmount();
				double transactionID = t.getTransactionNumber();
				boolean internal = t.isInternal();
				boolean newImport = t.isNewImport();
				Element transaction = createTransactionElement(day, month, year, bank, account, category,
						transactionID, amount, internal, newImport);
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
	
	private Element createElement(String name, String data) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(data));
        return element;		
	}
	
	private Element createTransactionElement(int day, int month, int year, int bank, int account, int category,
			double transaction, double amount, boolean internal, boolean newImport) {
		
				// Single Transaction element
		         Element tran = doc.createElement("transaction");
		         
		         
		         //Date element
		         tran.appendChild(createElement("transactionID", Double.toString(transaction)));
		         Element date = doc.createElement("date");
		         tran.appendChild(date);
		         date.appendChild(createElement("day"  , Integer.toString(day)));
		         date.appendChild(createElement("month", Integer.toString(month)));
		         date.appendChild(createElement("year" , Integer.toString(year)));
		         
		         tran.appendChild(createElement("bankID"    , Integer.toString(bank)));
		         tran.appendChild(createElement("accountID" , Integer.toString(account)));
		         tran.appendChild(createElement("categoryID", Integer.toString(category)));
		         tran.appendChild(createElement("amount"    , Double.toString(amount)));
		         tran.appendChild(createElement("internal"  , Boolean.toString(internal)));
		         tran.appendChild(createElement("newImport" , Boolean.toString(newImport)));
		         return tran;
	}
}
