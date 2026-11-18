# Multi-stage Dockerfile for Spring Boot on Render
FROM maven:3.8.5-openjdk-8-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=build /app/target/campushub-*.jar app.jar
EXPOSE 8080
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "app.jar"]
