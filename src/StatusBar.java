// 
// Name:  Lev, Arnold
// Project: 6 (Final)
// Due:        December 3rd, 2021
// Course: CS-2450-01-f21 
// 
// Description: 
//  A brief description of the project. 
// 

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.BadLocationException;

public class StatusBar extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public StatusBar(JFrame frame, JTextArea area) {
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setPreferredSize(new Dimension(frame.getWidth(), 25));
		setLayout(new BorderLayout());
		
		JLabel infoBar = new JLabel("Word Count: " + area.getText().trim().split("\\s+").length);
		if (area.getText().trim().equalsIgnoreCase("")) {
			infoBar.setText("Word Count: 0");
		}
		
		JLabel pos = new JLabel();
		try {
			int lineNum = area.getLineOfOffset(area.getCaretPosition());
			int columnNum = area.getCaretPosition() - area.getLineStartOffset(lineNum);
			pos.setText("Line: " + (lineNum + 1)  + " Column: " + (columnNum + 1));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		add(infoBar, BorderLayout.WEST);
		add(pos, BorderLayout.EAST);
	}
	
}