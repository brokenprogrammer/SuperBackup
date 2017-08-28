#include "stdio.h"
#include "SettingParser.h"

#define CONFIG_PATH "config.cfg"

int main()
{
	// Read in directories to watch, read in files to watch.
	// Sit and watch directories, when save occurs, store the old file
	// Back to watching directories.

	//@TODO: 1. Finish parser, validLine & ParseContent.
	//@TODO: 2. Test for existing config file.
	//@TODO: 3. Function for extracting parsed data into Setting structures.

	printf("Hello, World!");

	SettingParser settingParser(CONFIG_PATH);

	return 0;
}