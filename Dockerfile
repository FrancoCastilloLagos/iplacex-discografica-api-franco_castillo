# Stage 1: Build con Gradle
FROM gradle:8.3.3-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Stage 2: Runtime con OpenJDK 21
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/discografia-1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
