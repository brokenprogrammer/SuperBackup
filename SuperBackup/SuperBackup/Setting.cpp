#include "Setting.h"

Setting::Setting()
{
	type = INVALID;
	value = nullptr;
}

Setting::~Setting()
{

}

void Setting::Initialize(SettingType t, const char* v)
{
	type = t;
	value = v;
}

void Setting::Reset()
{
	type = INVALID;
	value = nullptr;
}

bool Setting::IsValid()
{
	if (type != INVALID && value != nullptr) {
		return true;
	}

	return false;
}