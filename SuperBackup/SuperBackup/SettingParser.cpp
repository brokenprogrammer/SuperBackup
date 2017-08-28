#include "SettingParser.h"
#include <fstream>
#include <sstream>
#include <stdexcept>
#include <iostream>

SettingParser::SettingParser(const std::string filename)
{
	this->filename = filename;
	parseContent();
	contentToSettings();
}

SettingParser::~SettingParser()
{
	// @TODO: Clear map & vectors.
}

bool SettingParser::keyExists(const std::string key) const
{
	return contents.find(key) != contents.end();
}

std::vector<std::string> SettingParser::getKey(const std::string key) const
{
	if (!keyExists(key))
	{
		throw std::invalid_argument("Key does not exist.");
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
			std::istringstream is_line(line);
			std::string key;

			if (std::getline(is_line, key, '=')) // Extract and store key from line.
			{
				std::string value;
				
				if (key[0] == '#' || key[0] == ';') // Ignore comments
				{
					continue;
				}

				if (std::getline(is_line, value))
				{
					// Store key & value
					contents[key].push_back(value);
				}
			}
		}
	}

	infile.close();
}

void SettingParser::contentToSettings()
{
	for (auto const &x : contents)
	{
		for (auto const &vec : contents[x.first])
		{
			// Check if filetype or path and create Setting.
			// Populate Settings object.
			// Set member Settings object to the created one.
		}
	}
}

bool SettingParser::validLine(const std::string &line) const
{
	if (line[0] != '#' || line[0] != ';') { // If not comments.
		if (line.find("=") != std::string::npos) // If contains an equal sign.
		{
			return true;
		}
	}

	return false;
}
