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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;

public class JFontChooser {
	
	private Font font;
	
	public void setFont(Font f) {
		font = f;
	}
	
	public Font getFont() {
		return font;
	}
	
	public static Font showDialog(JFrame parent, Font initial) {
		JDialog dialog = new JDialog(parent, "Select Font", true);
		
		JFontChooser font = new JFontChooser();
		font.setFont(initial);
		
		dialog.setLayout(new BorderLayout());
		
		//
		JList<String> list = new JList<String>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		list.setLayoutOrientation(JList.VERTICAL);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedValue("Courier New", true);

		JScrollPane scroll = new JScrollPane(list);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//
		
		// ------- [ The Look Buttons ] -------
		JPanel look = new JPanel();
		look.setLayout(new GridLayout(4, 0));

		ButtonGroup gp = new ButtonGroup();
		JRadioButton reg = new JRadioButton("Regular", true);
		JRadioButton bold = new JRadioButton("Bold");
		JRadioButton italic = new JRadioButton("Italic");
		gp.add(reg);
		gp.add(italic);
		gp.add(bold);
		
		JSpinner size = new JSpinner();
		size.setModel(new SpinnerNumberModel(12, 8, 20, 1));
		
		look.add(reg);
		look.add(bold);
		look.add(italic);

		look.add(size);
		//
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		
		JButton ok = new JButton("Ok");
		ok.addActionListener(e -> {
			if (reg.isSelected())
				font.setFont(new Font(list.getSelectedValue(), Font.PLAIN, (int) size.getValue()));
			else if (bold.isSelected())
				font.setFont(new Font(list.getSelectedValue(), Font.BOLD, (int) size.getValue()));
			else
				font.setFont(new Font(list.getSelectedValue(), Font.ITALIC, (int) size.getValue()));
			
			dialog.setVisible(false);
		});
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(e -> {
			font.setFont(null);
			dialog.setVisible(false);
		});
		
		buttons.add(ok);
		buttons.add(cancel);
		
		dialog.add(scroll, BorderLayout.WEST);
		dialog.add(look, BorderLayout.CENTER);
		dialog.add(buttons, BorderLayout.SOUTH);
		
		
		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);

		return font.getFont();
		
	}

}
