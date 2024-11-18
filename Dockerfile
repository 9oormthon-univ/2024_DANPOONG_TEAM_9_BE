FROM openjdk:21-jdk-slim
COPY build/libs/*.jar ./app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "./app.jar"]