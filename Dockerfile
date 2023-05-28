FROM maven:3.8.3-jdk-11-slim AS build
WORKDIR /build

COPY pom.xml .

RUN mvn --batch-mode dependency:resolve-plugins dependency:go-offline --fail-never

COPY . .

RUN mvn --batch-mode -DskipTests package

FROM openjdk:11-jre-slim
WORKDIR /api

COPY --from=build /build/target/site-api-0.0.1-SNAPSHOT.jar ./api.jar
