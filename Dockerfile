#
# Build stage
#
#FROM maven:3.9.2-jdk-17 AS build
#COPY . .
#RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY /target/distribution-of-tasks-0.0.1-SNAPSHOT.jar demo.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]