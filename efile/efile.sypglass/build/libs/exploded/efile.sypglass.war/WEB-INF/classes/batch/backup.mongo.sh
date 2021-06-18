#!/bin/bash
#auto backup MongoDB db
#by authors ypq 2021
#define backup path

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

#MongoDB BACKUP COMMAND
$MONGODUMP -h $MONGO_HOST -d $MONGODB_NAME -o $BAK_DIR

if [ $? -eq 0 ];then
	echo -e "\033[32mThe MongoDB Backup $MONGODB_NAME Successfully!\033[0m"
else
	echo -e "\033[32mThe MongoDB Backup $MONGODB_NAME Failed,please check.\033[0m"

fi
