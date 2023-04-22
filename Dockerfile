FROM maven:3.8.3-jdk-11-slim AS build
COPY pom.xml /app/
WORKDIR /app

RUN mvn dependency:resolve

COPY . /app

RUN mvn clean package

FROM openjdk:11-jre-slim
COPY --from=build /app/target/site-api-0.0.1-SNAPSHOT.jar /app.jar
