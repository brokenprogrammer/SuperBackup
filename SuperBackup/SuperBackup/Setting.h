#ifndef SETTING_H
#define SETTING_H
#pragma once

enum SettingType
{
	INVALID = 0,
	DIRECTORY,
	FILETYPE
};

struct Setting
{
	Setting();
	~Setting();

	void Initialize(SettingType t, const char* v);
	void Reset();
	bool IsValid();

	SettingType type;
	const char* value; // Example: File exension or Directory path.

private:
};

#endif
