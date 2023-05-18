FROM maven:3.8.3-jdk-11-slim AS build
WORKDIR /api

COPY pom.xml .
RUN mvn dependency:resolve

COPY . .

RUN mvn clean package

FROM openjdk:11-jre-slim
WORKDIR /api

COPY --from=build /api/target/site-api-0.0.1-SNAPSHOT.jar ./api.jar
