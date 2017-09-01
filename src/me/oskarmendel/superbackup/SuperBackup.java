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
package me.oskarmendel.superbackup;

import java.awt.AWTException;
import java.net.MalformedURLException;

import me.oskarmendel.superbackup.backup.BackupManager;
import me.oskarmendel.superbackup.backup.DirectoryWatcher;
import me.oskarmendel.superbackup.setting.SettingParser;
import me.oskarmendel.superbackup.ui.SystemTrayUI;

/**
 * Main entry point of the application.
 * 
 * @author Oskar Mendel
 * @version 0.00.00
 * @name SuperBackup.java
 */
public class SuperBackup {

	// Path to configurations file.
	private static final String CONFIG_PATH = "config.cfg";
	
	public static void main(String[] args) {
		try {
			SystemTrayUI ui = new SystemTrayUI();
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		SettingParser parser = new SettingParser(CONFIG_PATH);
		BackupManager bm = new BackupManager("C:\\Users\\Oskar\\Documents\\GitHub\\SuperBackup");
		System.out.println(parser.getSettings().getSettings().size());
		DirectoryWatcher watcher = new DirectoryWatcher("", parser.getSettings(), bm);
		watcher.watch();
		
		//TODO: Make use of settings within DirectoryWatcher.
		//TODO: Make recursive check of subdirectories in watched directory.
	}
}
