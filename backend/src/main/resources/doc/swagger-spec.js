window.swaggerSpec={
  "openapi" : "3.0.3",
  "info" : {
    "title" : "Ktor Forecast",
    "description" : "A backend to gather forecast data from the OpenWeather API.",
    "version" : "1.0.0"
  },
  "paths" : {
    "/forecast" : {
      "get" : {
        "summary" : "Get the current, hourly, and daily forecast for the given location.",
        "parameters" : [ {
          "name" : "city",
          "in" : "query",
          "description" : "The city to retrieve the forecast for.",
          "required" : true,
          "schema" : {
            "type" : "string"
          }
        }, {
          "name" : "state",
          "in" : "query",
          "description" : "The state to retrieve the forecast for.",
          "required" : true,
          "schema" : {
            "type" : "string"
          }
        } ],
        "responses" : {
          "200" : {
            "description" : "ForecastDTO",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ForecastDTO"
                }
              }
            }
          },
          "500" : {
            "description" : "InternalServerError",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/ErrorResponse"
                }
              }
            }
          }
        }
      }
    }
  },
  "components" : {
    "schemas" : {
      "ForecastDTO" : {
        "type" : "object",
        "properties" : {
          "placeName" : {
            "type" : "string"
          },
          "current" : {
            "type" : "object",
            "description" : "Contains the forecast data for the current date and time.",
            "items" : {
              "$ref" : "#/components/schemas/Current"
            }
          },
          "daily" : {
            "type" : "array",
            "description" : "Contains a list of forecast data for each day.",
            "items" : {
              "$ref" : "#/components/schemas/Day"
            }
          },
          "hourly" : {
            "type" : "array",
            "description" : "Contains a list of forecast data for each hour of the current day.",
            "items" : {
              "$ref" : "#/components/schemas/Hour"
            }
          }
        }
      },
      "Current" : {
        "type" : "object",
        "properties" : {
          "date" : {
            "type" : "string"
          },
          "temperature" : {
            "type" : "number",
            "format" : "double"
          },
          "temperatureUnit" : {
            "type" : "string"
          },
          "feelsLike" : {
            "type" : "number",
            "format" : "double"
          },
          "windSpeed" : {
            "type" : "number",
            "format" : "double"
          },
          "windDirection" : {
            "type" : "string"
          },
          "windUnit" : {
            "type" : "string"
          },
          "description" : {
            "type" : "string"
          }
        }
      },
      "Day" : {
        "type" : "object",
        "properties" : {
          "date" : {
            "type" : "string"
          },
          "name" : {
            "type" : "string"
          },
          "sunrise" : {
            "type" : "string"
          },
          "sunset" : {
            "type" : "string"
          },
          "highTemperature" : {
            "type" : "number",
            "format" : "double"
          },
          "lowTemperature" : {
            "type" : "number",
            "format" : "double"
          },
          "temperatureUnit" : {
            "type" : "string"
          },
          "windSpeed" : {
            "type" : "number",
            "format" : "double"
          },
          "windDirection" : {
            "type" : "string"
          },
          "windUnit" : {
            "type" : "string"
          },
          "description" : {
            "type" : "string"
          },
          "humidity" : {
            "type" : "number",
            "format" : "int32"
          }
        }
      },
      "Hour" : {
        "type" : "object",
        "properties" : {
          "date" : {
            "type" : "string"
          },
          "temperature" : {
            "type" : "number",
            "format" : "double"
          },
          "temperatureUnit" : {
            "type" : "string"
          },
          "feelsLike" : {
            "type" : "number",
            "format" : "double"
          },
          "windSpeed" : {
            "type" : "number",
            "format" : "double"
          },
          "windDirection" : {
            "type" : "string"
          },
          "windUnit" : {
            "type" : "string"
          },
          "description" : {
            "type" : "string"
          },
          "probabilityOfPrecipitation" : {
            "type" : "number",
            "format" : "double"
          }
        }
      },
      "ErrorResponse" : {
        "type" : "object",
        "properties" : {
          "code" : {
            "type" : "string",
            "description" : "Identifier representing the type of error that occurred."
          },
          "message" : {
            "type" : "string",
            "description" : "Description of the error."
          }
        },
        "description" : "Contains details of an error that was encountered."
      }
    }
  }
}