## Production DokerFile
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /home/app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:17
WORKDIR /app
COPY --from=build /home/app/target/spring-boot-lms-1.0.jar spring-boot-lms.jar
ENTRYPOINT ["java", "-jar", "spring-boot-lms.jar"]
EXPOSE 9797