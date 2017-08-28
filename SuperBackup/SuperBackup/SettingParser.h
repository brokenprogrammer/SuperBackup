#ifndef SETTINGPARSER_H
#define SETTINGPARSER_H
#pragma once

#include "Settings.h"
#include <string>
#include <map>
#include <vector>

class SettingParser
{
public:
	SettingParser(const std::string filename);
	~SettingParser();

	bool keyExists(const std::string key) const;
	std::vector<std::string> getKey(const std::string key) const;

private:

	void parseContent();
	void contentToSettings();

	bool validLine(const std::string &line) const;

	std::string filename;
	std::map<std::string, std::vector<std::string>> contents;
};

#endif
