FROM tomcat:8.5-jre8  
MAINTAINER portiz

RUN echo "export JAVA_OPTS=\"-Dapp.env=calnotify\"" > /usr/local/tomcat/bin/setenv.sh  
COPY ./calnotify.war /usr/local/tomcat/webapps/calnotify.war
COPY ./server.xml /usr/local/tomcat/conf/
CMD ["catalina.sh", "run"]
