FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY appointments-service/ .
RUN chmod +x gradlew
RUN ./gradlew build --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /home/app
COPY --from=builder /app/build/libs/*-all-optimized.jar app.jar

EXPOSE 8001
ENTRYPOINT ["java", "-jar", "app.jar"]