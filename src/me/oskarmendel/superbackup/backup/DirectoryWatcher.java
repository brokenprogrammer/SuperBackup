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
package me.oskarmendel.superbackup.backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;

import me.oskarmendel.superbackup.setting.Setting;
import me.oskarmendel.superbackup.setting.SettingContainer;
import me.oskarmendel.superbackup.setting.SettingType;

/**
 * DirectoryWatcher watches a specified directory for 
 * changes and uses its BackupManager to back up files on change.
 * 
 * @author Oskar Mendel
 * @version 0.00.00
 * @name DirectoryWatcher.java
 */
public class DirectoryWatcher {

	private String directory;
	private SettingContainer settings;
	private BackupManager bm;
	
	/**
	 * Creates a new DirectoryWatcher to watch the specified directory
	 * and use the specified BackupManager.
	 * 
	 * @param directory - Directory to watch.
	 * @param bm - BackupManager to handle copying of files.
	 */
	public DirectoryWatcher(String directory, SettingContainer settings, BackupManager bm) {
		this.directory = directory;
		this.settings = settings;
		this.bm = bm;
	}
	
	/**
	 * Initiates the process of watching a directory for changes.
	 */
	public void watch() {
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			
			Path dir = FileSystems.getDefault().getPath(directory);
			WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
												 StandardWatchEventKinds.ENTRY_MODIFY,
												 StandardWatchEventKinds.ENTRY_DELETE);
			
			// Register all sub directories.
			Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
										  StandardWatchEventKinds.ENTRY_MODIFY,
										  StandardWatchEventKinds.ENTRY_DELETE);
					return FileVisitResult.CONTINUE;
				}
			});
			
			while (true) {
				WatchKey k;
				try {
					// Wait for key to be available.
					k = watcher.take();
				} catch(InterruptedException e) {
					e.printStackTrace();
					return;
				}
				
				for (WatchEvent<?> event : k.pollEvents()) {
					// Get kind of event.
					WatchEvent.Kind<?> kind = event.kind();
					
					@SuppressWarnings("unchecked")
					WatchEvent<Path> ev = (WatchEvent<Path>)event;
					
					Path curDir = (Path)k.watchable();
			        Path filename = ev.context();
			        Path fullPath = curDir.resolve(filename);
			        
			        File targetFile = fullPath.toFile();
			        boolean shouldBeBackedUp = false;
			        int delim = targetFile.getAbsolutePath().lastIndexOf('.');
			        
			        if (delim != -1) {
				        String fileType = targetFile.getAbsolutePath().substring(delim);
				        
				        for (Setting s : settings.getSettings()) {
				        	if (s.getType() == SettingType.FILETPYE) {
				        		if (s.getValue().equals(fileType)) {
				        			shouldBeBackedUp = true;
				        		}
				        	}
				        }
			        }
			        
			        if (shouldBeBackedUp == true) {
				        if (kind == StandardWatchEventKinds.OVERFLOW) {
				        } else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
				        	bm.copyFile(targetFile);
				        } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
				        	bm.copyFile(targetFile);
				        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
				        }
			        }
			        
			        if (targetFile.isDirectory()) {
			        	if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
			        		bm.createDirectory(targetFile);
			        		Path p = FileSystems.getDefault().getPath(targetFile.getAbsolutePath());
			        		System.out.println(p);
			        		p.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
											    StandardWatchEventKinds.ENTRY_MODIFY,
											    StandardWatchEventKinds.ENTRY_DELETE);
			        	} else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
			        		System.out.println(targetFile.getName() + " was modified.");
			        	} else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
			        		
			        	}
			        }
				}
				
				boolean valid = k.reset();
				if (!valid) {
					// Throw some error for breaking.
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
