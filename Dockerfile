# Use Eclipse Temurin JRE as base image
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the pre-built JAR from the local folder to the image
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]