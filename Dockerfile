FROM maven:3.8.3-jdk-11-slim AS build
WORKDIR /build

COPY pom.xml .

VOLUME /root/.m2

RUN mvn dependency:resolve

COPY . .

RUN mvn clean package

FROM openjdk:11-jre-slim

WORKDIR /api

COPY --from=build /build/target/site-api-0.0.1-SNAPSHOT.jar ./api.jar
