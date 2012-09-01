#!/bin/bash

rm -rf *.tar.gz

home=/home/neogeodb-admin 
home_script=$home/src/main/script 

filename=`date "+%Y_%m_%d"`

echo "preparing cadaver input ..." 
sed s/__filename__/`date "+%Y_%m_%d"`.tar.gz/ <$home_script/restore.cadaver.script > $home_script/restore.cadaver.script.seded

echo "downloading backup file from dumptruck ..." 
cadaver -t https://dav.dumptruck.goldenfrog.com/r/giganews.com < $home_script/restore.cadaver.script.seded

exit 0;

echo "unpack backup file ..."
sudo tar zxf *.tar.gz

echo "removing old backup ..."
sudo rm -rf /ngdb_bak

echo "backuping actual data ..."
sudo mv /ngdb /ngdb_bak

echo "restoring filesystem ..."
sudo mv ngdb /

echo "restoring database ..."
mysql -uroot -pm1710calos ngdb < sql/$filename.sql

