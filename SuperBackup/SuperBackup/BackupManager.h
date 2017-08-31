#ifndef BACKUPMANAGER_H
#define BACKUPMANAGER_H
#pragma once

class BackupManager
{
public:
	BackupManager(const char* directory);
	~BackupManager();

	void copyFile(const char* source);

private:
	void createDirectory(const char* directory);

	void checkAllFiles();

	bool directoryExist(const char* directory);
	bool fileExist(const char* filename);

	const char* directory;
	const char* workingDirectory;
};

#endif
