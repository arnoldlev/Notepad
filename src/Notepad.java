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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class Notepad {

	Notepad(File arg) {
		JFrame frame = new JFrame();
		frame.setSize(640, 480);
		frame.setTitle("Untitled - Notepad");
		frame.setLocationRelativeTo(null);
		frame.setIconImage(new ImageIcon("Notepad.png").getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Font defaultFont = new Font("Courier New", Font.PLAIN, 12);
	
		JTextArea area = new JTextArea();
		area.setFont(defaultFont);
		area.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(area);
		
		if (arg != null) {
			Utils.open(frame, area, arg);
		}
		
		// Menu Bar
		JMenuBar bar = new JMenuBar();
		JPopupMenu popUp = new JPopupMenu();
		
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMenu format = new JMenu("Format");
		JMenu view = new JMenu("View");
		JMenu help = new JMenu("Help");
		file.setMnemonic('F');
		format.setMnemonic('O');
		edit.setMnemonic('E');
		view.setMnemonic('V');
		help.setMnemonic('H');
		
		// File Items //
		JMenuItem newer = new JMenuItem("New", 'N');
		newer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newer.addActionListener(e -> { 
			area.setText("");
			Utils.modified = false;
		});
		file.add(newer);
		
		JMenuItem open = new JMenuItem("Open", 'O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		open.addActionListener(e -> Utils.open(frame, area));
		file.add(open);
		
		JMenuItem save = new JMenuItem("Save", 'S');
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		save.addActionListener(e -> Utils.save(frame, area));
		file.add(save);
		
		JMenuItem saveAs = new JMenuItem("Save As", 'A');
		saveAs.addActionListener(e -> Utils.saveAs(frame, area));
		file.add(saveAs);
		
		JMenuItem setup = new JMenuItem("Page Setup", 'u');
		setup.setEnabled(false);
		file.addSeparator();
		file.add(setup);
		
		JMenuItem print = new JMenuItem("Print", 'P');
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		print.addActionListener(e -> {
			try {
				area.print();
			} catch (PrinterException e1) {
				e1.printStackTrace();
			}
		});
		file.add(print);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(e -> Utils.exit(frame, area));
		file.addSeparator();
		file.add(exit);
		//
		
		// Edit Items//
		JMenuItem undo = new JMenuItem("Undo", 'U');
		undo.setEnabled(false);
		edit.add(undo);
		
		JMenuItem cut = new JMenuItem("Cut", 't');
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		cut.addActionListener(e -> area.cut());
		edit.addSeparator();
		edit.add(cut);
		
		JMenuItem copy = new JMenuItem("Copy", 'C');
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		copy.addActionListener(e -> area.copy());
		edit.add(copy);
		
		JMenuItem paste = new JMenuItem("Paste", 'P');
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		paste.addActionListener(e -> area.paste());
	    edit.add(paste);
	    
	    JMenuItem delete = new JMenuItem("Delete", 'l');
	    delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
	    delete.addActionListener(e -> area.setText(""));
	    edit.add(delete);
	    
	    JMenuItem find = new JMenuItem("Find", 'F');
	    find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
	    find.addActionListener(e -> new FindDialog(frame, area));
	    edit.addSeparator();
	    edit.add(find);
	    
	    JMenuItem findNext = new JMenuItem("Find Next", 'N');
	    findNext.setEnabled(false);
	    edit.add(findNext);
	    
	    JMenuItem replace = new JMenuItem("Replace", 'R');
	    replace.setEnabled(false);
	    replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
	    edit.add(replace);
	    
	    JMenuItem goTo = new JMenuItem("Go To", 'G');
	    goTo.setEnabled(false);
	    goTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
	    goTo.addActionListener(e -> new GotoDlg(frame, area));
	    edit.add(goTo);
	    
	    JMenuItem select = new JMenuItem("Select All", 'A');
	    select.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
	    select.addActionListener(e -> area.selectAll());
	    edit.addSeparator();
	    edit.add(select);
	    
	    JMenuItem timeDate = new JMenuItem("Time/Date", 'D');
	    timeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
	    timeDate.addActionListener(e -> {
	    	SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm a MM/dd/YYYY");
	    	area.append(formatDate.format(new Date()));
	    });
	    edit.add(timeDate);
	    //
	    
		
		// Pop up Menu
		JMenuItem cut2 = new JMenuItem("Cut", 't');
		cut2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		cut2.addActionListener(e -> area.cut());
		popUp.add(cut2);
		
		JMenuItem copy2 = new JMenuItem("Copy", 'C');
		copy2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		copy2.addActionListener(e -> area.copy());
		popUp.add(copy2);
		
		JMenuItem paste2 = new JMenuItem("Paste", 'P');
		paste2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		paste2.addActionListener(e -> area.paste());
	    popUp.add(paste2);
	    //
		
		
	    
	    // Format Items //
	    JCheckBoxMenuItem wrap = new JCheckBoxMenuItem("Word Wrap");
	    wrap.setSelected(true);
	    wrap.setMnemonic('W');
	    wrap.addActionListener(e -> {
	    	if (wrap.isSelected()) {
	    		goTo.setEnabled(false);
	    		area.setLineWrap(true);
	    	} else {
	    		goTo.setEnabled(true);
	    		area.setLineWrap(false);
	    	}
	    });
	    format.add(wrap);
	    
	    JMenuItem font = new JMenuItem("Font...", 'F');
	    font.addActionListener(e -> {
	    	Font f = JFontChooser.showDialog(frame, defaultFont);
	    	area.setFont(f);
	    });
	    format.add(font);
	    //
	    
	    // View Items //
	    JCheckBoxMenuItem status = new JCheckBoxMenuItem("Status Bar");
	    status.setSelected(true);
	    JPanel statusBar = new StatusBar(frame, area);
		frame.add(statusBar, BorderLayout.SOUTH);
	    status.addActionListener(e -> {
	    	if (status.isSelected()) {
	    		statusBar.setVisible(true);
	    	} else {
	    		statusBar.setVisible(false);
	    	}
	    });
	    status.setMnemonic('S');
	    view.add(status);
	    //
	    
	    
	    // Help Items //
	    JMenuItem viewHelp = new JMenuItem("View Help", 'H');
	    viewHelp.setEnabled(false);
	    help.add(viewHelp);
	    
	    JMenuItem extraCredit = new JMenuItem("Extra Credits", 'x');
	    extraCredit.addActionListener(e -> JOptionPane.showMessageDialog(frame, "* Status Bar", "Extra Credit List", JOptionPane.INFORMATION_MESSAGE));
	    help.add(extraCredit);
		
		JMenuItem about = new JMenuItem("About Notepad", 'A');
		about.addActionListener(e -> { 
			
			JPanel panel = new JPanel();
			JLabel line1 = new JLabel("<html>Notepad Vo.1<br>by A.Lev");
			line1.setFont(new Font("Courier New", Font.BOLD, 14));
			panel.add(line1);
			JOptionPane.showMessageDialog(frame, panel, "About", 0, new ImageIcon("Notepad.png"));
		});
		help.addSeparator();
		help.add(about);
		//
		
		bar.add(file);
		bar.add(edit);
		bar.add(format);
		bar.add(view);
		bar.add(help);
		
		area.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popUp.show(e.getComponent(), e.getX(), e.getY());
                }
            }
		});
		area.add(popUp);
		
		area.addCaretListener(e -> {
			JLabel line = (JLabel) statusBar.getComponent(1);
			try {
				int lineNum = area.getLineOfOffset(area.getCaretPosition());
				int columnNum = area.getCaretPosition() - area.getLineStartOffset(lineNum);
				line.setText("Line: " + (lineNum + 1)  + " Column: " + (columnNum + 1));
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		});
		
		area.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				Utils.modified = true; 
				if (statusBar != null) {
					JLabel count = (JLabel) statusBar.getComponent(0);
					if (area.getText().trim().equalsIgnoreCase("")) {
						count.setText("Word Count: 0");
					} else {
						count.setText("Word Count: " + area.getText().trim().split("\\s+").length);
					}		
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				Utils.modified = true;
				if (statusBar != null) {
					JLabel count = (JLabel) statusBar.getComponent(0);
					if (area.getText().trim().equalsIgnoreCase("")) {
						count.setText("Word Count: 0");
					} else {
						count.setText("Word Count: " + area.getText().trim().split("\\s+").length);
					}
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
		
	    frame.addWindowListener(new WindowAdapter() {
	    	@Override
	        public void windowClosing(WindowEvent e) {	
	        	int r = Utils.exit(frame, area);
	        	if (r == JOptionPane.CANCEL_OPTION) {
	        		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	        	} else {
	        		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        	}
	        }
	    });
		
		frame.add(scroll, BorderLayout.CENTER);
		frame.setJMenuBar(bar);
		frame.setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			File file = new File(args[0]);
			if (file.exists()) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new Notepad(file);
					}
				});
			} else {
				Scanner scan = new Scanner(System.in);
				System.out.println("File not found. Do you want to create one? (Y / N)");
				char input = scan.nextLine().charAt(0);
				
				switch (Character.toUpperCase(input)) {
					case 'Y':
						file.createNewFile();
						break;
					case 'N':
						System.exit(0);
						break;
					default:
						System.exit(0);
						break;
					
				}
				
				scan.close();
				
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new Notepad(file);
					}
				});
			}
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new Notepad(null);
				}
			});
		}
	}

}
