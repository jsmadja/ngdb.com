for line in $(file -i `find /ngdb/images/articles -type f` | grep -v "medium\|small\|high" | cut -d':' -f1) 
do
	echo "SELECT * FROM Picture WHERE url ='$line';" > zop.sql
	a=$(mysql --user=ngdb --password=ngdb ngdb < zop.sql | wc -l)
	if [ $a -eq 0 ] 
	then
		article_id=$(echo "$line" | cut -d'/' -f5)
		article_dir="/ngdb/images/articles/$article_id"
		#rm -rf $article_dir
	fi
done

for line in $(file -i `find /ngdb/images/shop-items -type f` | grep -v "medium\|small\|high" | cut -d':' -f1)
do
        echo "SELECT * FROM Picture WHERE url ='$line';" > zop.sql
        a=$(mysql --user=ngdb --password=ngdb ngdb < zop.sql | wc -l)
        if [ $a -eq 0 ]
        then
                article_id=$(echo "$line" | cut -d'/' -f5)
                article_dir="/ngdb/images/shop-items/$article_id"
                echo $article_dir
		#rm -rf $article_dir
        fi 
done
