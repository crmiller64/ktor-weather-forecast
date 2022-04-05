import '@vaadin/details/theme/lumo/vaadin-details.js';

import { getTime, getDate, getTemperature, getWind } from "./utils/forecastUtils";

const DailyForecast = props => {
    const { dailyForecasts } = props;

    const forecasts = () => {
        if (dailyForecasts) {
            return dailyForecasts.map((forecast, index) =>
                <div key={index} className="list-group-item">
                    <div className="d-flex w-100 justify-content-between">
                        <h5 className="mb-1">{forecast.name}</h5>
                        <small>{getDate(forecast.date)}</small>
                    </div>
                    <div className="d-flex w-100 justify-content-between">
                        <p className="mb-1">{forecast.description}</p>
                        <small>High: {getTemperature(forecast.highTemperature, forecast.temperatureUnit)}</small>
                    </div>
                    <vaadin-details>
                        <div slot="summary">Details</div>
                        <small className="d-block">
                            <span className="fw-bold">Low: </span>
                            {getTemperature(forecast.lowTemperature, forecast.temperatureUnit)}
                        </small>
                        <small className="d-block">
                            <span className="fw-bold">Sunrise: </span>
                            {getTime(forecast.sunrise)}
                        </small>
                        <small className="d-block">
                            <span className="fw-bold">Sunset: </span>
                            {getTime(forecast.sunset)}
                        </small>
                        <small className="d-block">
                            <span className="fw-bold">Wind: </span>
                            {getWind(forecast.windSpeed, forecast.windDirection, forecast.windUnit)}
                        </small>
                        <small className="d-block">
                            <span className="fw-bold">Humidity: </span>
                            {Math.round(forecast.humidity)} &#37;
                        </small>
                    </vaadin-details>
                </div>
            );
        }
    }

    return (
        <div className="card shadow">
            <div className="card-body">
                <h5 className="card-title">Daily Forecast</h5>
                <div className="list-group">
                    {forecasts()}
                </div>
            </div>
        </div>
    );
}

export default DailyForecast;