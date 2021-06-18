@echo off

set host=localhost
set pass=
set dbname=yp
set backupfolder=D:\backup\mysql\
set mysqlpath="D:\wampserver\bin\mysql\mysql5.5.20\bin\"
set file = %1

set hour=%time:~0,2%
if "%time:~0,1%"==" " set hour=0%time:~1,1%
set now=%Date:~0,4%%Date:~5,2%%Date:~8,2%%hour%%Time:~3,2%%Time:~6,2%

set port=3306
set user=root

if not exist %backupfolder% (
	md %backupfolder%
)

set backupfile=%backupFolder%%dbname%-%1.sql

%mysqlpath%mysqldump -h%host% -P%port% -u%user% -p%pass% -c --add-drop-table %dbname% > %backupfile%

@echo on