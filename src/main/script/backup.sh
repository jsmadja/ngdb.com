#!/bin/bash

home=/home/neogeodb-admin
home_script=$home/src/main/script
dir=/tmp/ngdb_backup
filename=`date "+%Y_%m_%d"`

rm -rf $dir/*
mkdir -p $dir/sql

echo "sql backup ..."
mysqldump -uroot -pm1710calos --opt ngdb > $dir/sql/$filename.sql

echo "resources backup ..."
cp -rf /ngdb $dir/ngdb
cd $dir
find . -name "*_wm*" -type d -exec rm -v {} \;

echo "configuration files backup ..."
mkdir -p $dir/conf
mkdir $dir/conf/cron

cp /etc/cron.daily/suppressions_images_foireuses.sh $dir/conf/cron
cp /etc/apache2/mods-enabled/alias.conf $dir/conf

echo "compressing files ..."
tar -zcf $filename.tar.gz *

rm -rf sql
rm -rf ngdb

echo "uploading backup file ..."
lftp nmt:1234@kaddath.hd.free.fr -e "cd SATA_DISK; cd neogeodb; put $dir/$filename.tar.gz; quit;"

echo "cleaning temp directory ..."
rm -rf sql
rm -rf ngdb
rm -rf $dir

echo "backup done!"
