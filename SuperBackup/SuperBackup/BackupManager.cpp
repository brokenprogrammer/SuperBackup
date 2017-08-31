#include "BackupManager.h"
#include <Windows.h>
#include "Shlwapi.h"
#include <string>
#include <strsafe.h>
#include <iostream>
#include <fstream>
#include <chrono>
#include <ctime>


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

	if (fileExist("main.cpp"))
	{
		std::cout << "main exist" << std::endl;
	}

	//checkAllFiles();
}

BackupManager::~BackupManager()
{

}

//TODO: Refactor and use only source parameter since the name will be the same and
//TODO: the destination folder will be a member variable.
void BackupManager::copyFile(const char* source)
{
	// Getting current time.
	auto now = std::chrono::system_clock::now();
	auto now_c = std::chrono::system_clock::to_time_t(now);
	struct tm newtime;
	errno_t err;

	err = localtime_s(&newtime, &now_c);

	if (err)
	{
		//Throw some error.
	}

	// Building current time string.
	std::string currentTime = std::to_string(newtime.tm_year + 1900);
	currentTime.append("-");
	currentTime.append(std::to_string(newtime.tm_mon+1));
	currentTime.append("-");
	currentTime.append(std::to_string(newtime.tm_mday));
	currentTime.append("-");
	currentTime.append(std::to_string(newtime.tm_hour));
	currentTime.append("-");
	currentTime.append(std::to_string(newtime.tm_min));
	currentTime.append("-");
	currentTime.append(std::to_string(newtime.tm_sec));
	currentTime.append("_");

	// Building destination string.
	std::string dest = workingDirectory;
	dest.append("/");
	dest.append(currentTime);
	dest.append(source);

	std::cout << source << std::endl;
	std::cout << dest << std::endl;

	// Copying file to destination.
	std::ifstream src(source, std::ios::binary);
	std::ofstream dst(dest, std::ios::binary);

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
		std::string filepath = workingDirectory;
		filepath.append("/");
		filepath.append(ffd.cFileName);

		if (!fileExist(filepath.c_str())) // If file is not present, make a copy.
		{
			copyFile(ffd.cFileName);
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

//TODO: Make this function work on already stored files with date infront.
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
