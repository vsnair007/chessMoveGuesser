# ---------- STAGE 1: Build ----------
# Use Maven image to build the project
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Set work directory inside container
WORKDIR /app

# Copy pom.xml and download dependencies first (cache optimization)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy all source code
COPY src ./src

# Build the Spring Boot JAR
RUN mvn clean package -DskipTests=false

# ---------- STAGE 2: Run ----------
# Use lightweight JDK image to run the app
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
