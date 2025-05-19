FROM openjdk:17-jdk-alpine
EXPOSE 8080
VOLUME /tmp
COPY build/libs/*.jar ohsoontaxi.jar
ENTRYPOINT ["java", "-jar", "/ohsoontaxi.jar"]