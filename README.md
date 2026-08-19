# Appointments App
Application that allows you to book, view, and cancel health appointments with practitioners.

## Navigation

- **[Preview](docs/PREVIEW.md#preview)**
- **[System context](docs/PREVIEW.md#system-context)**
- **[Running using docker compose](#running-using-docker-compose)**
- [Running manually](docs/RUN_MANUALLY.md#running-manually)
- [Running tests](#running-backend-tests)

## Running using docker compose

Navigate to `appointments-app`

```shell
docker compose up -d
```

and navigate to `http://localhost:4200` in your browser.

## Running backend tests

Navigate to `appointments-service`

### Running unit tests

```
./gradlew test
```

### Running integration testing

```
./gradlew integrationTest
```