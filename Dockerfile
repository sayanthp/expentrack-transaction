# Use a base image with Java 11 (or your preferred version)
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
# Replace 'target/transaction-service-*.jar' with the actual JAR file path produced by your build
COPY target/expentrack-transaction-service-1.0.0-SNAPSHOT.jar /app/transaction-service.jar

# Expose the port the app will run on (as per your server configuration)
EXPOSE 8084

# Set environment variables (optional, adjust as needed)
# You can inject sensitive data like DB username/password during container startup
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

# Command to run the Spring Boot app
ENTRYPOINT ["java", "-jar", "transaction-service.jar"]
