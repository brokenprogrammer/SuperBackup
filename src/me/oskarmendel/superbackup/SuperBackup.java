package me.oskarmendel.superbackup;

import me.oskarmendel.superbackup.backup.BackupManager;
import me.oskarmendel.superbackup.backup.DirectoryWatcher;
import me.oskarmendel.superbackup.setting.SettingParser;

/**
 * Main entry point of the application.
 * 
 * @author Oskar Mendel
 * @version 0.00.00
 */
public class SuperBackup {

	// Path to configurations file.
	private static final String CONFIG_PATH = "config.cfg";
	
	public static void main(String[] args) {
		SettingParser parser = new SettingParser(CONFIG_PATH);
		BackupManager bm = new BackupManager("C:\\Users\\Oskar\\Documents\\GitHub\\SuperBackup");
		
		DirectoryWatcher watcher = new DirectoryWatcher("", bm);
		watcher.watch();
	}

}
