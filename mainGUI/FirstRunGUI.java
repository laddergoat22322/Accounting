package mainGUI;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Transactions.TransactionManager;

public class FirstRunGUI extends GUI {
	private JFrame frame;
	private JPanel thePanel;
	
	/**
	 * Constructor.
	 */
	public FirstRunGUI() {
		initialize();
	}

	private void createPanelComponents() {
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, 2);
		
		JLabel welcomeLabel = createLabel("Welcome!", largeHeaderFont);
		thePanel.add(welcomeLabel, c);
		
		JLabel nameLabel = createLabel("Please enter your name:", mediumFont);
		c.gridy++;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		c.ipadx = 20;
		c.fill = GridBagConstraints.NONE;
		thePanel.add(nameLabel, c);
		
		JTextField tf = new JTextField(20);
		tf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		c.gridx++;
		c.anchor = GridBagConstraints.WEST;
		thePanel.add(tf, c);
		
		JButton enterBut = new JButton("Enter");
		enterBut.setPreferredSize(new Dimension(120, 50));
		c.gridy++;
		c.gridx = 0;
		c.ipadx = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		thePanel.add(enterBut, c);
		enterBut.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame,
						    "Please enter your name before continuing",
						    "Incomplete",
						    JOptionPane.ERROR_MESSAGE);
				}
				else {
					TransactionManager tm = TransactionManager.getInstance();
					tm.setUserName(tf.getText());
					frame.dispose();
					new MainGUI();
				}
			}
		});
		
	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(1000,600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Accounting Program - By Matthew Janssen");
		
		thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());
		
		createPanelComponents();
		
		frame.add(thePanel);
		frame.setVisible(true);
	}
}
	