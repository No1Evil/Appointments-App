## Running manually

### Prerequisites

* Docker
* Java 21
* Node.js 20+
* Angular CLI

### Prepare database (PostgreSQL 16.1)

```
docker run --name appointments-db -e POSTGRES_PASSWORD=test -p 5432:5432 -d postgres:16.1
```

This command will start PostgreSQL container listening for connection on **localhost:5432**.
Username is **postgres** and password is **test**.

If for some reason you have to re-initialize Postgres container from the scratch then run:
`docker rm -fv appointments-db` and execute `docker run` command again, or look for the container
id through `docker ps`.

### Running the backend application

Under `appointments-service` folder execute the following command:

```
./gradlew run
```

### Running the frontend application

Navigate to `appointments-web` folder and before the first launch install required dependencies:
```
npm install
```

**Generate the API from the built backend**

```
npx @openapitools/openapi-generator-cli generate -g typescript-angular \
-i ../appointments-service/build/classes/java/main/META-INF/swagger/appointments-application-v0.1.yml -o src/app/core/api
```

When dependencies are installed and API is generated run the frontend application:
```
ng serve
```

and navigate to `http://localhost:4200` in your browser.
