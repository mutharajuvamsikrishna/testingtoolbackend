
FROM openjdk:17-jdk-alpine


WORKDIR /app


COPY target/*.jar /app/automation.jar


ENTRYPOINT exec java $JAVA_OPTS -jar automation.jar