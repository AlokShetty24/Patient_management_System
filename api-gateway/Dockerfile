# ---------- Stage 1: Build Stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy only pom.xml first (for dependency caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build and package (skip tests for faster build)
RUN mvn clean package -DskipTests


# ---------- Stage 2: Runtime Stage ----------
FROM eclipse-temurin:21-jdk AS runner

WORKDIR /app

# Copy the packaged JAR from builder
COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the app port
EXPOSE 4004

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
