@echo off

set user=root

set mongopath=%1
set folderpath=%2
set filepath=%3
set host=%4
set dbname=%5

if not exist %folderpath% (
	md %folderpath%
)

set backupFullPath=%folderpath%%filepath%

%mongopath%mongodump -h %host% -d %dbname% -o %backupFullPath%

choice /t 5 /d y /n >nul

exit
@echo on