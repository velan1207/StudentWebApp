FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM tomcat:10.1-jdk21
COPY --from=build /app/target/StudentWebApp.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
