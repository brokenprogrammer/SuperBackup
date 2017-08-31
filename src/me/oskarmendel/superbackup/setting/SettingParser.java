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
package me.oskarmendel.superbackup.setting;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * SettingParser that parses a configurations file for Settings.
 * 
 * @author Oskar Mendel
 * @version 0.00.00
 */
public class SettingParser {
	
	private String filename;						  // Path for configurations file.
	private Map<String, ArrayList<String>> contents;  // Map containing all the configurations.
	private SettingContainer settings;				  // SettingsContainer to store all parsed Settings in.
	
	/**
	 * Creates a new SettingParser using the path to the specified
	 * configurations file and parses the content.
	 * 
	 * @param filename - Path to configurations file.
	 */
	public SettingParser(String filename) {
		this.filename = filename;
		this.contents = new HashMap<String, ArrayList<String>>();
		this.settings = new SettingContainer();
		parseContent();
		contentToSettings();
	}
	
	/**
	 * Checks if the specified key exist within the parsed content.
	 * 
	 * @param key - Key to check if exists.
	 * 
	 * @return - True if the specified key exists; False otherwise.
	 */
	public boolean keyExists(String key) {
		return contents.containsKey(key);
	}
	
	/**
	 * Returns the list of values for the specified Settings key.
	 * 
	 * @param key - Key to retrieve values for.
	 * 
	 * @return - ArrayList of values for the specified key.
	 */
	ArrayList<String> getKey(String key) {
		if (!contents.containsKey(key)) {
			throw new IllegalArgumentException("Specified key does not exist.");
		}
		
		return contents.get(key);
	}
	
	SettingContainer getSettings() {
		return settings;
	}
	
	/**
	 * Parses the content of the configurations file for this SettingParser.
	 */
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
	
	/**
	 * Converts the map of parsed content into a SettingContainer.
	 */
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
	
	/**
	 * Checks if the specified parsed line is a valid line.
	 * 
	 * @param line - Line to check if its valid.
	 * 
	 * @return True if the line is a valid line; False otherwise.
	 */
	private boolean validLine(String line) {
		if (line.charAt(0) != '#' || line.charAt(0) != ';') {
			if (line.contains("=")) {
				return true;	
			}
		}
		
		return false;
	}
}
