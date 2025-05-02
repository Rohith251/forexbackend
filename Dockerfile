# Build Stage
FROM maven:3.8.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose application port
EXPOSE 8081

# Set environment variables (if needed for default behavior)
ENV DATABASE_URL=jdbc:postgresql://host.docker.internal:5432/sundaram
ENV DATABASE_USER=postgres
ENV DATABASE_PASSWORD=rohith

ENTRYPOINT ["java", "-jar", "app.jar"]
