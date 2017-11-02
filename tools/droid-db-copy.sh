#!/bin/bash

REQUIRED_ARGS=2
PULL_DIR="./"

if [ $# -ne $REQUIRED_ARGS ]
then
echo "Usage: $0 [package_name] [db_name]"
echo "Try $0 nl.splendo.assignment.posytive SplenDb.db"
echo ""
exit 1
fi;
eval "adb -d shell 'run-as $1 ls -la /data/data/$1/databases/'"
eval "adb -d shell 'run-as $1 cp /data/data/$1/databases/$2 /data/data/$1/databases/$2-copy'"
if [ $? -ne 0 ]
then
echo "Error cloning database"
echo ""
exit 1
fi;

eval "adb -d shell 'run-as $1 chmod 666 /data/data/$1/databases/$2-copy'"
if [ $? -ne 0 ]
then
echo "Error changing clone permission"
echo ""
exit 1
fi;

eval "adb -d shell 'run-as $1 cp /data/data/$1/databases/$2-copy /sdcard/$2'"
if [ $? -ne 0 ]
then
echo "Error coping to /sdcard"
echo ""
exit 1
fi;

eval "adb -d shell 'run-as $1 rm /data/data/$1/databases/$2-copy'"
if [ $? -ne 0 ]
then
echo "Error removing clone"
echo ""
exit 1
fi;

eval "adb -d pull /sdcard/$2 $PULL_DIR"
if [ $? -ne 0 ]
then
echo "Error downloading"
echo ""
exit 1
fi;

eval "adb -d shell 'rm /sdcard/$2'"
if [ $? -ne 0 ]
then
echo "Error removing /sdcard copy"
echo ""
exit 1
fi;

exit 0
