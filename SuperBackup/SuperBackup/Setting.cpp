#include "Setting.h"

Setting::Setting()
{
	type = INVALID;
	value = nullptr;
}

Setting::~Setting()
{

}

void Setting::Initialize()
{

}

void Setting::Reset()
{

}

bool Setting::IsValid()
{
	if (type != INVALID && value != nullptr) {
		return true;
	}

	return false;
}