FROM eclipse-temurin:21-jdk-alpine AS api-generator
WORKDIR /app
COPY appointments-service/ .
RUN chmod +x gradlew
RUN ./gradlew compileJava --no-daemon

FROM node:20-alpine
WORKDIR /app
COPY appointments-web/ .
RUN npm install

RUN rm -rf src/app/core/api && mkdir -p src/app/core/api
RUN apk add --no-cache openjdk21-jre-headless
COPY --from=api-generator /app/build/classes/java/main/META-INF/swagger/*.yml swagger/openapi.yml
RUN npx @openapitools/openapi-generator-cli generate -g typescript-angular \
    -i swagger/openapi.yml -o src/app/core/api

EXPOSE 4200
CMD ["npx", "ng", "serve", "--host", "0.0.0.0", "--port", "4200", "--proxy-config", "src/proxy.docker.conf.json"]
