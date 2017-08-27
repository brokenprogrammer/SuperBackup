#ifndef SETTINGPARSER_H
#define SETTINGPARSER_H
#pragma once

#include "Settings.h"

namespace SettingParser
{
	Settings parseSettings(const char* config_path);
}

#endif
