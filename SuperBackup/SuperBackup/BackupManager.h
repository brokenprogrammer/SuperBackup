#ifndef BACKUPMANAGER_H
#define BACKUPMANAGER_H
#pragma once

class BackupManager
{
public:
	BackupManager(const char* directory);
	~BackupManager();

private:
	const char* directory;
};

#endif
