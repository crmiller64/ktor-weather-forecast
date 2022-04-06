# ktor-weather-forecast

## Building the Full App

Execute the following command from the top-level directory of the project:

`./gradlew build`

This build will build the frontend portion of the project using
the [frontend-gradle-plugin](https://github.com/Siouan/frontend-gradle-plugin), then copy the assets from the frontend
into the backend resources, which will then be served from the backend.

## Building the Backend Only

Execute the following command from the top-level directory of the project:

`./gradlew :backend:build -PskipFrontend`

## Building the Frontend Only

Execute the following command from the `frontend` directory of the project:

`npm build`

## Running the Full App

Execute the following command from the top-level directory of the project to run the app:
`./gradlew run --continuous`

Open a web browser to:
http://localhost:9090/

## Running the Frontend

Execute the following command from the frontend directory of the project:

`npm run`

Open a web browser to:
http://localhost:3000/

## Running the Backend

Execute the following command from the top-level directory of the project to run the app:
`./gradlew :backend:run --continuous -PskipFrontend`

Open a web browser to:
http://localhost:9090/doc

## Running the Frontend

Execute the following command from the frontend directory of the project:

`npm run`

Open a web browser to:
http://localhost:3000/