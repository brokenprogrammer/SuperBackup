#include "BackupManager.h"
#include <Windows.h>
#include "Shlwapi.h"
#include <string>
#include <strsafe.h>
#include <iostream>
#include <fstream>

BackupManager::BackupManager(const char* directory)
{
	//TODO: 1. When constructed scan target directory, if copies of files
	//	doesn't exist this manager saves them.
	//TODO: 2. Public backup function for when running and a file changed.

	this->directory = directory;

	std::string a = directory;
	size_t found = a.find_last_of("/\\");
	std::string b = a.substr(found+1);

	this->workingDirectory = b.c_str();

	if (!directoryExist(workingDirectory))
	{
		createDirectory(workingDirectory);
	}

	//checkAllFiles();
}

BackupManager::~BackupManager()
{

}

//TODO: Refactor and use only source parameter since the name will be the same and
//TODO: the destination folder will be a member variable.
void BackupManager::copyFile(const char* source, const char *destination)
{
	std::ifstream src(source ,std::ios::binary);
	std::ofstream dst(destination, std::ios::binary);

	dst << src.rdbuf();
}

void BackupManager::createDirectory(const char * directory)
{
	std::string path = directory;

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
		std::string filepath = workingDirectory;
		filepath.append("/");
		filepath.append(ffd.cFileName);

		if (!fileExist(filepath.c_str())) // If file is not present, make a copy.
		{
			copyFile(ffd.cFileName, filepath.c_str());
			//std::cout << "File " << ffd.cFileName << " doesn't exist." << std::endl;
		}
		else if (false) // Else check if the present file is older than current file.
		{

		}

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
	std::string path = filename;

	int retval;
	retval = PathFileExists(filename);
	
	if (retval == 1)
	{
		// Path to file does exist.
		return true;
	}
	else
	{
		// Path to file doesn't exist.
		return false;
	}
}
