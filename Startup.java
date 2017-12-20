import java.awt.EventQueue;

import mainGUI.*;

public class Startup {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				FirstRunGUI window = new FirstRunGUI();
			}
		});
	}
}
