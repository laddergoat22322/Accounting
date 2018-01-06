package Add_Remove_Components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Transactions.TransactionManager;
import mainGUI.GUI;
import mainGUI.MainGUI;

public class AddCategoryGUI extends GUI{
	
	private JFrame frame;
	private int bankID;
	private int accountID;
	private boolean done;
	
	public AddCategoryGUI() {
		done = false;
		initialise();
	}
	
	private void initialise() {
		frame = new JFrame();
		frame.setSize(600,300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("New Category");
		
		JPanel panel = createPanelWithComponents();
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	private JPanel createPanelWithComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = setupGridBag(GridBagConstraints.CENTER, GridBagConstraints.NONE, 3);
		
		JLabel headerLabel = createLabel("Add New Category", largeFont);
		panel.add(headerLabel, c);
		
		JLabel fileLabel = createLabel("Category Name", mediumFont);
		c.gridwidth = 1;
		c.gridy++;
		c.ipadx = 20;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;
		panel.add(fileLabel, c);
		
		JTextField tf = new JTextField();
		Dimension preferredSize = new Dimension(300, 30);
	    tf.setPreferredSize(preferredSize);
		tf.setFont(mediumFont);
		c.gridx++;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		panel.add(tf, c);
		
		
		JButton enterButton = new JButton("Done");
		enterButton.setPreferredSize(new Dimension(120, 45));
		c.gridy++;
		c.gridx = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 3;
		panel.add(enterButton, c);
		enterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = tf.getText();
				if (text.isEmpty()) {
					JOptionPane.showMessageDialog(frame,
						    "Please Enter An Account Name",
						    "Incomplete",
						    JOptionPane.ERROR_MESSAGE);
				}
				else {
					TransactionManager.addCategory(text);
					TransactionManager.analyseData();
					frame.dispose();
				}
			}
			
		});
		return panel;
	}

	public boolean isDone() {
		return done;
	}
}
