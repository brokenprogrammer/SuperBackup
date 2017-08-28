#include "DirectoryWatcher.h"
#include <Windows.h>
#include <tchar.h>
#include <iostream>

DirectoryWatcher::DirectoryWatcher(const char* directory)
{
	this->directory = directory;
}

DirectoryWatcher::~DirectoryWatcher()
{

}

void DirectoryWatcher::watch()
{
	LPSTR dir = const_cast<LPSTR>(directory);
	DWORD wait_status;
	HANDLE change_handles[2];
	TCHAR lpDrive[4];
	TCHAR lpFile[_MAX_FNAME];
	TCHAR lpExt[_MAX_EXT];

	_tsplitpath_s(dir, lpDrive, 4, NULL, 0, lpFile, _MAX_FNAME, lpExt, _MAX_EXT);
	lpDrive[2] = (TCHAR)'\\';
	lpDrive[3] = (TCHAR)'\0';

	// Watch directory for file creation & deletion.
	change_handles[0] = FindFirstChangeNotification(
		dir,
		FALSE,
		FILE_NOTIFY_CHANGE_FILE_NAME);

	// Verify handles were added correctly.
	if (change_handles[0] == INVALID_HANDLE_VALUE)
	{
		ExitProcess(GetLastError());
	}

	// Watch subtree for directory creation & deletion.
	change_handles[1] = FindFirstChangeNotification(
		lpDrive,
		TRUE,
		FILE_NOTIFY_CHANGE_DIR_NAME);

	// Verify handles were added correctly.
	if (change_handles[1] == INVALID_HANDLE_VALUE)
	{
		ExitProcess(GetLastError());
	}

	// Validation check of handles
	if ((change_handles[0] == NULL) || (change_handles[1] == NULL))
	{
		ExitProcess(GetLastError());
	}

	while (TRUE)
	{
		// Wait for notification.
		//std::cout << "Waiting for notification.." << std::endl;

		wait_status = WaitForMultipleObjects(2, change_handles, FALSE, INFINITE);

		switch (wait_status)
		{
		case WAIT_OBJECT_0:
			// File was created, renamed or deleted.
			std::cout << "File was created, renamed or deleted." << std::endl;
			break;
		case WAIT_OBJECT_0 + 1:
			// Directory was created, renamed or deleted.
			std::cout << "Directory was created, renamed or deleted." << std::endl;
			break;
		case WAIT_TIMEOUT:
			// Timeout occured.
			std::cout << "Timeout occured." << std::endl;
			break;
		}
	}
}