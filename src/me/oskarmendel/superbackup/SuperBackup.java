package me.oskarmendel.superbackup;

import me.oskarmendel.superbackup.backup.BackupManager;
import me.oskarmendel.superbackup.backup.DirectoryWatcher;
import me.oskarmendel.superbackup.setting.SettingParser;

public class SuperBackup {

	private static final String CONFIG_PATH = "config.cfg";
	
	public static void main(String[] args) {
		
		// 1. Read in directories to watch, read in files to watch.
		// 2. Sit and watch directories, when save occurs, store the old file
		// 3. Back to watching directories.
		
		System.out.println("Hello World!");
		
		SettingParser parser = new SettingParser(CONFIG_PATH);
		BackupManager bm = new BackupManager("C:\\Users\\Oskar\\Documents\\GitHub\\SuperBackup");
		
		DirectoryWatcher watcher = new DirectoryWatcher("", bm);
		watcher.watch();
	}

}
