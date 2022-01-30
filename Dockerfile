FROM openjdk:8-jdk-alpine
MAINTAINER Vincent Bii
COPY target/jumia-1.0.war jumia-1.0.war
COPY sample.db sample.db
ENTRYPOINT ["java","-jar","/jumia-1.0.war"]