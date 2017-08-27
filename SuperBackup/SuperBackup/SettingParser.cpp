#include "SettingParser.h"
#include <fstream>

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
	return contents.find(key) != contents.end();
}

std::vector<std::string> SettingParser::getKey(const std::string key) const
{
	if (!keyExists(key))
	{
		// Error handling?
	}

	return contents.at(key);
}

void SettingParser::parseContent()
{
	std::ifstream infile(this->filename);
	
	std::string line;

	while (std::getline(infile, line))
	{
		if (validLine(line))
		{
			// Read key & value
			// Store key & value
		}
	}

	infile.close();
}

bool SettingParser::validLine(const std::string &line) const
{
	//TODO: Implement this function.
	return false;
}
