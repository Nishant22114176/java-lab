FROM eclipse-temurin:25-jre
WORKDIR /app
COPY target/java25-cyber-console.jar app.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=3000"]
