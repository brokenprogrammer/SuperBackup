package me.oskarmendel.superbackup.setting;

import java.util.ArrayList;

/**
 * 
 * @author Oskar
 *
 */
public class SettingContainer {

	private ArrayList<Setting> settingList;
	
	public SettingContainer() {
		settingList = new ArrayList<Setting>();
	}
	
	public void addSetting(Setting setting) {
		settingList.add(setting);
	}
	
	public Setting removeSetting(Setting setting) {
		Setting s = settingList.get(settingList.indexOf(setting));
		settingList.remove(setting);
		
		return s;
	}
	
	public void clearSettings() {
		settingList.clear();
	}
}
