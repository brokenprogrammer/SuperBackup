#ifndef SETTINGS_H
#define SETTINGS_H
#pragma once

#include "Setting.h"
#include <vector>

class Settings
{
public:
	Settings();
	~Settings();

	Setting& operator[](size_t i);
	const Setting& operator[](size_t i) const;

	void AddSetting(Setting s);
	Setting RemoveSetting(Setting s);
	void ClearSettings();

	// @TODO: Some function to collect all Settings and combine them.

private:
	std::vector<Setting> settings;
};

#endif
