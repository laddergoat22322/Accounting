package Import_Export;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import Transactions.TransactionManager;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Calendar;

public class UserDataImport {
	
	private String fileLoc;
	private TransactionManager tm;
	
	public UserDataImport() {
		this.fileLoc = "C:/Accounting Program/Data.xml";
		this.tm = TransactionManager.getInstance();
		importData();
	}

	@SuppressWarnings("static-access")
	private void importData() {
		try {

			File fXmlFile = new File(fileLoc);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			
			//Import Username
			NodeList usernameList = doc.getElementsByTagName("username");
			tm.setUserName(usernameList.item(0).getTextContent());
			
			//Import categories
			NodeList nList = doc.getElementsByTagName("category");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				tm.addCategory(nNode.getTextContent());
			}
			
			//Import Banks and accounts
			NodeList bankList = doc.getElementsByTagName("bank");
			for (int i = 0; i < bankList.getLength(); i++) {
				Node nNode = bankList.item(i);
				Element eElement = (Element) nNode;
				tm.addBank(eElement.getAttribute("name"));
				NodeList accountsList = nNode.getChildNodes();
				for (int j = 0; j < accountsList.getLength(); j++) {
					Node accountNode = accountsList.item(j);
					tm.addAccount(i, accountNode.getTextContent());
				}
			}
			
			//Import Transactions
			NodeList transactionsList = doc.getElementsByTagName("transaction");
			for (int i = 0; i < transactionsList.getLength(); i++) {
				Node nNode = transactionsList.item(i);
				NodeList transactionDataList = nNode.getChildNodes();
				
				//TransactionID parse
				double transactionID = Double.parseDouble(transactionDataList.item(0).getTextContent());
				
				//Date Parse
				Node dateNode = transactionDataList.item(1);
				NodeList dateList = dateNode.getChildNodes();
				Calendar cal = Calendar.getInstance();
				if (dateNode.getNodeType() == Node.ELEMENT_NODE) {
					int day = Integer.parseInt(dateList.item(0).getTextContent());
					int month = Integer.parseInt(dateList.item(1).getTextContent());
					int year =  Integer.parseInt(dateList.item(2).getTextContent());
					cal.set(year, month, day);
				}
				
				//BankID Parse
				int bankID = Integer.parseInt(transactionDataList.item(2).getTextContent());
				
				//AccountID Parse
				int accountID = Integer.parseInt(transactionDataList.item(3).getTextContent());
				
				//CategoryID Parse
				int categoryID = Integer.parseInt(transactionDataList.item(4).getTextContent());
				
				//Amount Parse
				double amount = Double.parseDouble(transactionDataList.item(5).getTextContent());
				
				//Internal Parse
				boolean internal = Boolean.parseBoolean(transactionDataList.item(6).getTextContent());
				
				//Description
				String description = transactionDataList.item(7).getTextContent();
				
				tm.addTransaction(amount, description, categoryID, cal, bankID, accountID, internal);
				
			}
			
			
		    } catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
