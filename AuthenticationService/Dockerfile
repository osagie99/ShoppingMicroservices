FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} authenticationService.jar

ENTRYPOINT ["java", "-jar", "/authenticationService.jar"]

EXPOSE 9000