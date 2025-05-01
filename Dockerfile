# Build Stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime Stage
FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose application and PostgreSQL ports
EXPOSE 5432

# Set PostgreSQL environment variables
ENV DATABASE_URL=jdbc:postgresql://192.168.94.33:5432/sundaram
ENV DATABASE_USER=postgres
ENV DATABASE_PASSWORD=rohith

ENTRYPOINT ["java", "-jar", "app.jar"]
