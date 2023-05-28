FROM maven:3.8.3-jdk-11-slim

WORKDIR /app

VOLUME /root/.m2

COPY pom.xml .

RUN mvn --batch-mode dependency:resolve-plugins dependency:go-offline --fail-never

COPY . .
RUN mvn --batch-mode -DskipTests package

RUN mv target/site-api-0.0.1-SNAPSHOT.jar api.jar
