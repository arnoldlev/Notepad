// 
// Name:  Lev, Arnold
// Project: 6 (Final)
// Due:        December 3rd, 2021
// Course: CS-2450-01-f21 
// 
// Description: 
//  A brief description of the project. 
// 

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Utils {
	
	public static File fileOpen;
	public static boolean modified;
	
	public static int exit(JFrame frame, JTextArea area) {
		if (modified) {
			int result = JOptionPane.showConfirmDialog(frame, "You have unsaved data. Do you want to save first?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
			switch (result) {
				case JOptionPane.YES_OPTION:
					save(frame, area);
					System.exit(0);
					break;
				case JOptionPane.NO_OPTION:
					System.exit(0);
					break;
				default:
					break;
			}
			return result;
		} else {
			System.exit(0);
		}
		return -1;
	}
	
	public static void open(JFrame frame, JTextArea area, File file) {
	    try {
			String actual = Files.readString(Path.of(file.getAbsolutePath()));
			area.setText(actual);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
	    fileOpen = file;
	    modified = false;
	    frame.setTitle(fileOpen.getName() + " - Notepad");
	}
	
	public static void open(JFrame frame, JTextArea area) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text", "txt"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Java Source", "java"));
		
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			fileOpen = fileChooser.getSelectedFile();
			Path fileName = Path.of(fileOpen.getAbsolutePath());
			
		    try {
				String actual = Files.readString(fileName);
				area.setText(actual);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		modified = false;
		frame.setTitle(fileOpen.getName() + " - Notepad");
	}
	
	public static void save(JFrame frame, JTextArea area) {
		if (fileOpen == null) {
			saveAs(frame, area);
		} else {
			try (FileWriter fw = new FileWriter(fileOpen)) {
				fw.write(area.getText());
				modified = false;
				frame.setTitle(fileOpen.getName() + " - Notepad");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveAs(JFrame frame, JTextArea area) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text", "txt"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Java Source", "java"));
		
		int result = fileChooser.showSaveDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			File savedFile = fileChooser.getSelectedFile();
			try (FileWriter fw = new FileWriter(savedFile + ".txt")) {
				fw.write(area.getText());
				fileOpen = savedFile;
				modified = false;
				frame.setTitle(fileOpen.getName() + " - Notepad");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
