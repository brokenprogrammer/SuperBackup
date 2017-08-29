#ifndef DIRECTORYWATCHER_H
#define DIRECTORYWATCHER_H
#pragma once

class DirectoryWatcher
{
public:
	DirectoryWatcher(const char* directory, void(*action)());
	~DirectoryWatcher();

	void watch();

private:
	const char* directory;
	void (*action)();

	// TODO: Add function pointer member that acts as the 
	//	action for when a change was noticed.
};

#endif
