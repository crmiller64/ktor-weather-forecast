# Ktor + React Weather Forecast

A full stack weather forecast web-app built with Ktor and React.

Credit to Marco Gomiero's guide
to [Structuring a Ktor project](https://www.marcogomiero.com/posts/2021/ktor-project-structure/) - without such a
resource this project would have been much tougher than it was.

## APIs

This project makes use of APIs from [Mapbox](https://www.mapbox.com/) and [OpenWeather](https://openweathermap.org/), as
such you will need to have valid API keys with both services before you can run this app. These services where chosen as
they offer free plans.

## Building

### Full App

Execute the following command from the top-level directory of the project:

`./gradlew build`

This build will build the frontend subproject using
the [Frontend Gradle Plugin](https://github.com/Siouan/frontend-gradle-plugin), then copy the assets from the frontend
into the backend resources, which will then be served from the Ktor backend.

### Backend Only

If you want to build just the backend subproject you can pass a `skipFrontend` arg to the `build` command noted in
the **Building the Full App** section above. To build just the backend code execute the following command from the
top-level directory of the project:

`./gradlew :backend:build -PskipFrontend`

#### Generating Backend API Documentation

The `oas3.yaml` file contains the [OpenAPI](https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.0.3.md)
spec for the backend API. This project uses
the [Gradle Swagger Generator Plugin](https://github.com/int128/gradle-swagger-generator-plugin) to generate Swagger
documentation from the `oas3.yaml` file. The documentation generation process is set up to be run with every build of
the backend project.

To rebuild the backend API docs manually execute the following command from the top-level directory of the project:

`./gradlew :backend:generateSwaggerUI`

To access the Swagger docs for the backend open a web browser to http://localhost:9090/doc when the backend app is
running.

### Frontend Only

Execute the following command from the `frontend` directory of the project:

`npm build`

## Running

### Full App

1. Execute the following command from the top-level directory of the project to run the app:
    1. `MAPBOX_TOKEN=xxxxxx OPEN_WEATHER_TOKEN=xxxxxx ./gradlew run -Dio.ktor.development=true`
        1. Where the `MAPBOX_TOKEN` and `OPEN_WEATHER_TOKEN` environment variables are set to your access tokens. Note
           that the `-Dio.ktor.development=true` arg will run the Ktor server
           in [development mode](https://ktor.io/docs/development-mode.html).
2. Open a web browser to: http://localhost:9090/

#### Defining Access Tokens in the Configuration File

You may also define the Mapbox and OpenWeather access tokens explicitly in the `application.conf` file as follows:

```
mapbox {
    accessToken = PASTE_ACCESS_TOKEN_HERE
    accessToken = ${?MAPBOX_TOKEN}
}
openWeather {
    accessToken = PASTE_ACCESS_TOKEN_HERE
    accessToken = ${?OPEN_WEATHER_TOKEN}
}
```

Then you can run the app without the need to define the access tokens as environment variables on the command line:

`./gradlew run -Dio.ktor.development=true`

### Backend Only

If you want to run just the backend subproject you can pass a `skipFrontend` arg to the `run` command noted in the
**Running the Full App** section above. For example, the following command will set the needed access tokens, run the
server in development mode, _and_ skip the frontend build:

`MAPBOX_TOKEN=xxxxxx OPEN_WEATHER_TOKEN=xxxxxx ./gradlew :backend:run -Dio.ktor.development=true -PskipFrontend`

After running the above command open a web browser to:
http://localhost:9090/doc

### Frontend Only

Execute the following command from the frontend directory of the project:

`npm run`

Open a web browser to:
http://localhost:3000/