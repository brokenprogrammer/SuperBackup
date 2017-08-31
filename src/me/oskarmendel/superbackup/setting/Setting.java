package me.oskarmendel.superbackup.setting;

/**
 * Setting class holds a setting with a SettingType and
 * a value of that type.
 * 
 * @author Oskar Mendel
 * @version 0.00.00
 */
public class Setting {
	
	private SettingType type;
	private String value;
	
	/**
	 * Default constructor for the Setting class.
	 * Constructs an empty setting and sets it as invalid.
	 */
	public Setting() {
		type = SettingType.INVALID;
		value = "";
	}
	
	/**
	 * Creates a new Setting with the specified SettingType and value.
	 * 
	 * @param type - SettingType.
	 * @param value - Value of this Setting.
	 */
	public Setting(SettingType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Resets the setting to be invalid and empty.
	 */
	public void Reset() {
		type = SettingType.INVALID;
		value = "";
	}
	
	/**
	 * Checks if the Setting is valid or not.
	 * 
	 * @return - True of the Setting is valid and available for use; False otherwise.
	 */
	public boolean isValid() {
		if (this.type != SettingType.INVALID && value != "") {
			return true;
		}
		
		return false;
	}
}
