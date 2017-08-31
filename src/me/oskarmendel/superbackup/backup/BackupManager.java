package me.oskarmendel.superbackup.backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * BackupManager manages backups of files.
 * 
 * @author Oskar Mendel
 * @version 0.00.00
 */
public class BackupManager {
	
	private String directory;
	private String workingDirectory;
	
	/**
	 * Creates a new BackupManager to be backing up the specified
	 * directory.
	 * 
	 * @param directory - Path to directory.
	 */
	public BackupManager(String directory) {
		this.directory = directory;
		
		int lastPath = directory.lastIndexOf('\\');
		
		this.workingDirectory = directory.substring(lastPath+1);
		
		checkAllFiles();
	}
	
	/**
	 * Creates a copy of the specified file in the workingDirectory of this BackupManager.
	 * 
	 * @param file - File to copy.
	 */
	public void copyFile(File file) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
			LocalDateTime now = LocalDateTime.now();
			
			File dest = new File(this.workingDirectory + "/" + formatter.format(now) + "-" + file.getName());
			if (!dest.exists()) {
				Files.copy(file.toPath(), dest.toPath());
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Performs a check on all files within the specified directory and 
	 * creates copies of the files that either do not have a backup or 
	 * have an out dated backup.
	 */
	private void checkAllFiles() {
		File folder = new File(this.directory);
		File workingDir = new File(this.workingDirectory);
		
		File[] files = folder.listFiles();
		File[] backupFiles = workingDir.listFiles(); 
		//TODO: Some kind of recursive check to check sub directories and back them up as well.
		
		// Forearch file: Check if there already is a backup of that file.
		for (File f : files) {
			if (f.isFile()) {
				ArrayList<File> currentBackups = new ArrayList<>();
				boolean backupExist = false;
				
				// Foreach backup file: If the file is already backed up, compare dates and backup if the backup is older.
				for (File bf : backupFiles) {
					if (bf.getName().endsWith(f.getName())) { // Found backup file.
						currentBackups.add(bf);
						backupExist = true;
					}
				}
				
				if (backupExist) {
					
					// Sort by last modified.
					currentBackups.sort(new Comparator<File>() {
						@Override
						public int compare(File f1, File f2) {
							return (f1.lastModified() < f2.lastModified()) ? 1 : -1;
						}
					});
					
					// Check if original is modified more recently than newest backup.
					if (f.lastModified() > currentBackups.get(0).lastModified()) {
						// Make new backup.
						copyFile(f);
					}
				} else {
					// No backups exist so make one.
					copyFile(f);
				}
			}
		}
	}
}
