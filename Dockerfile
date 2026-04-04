FROM tomcat:10.1-jdk21
COPY target/StudentWebApp.war /usr/local/tomcat/webapps/StudentWebApp.war
EXPOSE 8080