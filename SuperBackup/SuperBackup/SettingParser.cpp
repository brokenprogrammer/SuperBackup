#include "SettingParser.h"

SettingParser::SettingParser(const std::string filename)
{
	this->filename = filename;
	parseContent();
}

SettingParser::~SettingParser()
{
}

bool SettingParser::keyExists(const std::string key) const
{
	return false;
}

std::vector<std::string> SettingParser::getKey(const std::string key) const
{
	return std::vector<std::string>();
}

void SettingParser::parseContent()
{
}

bool SettingParser::validLine(const std::string & line) const
{
	return false;
}
