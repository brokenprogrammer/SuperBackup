package me.oskarmendel.superbackup.setting;

/**
 * 
 * @author Oskar
 *
 */
public class Setting {
	
	private SettingType type;
	private String value;
	
	public Setting() {
		type = SettingType.INVALID;
		value = "";
	}
	
	public Setting(SettingType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public void Reset() {
		type = SettingType.INVALID;
		value = "";
	}
	
	public boolean isValid() {
		if (this.type != SettingType.INVALID && value != "") {
			return true;
		}
		
		return false;
	}
}
