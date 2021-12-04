// 
// Name:  Lev, Arnold
// Project: 6 (Final)
// Due:        December 3rd, 2021
// Course: CS-2450-01-f21 
// 
// Description: 
//  A brief description of the project. 
// 

import javax.swing.JDialog;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.*;

public class FindDialog {
	
	public FindDialog(JFrame frame, JTextArea area) {
		JDialog dialog = new JDialog(frame, "Find", false);
		
		JTextField word = new JTextField();
		JButton	find = new JButton();
		JCheckBox match = new JCheckBox();
		JButton cancel = new JButton();
		JCheckBox wrap = new JCheckBox();

		//======== this ========
		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(null);
		contentPane.add(word);
		word.setBounds(15, 25, 215, 25);

		//---- find ----
		find.setText("Find");
		find.setFont(new Font("Arial", Font.PLAIN, 12));
		contentPane.add(find);
		find.addActionListener(e -> {
			int start = 0;
			if (wrap.isSelected()) {
				if (!match.isSelected()) {
					start = area.getText().toLowerCase().indexOf(word.getText().toLowerCase());
				} else {
					start = area.getText().indexOf(word.getText());
				}
				
				if (start != -1) {
					area.select(start, word.getText().length() + start);
				}
			} else {
				int pos = area.getCaretPosition();
				String textToSearch = area.getText().substring(pos);
				if (!match.isSelected()) {
					start = textToSearch.toLowerCase().indexOf(word.getText().toLowerCase());
				} else {
					start = textToSearch.indexOf(word.getText());
				}
				
				if (start != -1) {
					area.select(start + pos, word.getText().length() + (start + pos));
				}
			}
		});
		find.setBounds(245, 25, 90, 25);

		//---- match ----
		match.setText("Match Case");
		match.setFont(new Font("Arial", Font.PLAIN, 12));
		contentPane.add(match);
		match.setBounds(15, 65, 125, match.getPreferredSize().height);

		//---- cancel ----
		cancel.setText("Cancel");
		cancel.setFont(new Font("Arial", Font.PLAIN, 12));
		contentPane.add(cancel);
		cancel.addActionListener(e -> dialog.setVisible(false));
		cancel.setBounds(245, 60, 90, 25);

		//---- wrap ----
		wrap.setText("Wrap Around");
		wrap.setFont(new Font("Arial", Font.PLAIN, 12));
		contentPane.add(wrap);
		wrap.setBounds(15, 95, 125, wrap.getPreferredSize().height);

		{
			// compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		
		dialog.pack();
		dialog.setLocationRelativeTo(dialog.getOwner());
		dialog.setVisible(true);
	}

}
