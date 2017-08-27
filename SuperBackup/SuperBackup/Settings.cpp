#include "Settings.h"

Settings::Settings()
{

}

Settings::~Settings()
{
	ClearSettings();
}

Setting& Settings::operator[](size_t i)
{
	return settings[i];
}

const Setting& Settings::operator[](size_t i) const
{
	return settings[i];
}

void Settings::AddSetting(Setting s)
{
	settings.push_back(s);
}

Setting Settings::RemoveSetting(Setting s)
{
	//TODO: Implement this.
	return Setting();
}

void Settings::ClearSettings()
{
	settings.clear();
}
