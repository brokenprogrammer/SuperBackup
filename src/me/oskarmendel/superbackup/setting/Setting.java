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
