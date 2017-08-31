package me.oskarmendel.superbackup.setting;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Oskar
 *
 */
public class SettingParser {
	
	private String filename;
	private Map<String, ArrayList<String>> contents;
	private SettingContainer settings;
	
	public SettingParser(String filename) {
		this.filename = filename;
		this.contents = new HashMap<String, ArrayList<String>>();
		parseContent();
		contentToSettings();
	}
	
	public boolean keyExists(String key) {
		return contents.containsKey(key);
	}
	
	ArrayList<String> getKey(String key) {
		if (!contents.containsKey(key)) {
			throw new IllegalArgumentException("Specified key does not exist.");
		}
		
		return contents.get(key);
	}
	
	SettingContainer getSettings() {
		return settings;
	}
	
	private void parseContent() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			
			String line;
			while ((line = br.readLine()) != null) {
				if (validLine(line)) {
					String key;
					String value;
					int delim = line.indexOf('=');
					
					key = line.substring(0, delim).trim();
					value = line.substring(delim+1).trim();
					
					if (contents.get(key) == null) {
						contents.put(key, new ArrayList<String>());
					}
					
					contents.get(key).add(value);
				}
			}
			
			br.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void contentToSettings() {
		for (String key : contents.keySet()) {
			for (String value : contents.get(key)) {
				
				if (key.equals("directory") || key.equals("dir")) {
					Setting s = new Setting(SettingType.DIRECTORY, value);
					settings.addSetting(s);
				} else if (key.equals("filetype")) {
					Setting s = new Setting(SettingType.FILETPYE, value);
					settings.addSetting(s);
				}
				
			}
		}
	}
	
	private boolean validLine(String line) {
		if (line.charAt(0) != '#' || line.charAt(0) != ';') {
			if (line.contains("=")) {
				return true;	
			}
		}
		
		return false;
	}
}
