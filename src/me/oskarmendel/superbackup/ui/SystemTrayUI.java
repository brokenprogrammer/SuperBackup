/**
 * MIT License
 * 
 * Copyright (c) 2017 Oskar Mendel
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package me.oskarmendel.superbackup.ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * SystemTrayUI is the user interface of this application
 * and creates a SystemTray icon together with the menu for 
 * using it.
 * 
 * @author Oskar Mendel
 * @version 0.00.00
 * @name SystemTrayUI.java
 */
public class SystemTrayUI {
	
	private TrayIcon trayIcon;
	private PopupMenu popupMenu;
	
	/**
	 * Creates a new SystemTrayUI by initializing the TrayIcon
	 * together with its PopupMenu and then adding it to the
	 * SystemTray.
	 * 
	 * @throws AWTException - Thrown either when SystemTray is not supported
	 * 						  or when unable to add the TrayIcon to the SystemTray.
	 * @throws MalformedURLException - Thrown from createImage when an 
	 * 								   invalid file path was specified.
	 */
	public SystemTrayUI() throws AWTException, MalformedURLException {
		if (!SystemTray.isSupported()) {
			throw new AWTException("SystemTray is not available.");
		}
		
		this.trayIcon = new TrayIcon(createImage("trayicon.jpg", "SuperBackup"));
		this.popupMenu = new PopupMenu();
		final SystemTray tray = SystemTray.getSystemTray();
		
		
		// Create PopupMenu components.
		MenuItem aboutItem = new MenuItem("About");
		MenuItem exitItem = new MenuItem("Exit");
		
		this.popupMenu.add(aboutItem);
		this.popupMenu.addSeparator();
		this.popupMenu.add(exitItem);
		
		// Set PopupMenu for the trayIcon.
		this.trayIcon.setPopupMenu(this.popupMenu);
		
		try {
			tray.add(this.trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		// Create ActionListener for the About MenuItem.
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "SuperBackup for easy backup of your files. \n" +
													"For more info: https://github.com/brokenprogrammer/SuperBackup \n\n" +
													"Created by: Oskar Mendel.");
			}
		});
		
		// Create ActionListener for the Exit MenuItem.
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	/**
	 * Creates a new Image object using the specified path and image description.
	 * 
	 * @param path - Path of the image to create.
	 * @param desc - Description of the image to create.
	 * 
	 * @return - Image object loaded from specified Path with specified description.
	 * 
	 * @throws MalformedURLException - Thrown when an invalid path was specified.
	 */
	private static Image createImage(String path, String desc) throws MalformedURLException {
		URL imageURL = Paths.get(path).toUri().toURL();
		
		if (imageURL == null) {
			throw new NullPointerException("Image does not exist.");
		} else {
			return (new ImageIcon(imageURL, desc)).getImage();
		}
	}
}
