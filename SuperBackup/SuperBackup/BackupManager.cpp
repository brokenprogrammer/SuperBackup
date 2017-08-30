#include "BackupManager.h"
#include <Windows.h>
#include "Shlwapi.h"
#include <string>
#include <strsafe.h>
#include <iostream>

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

void BackupManager::copyFile(const char * filename)
{
}

void BackupManager::createDirectory(const char * directory)
{
	char currentpath[MAX_PATH];
	GetModuleFileName(NULL, currentpath, MAX_PATH);

	std::string path = currentpath;
	path.append(directory);

	// Attempt to create directory, throw error if no success.
	CreateDirectory(directory, NULL);
}

void BackupManager::checkAllFiles()
{
	WIN32_FIND_DATA ffd;
	LARGE_INTEGER filesize;
	TCHAR szDir[MAX_PATH];
	size_t length_of_arg;
	HANDLE hFind = INVALID_HANDLE_VALUE;
	DWORD dwError = 0;

	// Prepare string for FindFile function.
	StringCchCopy(szDir, MAX_PATH, directory);
	StringCchCat(szDir, MAX_PATH, TEXT("\\*"));

	// Find the first file in the directory.
	hFind = FindFirstFile(szDir, &ffd);

	if (hFind == INVALID_HANDLE_VALUE)
	{
		// Throw some error.
		return;
	}

	do
	{
		// Use found file and its data.
		std::cout << ffd.cFileName << std::endl;
	} while (FindNextFile(hFind, &ffd) != 0);

	dwError = GetLastError();
	// Handle unwanted errors.

	FindClose(hFind);
}

bool BackupManager::directoryExist(const char * directory)
{
	std::string path = directory;

	int retval;
	retval = PathFileExists(path.c_str());

	if (retval == 1)
	{
		// Path to directory does exist.
		return true;
	}
	else 
	{
		// Path to directory doesn't exist.
		return false;
	}
}

bool BackupManager::fileExist(const char * filename)
{
	return false;
}
