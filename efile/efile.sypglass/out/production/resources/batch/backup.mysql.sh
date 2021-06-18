#!/bin/bash
#auto backup mysql db
#by authors ypq 2021
#define backup path
#数据库端口号
host=3306
#用户名
user=root
if [ $UID -ne 0 ];then
	echo "Must to be use root for exec shell."
	exit
fi

if [ ! -d $BAK_DIR ];then
	mkdir -p $BAK_DIR
	echo -e "\033[32mThe $BAK_DIR Create Successfully!\033[0m"
else
	echo "This $BAK_DIR is exists..."
fi

#Mysql BACKUP COMMAND
$MYSQLDUMP -h$MYSQLDB_HOST -P$host -u$user -p$MYSQLDB_PASSWORD $MYSQLDB_NAME>$BAK_DIR/$FILENAME.sql

if [ $? -eq 0 ];then
	echo -e "\033[32mThe Mysql Backup $MYSQLDB_NAME Successfully!\033[0m"
else
	echo -e "\033[32mThe Mysql Backup $MYSQLDB_NAME Failed,please check.\033[0m"

fi
