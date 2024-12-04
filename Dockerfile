FROM openjdk:17-jdk-slim

RUN apt update && apt install -y curl && rm -rf /var/lib/apt/lists/*

VOLUME /tmp

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]