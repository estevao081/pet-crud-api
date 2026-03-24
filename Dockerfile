FROM maven:3.9.9-eclipse-temurin-21 AS builder

RUN apt-get-update
RUN apt-get-install openjdk-21-jdk -y
COPY . .

RUN apt-get-install maven -y
RUN mvn clean install

FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY --from=builder /target/pet-crud-api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "pet-crud-api.jar"]