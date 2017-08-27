#include "stdio.h"
#include "SettingParser.h"

#define CONFIG_PATH ""

int main()
{
	// Read in directories to watch, read in files to watch.
	// Sit and watch directories, when save occurs, store the old file
	// Back to watching directories.


	// Example cfg file:
	//SuperBackup.cfg
	//directory = C:/my/test/app
	//directory = C:/second/app
	//filetype = *.c
	//filetype = *.h
	//filetype = *.cpp

	printf("Hello, World!");

	Settings settings = SettingParser::parseSettings(CONFIG_PATH);

	return 0;
}