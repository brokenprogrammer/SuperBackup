package me.oskarmendel.superbackup.setting;

import java.util.ArrayList;

/**
 * SettingContainer class used as a container for Settings.
 * TODO: Add method to combine / get some usable value taken from all Settings.
 * 
 * @author Oskar Mendel
 * @version 0.00.00
 */
public class SettingContainer {

	private ArrayList<Setting> settingList;
	
	/**
	 * Default constructor of the SettingContainer.
	 * Initializes all members with default values.
	 */
	public SettingContainer() {
		settingList = new ArrayList<Setting>();
	}
	
	/**
	 * Adds a Setting to this SettingContainer.
	 * 
	 * @param setting - Setting object to add to this SettingContainer.
	 */
	public void addSetting(Setting setting) {
		settingList.add(setting);
	}
	
	/**
	 * Removes a Setting from this SettingContainer.
	 * 
	 * @param setting - Setting object to remove from this SettingContainer.
	 * 
	 * @return - Removed Setting object.
	 */
	public Setting removeSetting(Setting setting) {
		Setting s = settingList.get(settingList.indexOf(setting));
		settingList.remove(setting);
		
		return s;
	}
	
	/**
	 * Clears the SettingContainer of all Settings.
	 */
	public void clearSettings() {
		settingList.clear();
	}
}
