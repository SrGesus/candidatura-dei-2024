# DEI Candidate Management System - CMS

The DEI Candidade Management System (CMS) is a platform that aims to facilitate the management of information regarding studentship candidacies and was developed as a pratical exercise within the scope of my candidacy to one of such studentship.

The platform has been developed with a Java backend using [Spring-boot](https://spring.io/), and a frontend in [Vue.js](https://vuejs.org/) making much use of [Vuetify](https://vuetifyjs.com/en/) components.

## Requirements

- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Node 14+](https://nodejs.org/en/) ([Node Version Manager](https://github.com/nvm-sh/nvm) recommended)
- [Docker](https://www.docker.com/)

## Local Deployment

Clone the project

```bash
git clone git@github.com:SrGesus/candidatura-dei-2024.git
```

Go to the project source directory

```bash
cd candidatura-dei-2024/src
```

### PostgreSQL

To run the database with Docker Compose, run the following command:
```bash
docker compose up
```

Alternatively without Compose:
```bash
docker run -d \
  --name postgres \
  -p 7654:5432 \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_DB=dmsdb \
  postgres:14-alpine
```

### Backend

Create a copy of the `application.properties` file.

```bash
cp ./backend/src/main/resources/application.properties.example ./backend/src/main/resources/application.properties
```

Use Maven to run the backend:
```bash
cd ./backend
mvn clean spring-boot:run
```

### Frontend

Now, you need to install the dependencies:

Create a copy of the `example.env` file named `.env`.

```bash
cp ./frontend/example.env ./frontend/.env
```

```bash
cd ./frontend
npm i
```

To run the frontend, run the following command:

```bash
npm run dev
```

## Access the Database

In order to access the database, you can use the following command:

```bash
# psql postgres://USER:PASSWORD@HOSTNAME:PORT/DB
psql postgres://postgres:postgres@localhost:7654/dmsdb
```
