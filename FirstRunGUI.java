/**
 * @author      Matthew Janssen
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.*;

import Import_Export.NewImportGIU;

import java.text.ParseException;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstRunGUI extends JFrame {
	private JFrame frame;
	private JPanel thePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new FirstRunGUI();
			}
		});
	}

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	public FirstRunGUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(1000,600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle("Accounting Program - By Matthew Janssen");
		
		thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());
		
		createPanelComponents();
		
		
		
		frame.add(thePanel);
		frame.setVisible(true);
	}

	private void createPanelComponents() {
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		
		JLabel welcomeLabel = new JLabel("Welcome!");
		welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 29));
		thePanel.add(welcomeLabel, c);
		
		JLabel nameLabel = new JLabel("Please enter your name:");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		c.gridy = 2;
		c.gridwidth = 1;
		c.anchor = c.EAST;
		c.ipadx = 20;
		thePanel.add(nameLabel, c);
		
		JTextField tf = new JTextField(20);
		tf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		c.gridx = 2;
		c.anchor = c.WEST;
		thePanel.add(tf, c);
		
		JButton enterBut = new JButton("Enter");
		enterBut.setPreferredSize(new Dimension(120, 50));
		c.gridy = 3;
		c.gridx = 1;
		c.ipadx = 0;
		c.anchor = c.CENTER;
		c.gridwidth = 2;
		thePanel.add(enterBut, c);
		enterBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (tf.getText().equals("") || 
						tf.getText().equals("Please enter your name here")) {
					tf.setText("Please enter your name here");
					tf.setForeground(Color.RED);
				}
				else {
					frame.dispose();
					new MainGUI(tf.getText());
				}
			}
		});
		
	}
}
	