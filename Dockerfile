FROM openjdk:17-jdk-alpine
EXPOSE 8080
VOLUME /tmp
COPY build/libs/*.jar luck-lottery-server.jar
ENTRYPOINT ["java", "-jar", "/ohsoontaxi-server.jar"]