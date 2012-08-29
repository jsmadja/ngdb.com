#!/bin/bash
cd
/opt/apache-tomcat-7.0.27/bin/shutdown.sh
#rm -rf /opt/apache-tomcat-7.0.27/temp/javamelody
git pull
mvn clean compile package
rm -rf /opt/apache-tomcat-7.0.27/temp/com*
rm -rf /opt/apache-tomcat-7.0.27/work/Catalina/localhost/
rm -rf /opt/apache-tomcat-7.0.27/webapps/ROOT*
cp target/ngdb.war /opt/apache-tomcat-7.0.27/webapps/ROOT.war
/opt/apache-tomcat-7.0.27/bin/startup.sh

