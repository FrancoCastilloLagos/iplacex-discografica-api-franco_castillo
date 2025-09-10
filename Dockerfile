# Stage 1: Build con Gradle
FROM gradle:8.8.0-jdk21 AS build
WORKDIR /app
COPY . .

# Dar permisos de ejecución a gradlew
RUN chmod +x gradlew

RUN ./gradlew build -x test

# Stage 2: Runtime con OpenJDK 21
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/iplacex-discografica-api-franco_castillo-1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
