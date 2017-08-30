#include "stdio.h"
#include "SettingParser.h"
#include "Settings.h"
#include "DirectoryWatcher.h"
#include "BackupManager.h"

#define CONFIG_PATH "config.cfg"

void onFileChange()
{

}

int main()
{
	// Read in directories to watch, read in files to watch.
	// Sit and watch directories, when save occurs, store the old file
	// Back to watching directories.

	//@TODO: 1. Finish parser, validLine & ParseContent.
	//@TODO: 2. Test for existing config file.
	//@TODO: 3. Function for extracting parsed data into Setting structures.

	printf("Hello, World!\n");

	SettingParser settingParser(CONFIG_PATH);

	Settings settingsCollection = settingParser.GetSettings();

	BackupManager bm("C:\\Users\\Oskar\\Documents\\GitHub\\SuperBackup\\SuperBackup\\SuperBackup");

	//DirectoryWatcher watcher("C:\\Users\\Oskar\\Documents\\GitHub\\SuperBackup\\SuperBackup\\SuperBackup", onFileChange);
	//watcher.watch();

	return 0;
}