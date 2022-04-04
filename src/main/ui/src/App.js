import "./App.css";

import { useEffect, useState } from "react";
import axios from "axios";

import Location from "./Location";
import DailyForecast from "./DailyForecast";
import HourlyForecast from "./HourlyForecast";

function App() {
    const [forecast, setForecast] = useState(null)
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    // set port to same as host for heroku deployment
    const port = process.env.PORT || 9090;

    const handleSubmit = (city, state) => {
        setIsLoading(true);
        setForecast(null);
        setError(null);
        // send request to KTOR web-service
        axios.get(`http://localhost:${port}/forecast?city=${city}&state=${state}`)
            .then(response => {
                console.log(response.data);
                setForecast(response.data);
            })
            .catch(error => {
                console.log("Error retrieving weather data.", error.response);
                setError({ message: error.response.data, error: error.response })
            })
            .then(() => {
                setIsLoading(false);
            });
    }

    return (
        <div className="container my-5">
            {error &&
                <div className="alert alert-danger" role="alert">
                    {error.message}
                </div>
            }
            <h1>Weather Forecast</h1>
            <div className="row mt-1">
                <div className="col-6">
                    <Location
                        onSubmit={(city, state) => handleSubmit(city, state)}
                    />
                </div>
            </div>
            {isLoading &&
                <div className="spinner-border text-primary" role="status">
                    <span className="visually-hidden">Loading...</span>
                </div>
            }
            {!isLoading && forecast &&
                <div className="row mt-3">
                    <div className="col-6">
                        <HourlyForecast
                            hourlyForecasts={forecast.hourly}
                        />
                    </div>
                    <div className="col-6">
                        <DailyForecast
                            dailyForecasts={forecast.daily}
                        />
                    </div>
                </div>
            }
        </div>
    );
}

export default App;
