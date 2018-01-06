import java.awt.EventQueue;
import java.io.File;

import Import_Export.UserDataImport;
import Transactions.TransactionManager;
import mainGUI.*;

public class Startup {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			File tmpDir = new File("C:/Accounting Program/Data.xml");
			boolean exists = tmpDir.exists();
			if (exists) {
				TransactionManager.getInstance();
				new UserDataImport();
				TransactionManager.analyseData();
				new MainGUI();
			}
			else {
				TransactionManager.getInstance();
				TransactionManager.addCategory("Uncategorized");
				new FirstRunGUI();
			}
		}
	});
}
}
