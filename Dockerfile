FROM openjdk:8-jdk-alpine
LABEL maintainer="Yash Khatri"
ADD target/identity-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]