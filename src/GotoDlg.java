// 
// Name:  Lev, Arnold
// Project: 6 (Final)
// Due:        December 3rd, 2021
// Course: CS-2450-01-f21 
// 
// Description: 
//  A brief description of the project. 
// 

import javax.swing.JButton;

import java.awt.Cursor;
import java.awt.Font;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.text.BadLocationException;
import javax.swing.text.NumberFormatter;

public class GotoDlg {
	
	public GotoDlg(JFrame frame, JTextArea area) {
		JDialog dialog = new JDialog(frame, "Go To Line", true);
		dialog.setLayout(null);
		dialog.setSize(320, 135);
		
	    NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(1);
	    formatter.setAllowsInvalid(false);
		
		JLabel line = new JLabel();
		JFormattedTextField lineNum = new JFormattedTextField(formatter);
		JButton goTo = new JButton();
		JButton cancel = new JButton();
		
		//---- line ----
		line.setText("Line Number:");
		line.setFont(new Font("Arial", Font.PLAIN, 12));
		line.setDisplayedMnemonic('L');
		dialog.add(line);
		line.setBounds(15, 10, 90, 19);

		//---- lineNum ---
		lineNum.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		dialog.add(lineNum);
		lineNum.setBounds(15, 30, 275, lineNum.getPreferredSize().height);
		

		//---- goTo ----
		goTo.setText("Go To");
		goTo.addActionListener(e -> {
			if (!lineNum.getText().equalsIgnoreCase("")) {
				int num = Integer.valueOf(lineNum.getText());
				try {
					area.setCaretPosition(area.getLineStartOffset(num - 1));
				} catch (BadLocationException e1) {
					
				}
				dialog.setVisible(false);
			}
		});
		goTo.setFont(new Font("Arial", Font.PLAIN, 12));
		dialog.add(goTo);
		goTo.setBounds(110, 70, 85, goTo.getPreferredSize().height);

		//---- cancel ----
		cancel.setText("Cancel");
		cancel.setFont(new Font("Arial", Font.PLAIN, 12));
		cancel.addActionListener(e -> dialog.setVisible(false));
		dialog.add(cancel);
		cancel.setBounds(210, 70, 85, 23);
		
		dialog.setLocationRelativeTo(dialog.getParent());
		dialog.setVisible(true);
	}

}
