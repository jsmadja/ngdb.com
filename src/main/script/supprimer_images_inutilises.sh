#!/bin/bash
for line in $(file -i `find /ngdb/images/articles -type f` | grep -v "medium\|small\|high" | cut -d':' -f1) 
do
	echo "SELECT * FROM Picture WHERE url ='$line';" > /tmp/zop.sql
	a=$(mysql --user=ngdb --password=ngdb ngdb < /tmp/zop.sql | wc -l)
	if [ $a -eq 0 ]
	then
		article_id=$(echo $line | cut -d'/' -f5)
		image_id=$(echo $line | cut -d'/' -f6 | cut -d'.' -f1)
		rm -rfv /ngdb/images/articles/$article_id/$image_id*
		echo $line
	fi 
done
