package me.oskarmendel.superbackup.backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class DirectoryWatcher {

	private String directory;
	BackupManager bm;
	
	public DirectoryWatcher(String directory, BackupManager bm) {
		this.directory = directory;
		this.bm = bm;
	}
	
	public void watch() {
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			
			Path dir = FileSystems.getDefault().getPath(directory);
			WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
												 StandardWatchEventKinds.ENTRY_MODIFY,
												 StandardWatchEventKinds.ENTRY_DELETE);
			
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
			        Path filename = ev.context();
			        
			        File targetFile = filename.toFile();
			        if (targetFile.isFile()) {
				        if (kind == StandardWatchEventKinds.OVERFLOW) {
				        	
				        } else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
				        	bm.copyFile(targetFile);
				        } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
				        	bm.copyFile(targetFile);
				        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
				        	
				        }
			        }
				}
				
				boolean valid = key.reset();
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
