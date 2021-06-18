@echo off

set mysqlpath=%1
set folderpath=%2
set filepath=%3
set host=%4
set pass=%5
set dbname=%6


set port=3306
set user=root

if not exist %folderpath% (
	md %folderpath%
)

set backupFullPath=%folderpath%%filepath%.sql

%mysqlpath%mysqldump -h%host% -P%port% -u%user% -p%pass% -c --add-drop-table %dbname% > %backupFullPath%

choice /t 5 /d y /n >nul

exit
@echo on