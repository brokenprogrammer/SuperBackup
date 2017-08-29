#include "BackupManager.h"

BackupManager::BackupManager(const char* directory)
{
	//TODO: 1. When constructed scan target directory, if copies of files
	//	doesn't exist this manager saves them.
	//TODO: 2. Public backup function for when running and a file changed.

	this->directory = directory;
}

BackupManager::~BackupManager()
{

}