# ---------- Stage 1: Build Stage ----------
# Use Maven image with JDK 21 (Eclipse Temurin) to build the application
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file (pom.xml) first to leverage Docker layer caching
COPY pom.xml .

# Download and cache all project dependencies for faster builds
RUN mvn dependency:go-offline -B

# Copy the application source code into the container
COPY src ./src

# Build the application and package it as a JAR file
RUN mvn clean package


# ---------- Stage 2: Runtime Stage ----------
# Use a lightweight OpenJDK 21 image to run the packaged application
FROM openjdk:21-jdk AS runner

# Set the working directory for the runtime container
WORKDIR /app

# Copy the built JAR file from the builder stage to the runtime stage
COPY --from=builder ./app/target/analytics_service-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the application port (use the same port configured in Spring Boot)
EXPOSE 4002

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
