import '@vaadin/details/theme/lumo/vaadin-details.js';

import { getTime, getDate, getTemperature, getWind } from "./utils/forecastUtils";

const HourlyForecast = props => {
    const { hourlyForecasts } = props;

    const getPercent = n => {
        return `${Math.round(n * 100)}%`
    }

    const forecasts = () => {
        if (hourlyForecasts) {
            return hourlyForecasts.map((forecast, index) =>
                <div key={index} className="list-group-item">
                    <div className="d-flex w-100 justify-content-between">
                        <h5 className="mb-1">{getTime(forecast.date)}</h5>
                        <small>{getDate(forecast.date)}</small>
                    </div>
                    <div className="d-flex w-100 justify-content-between">
                        <p className="mb-1">{forecast.description}</p>
                        <small>{getTemperature(forecast.temperature, forecast.temperatureUnit)}</small>
                    </div>
                    <vaadin-details>
                        <div slot="summary">Details</div>
                        <small className="d-block">
                            <span className="fw-bold">Feels Like: </span>
                            {getTemperature(forecast.feelsLike, forecast.temperatureUnit)}
                        </small>
                        <small className="d-block">
                            <span className="fw-bold">Wind: </span>
                            {getWind(forecast.windSpeed, forecast.windDirection, forecast.windUnit)}
                        </small>
                        <small className="d-block">
                            <span className="fw-bold">Chance of Precipitation: </span>
                            {getPercent(forecast.probabilityOfPrecipitation)}
                        </small>
                    </vaadin-details>
                </div>
            );
        }
    }

    return (
        <div className="card shadow">
            <div className="card-body">
                <h5 className="card-title">Hourly Forecast</h5>
                <div className="list-group">
                    {forecasts()}
                </div>
            </div>
        </div>
    );
}

export default HourlyForecast;