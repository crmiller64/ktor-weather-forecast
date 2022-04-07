import "./App.css";

import { useState } from "react";
import axios from "axios";

import Location from "./Location";
import CurrentForecast from "./CurrentForecast";
import DailyForecast from "./DailyForecast";
import HourlyForecast from "./HourlyForecast";

function App() {
    const [ forecast, setForecast ] = useState(null)
    const [ isLoading, setIsLoading ] = useState(false);
    const [ error, setError ] = useState(null);

    const handleSubmit = (city, state) => {
        setIsLoading(true);
        setForecast(null);
        setError(null);
        // send request to KTOR web-service
        axios.get(`/forecast?city=${ city }&state=${ state }`)
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
            { error &&
                <div className="alert alert-danger" role="alert">
                    { error.message }
                </div>
            }
            <h1>Weather Forecast</h1>
            <div className="row mt-1 gy-3">
                <div className="col-md-6">
                    <Location
                        onSubmit={ (city, state) => handleSubmit(city, state) }
                        placeName={ forecast ? forecast.placeName : "" }
                    />
                </div>
                { !isLoading && forecast &&
                    <div className="col-md-6">
                        <CurrentForecast
                            forecast={ forecast.current }
                        />
                    </div>
                }
            </div>
            { isLoading &&
                <div class="d-flex justify-content-center">
                    <div class="spinner-grow text-primary m-5" role="status">
                        <span class="visually-hidden">Loading...</span>
                    </div>
                </div>
            }
            { !isLoading && forecast &&
                <div className="row mt-1 gy-3">
                    <div className="col-md-6">
                        <HourlyForecast
                            hourlyForecasts={ forecast.hourly }
                        />
                    </div>
                    <div className="col-md-6">
                        <DailyForecast
                            dailyForecasts={ forecast.daily }
                        />
                    </div>
                </div>
            }
        </div>
    );
}

export default App;
