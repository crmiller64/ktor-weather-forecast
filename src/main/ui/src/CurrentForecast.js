import { getDateTime, getTemperature, getWind } from "./utils/forecastUtils";

const CurrentForecast = props => {
    const { forecast } = props;

    return (
        <div className="card shadow">
            <div className="card-body">
                <h5 className="card-title">Current Conditions</h5>
                <div>
                    <span className="fs-6 mb-1">{getDateTime(forecast.date)}</span>
                    <p className="mb-1">{forecast.description}</p>
                    <small className="d-block">
                        <span className="fw-bold">Temperature: </span>
                        {getTemperature(forecast.temperature, forecast.temperatureUnit)}
                    </small>
                    <small className="d-block">
                        <span className="fw-bold">Feels Like: </span>
                        {getTemperature(forecast.feelsLike, forecast.temperatureUnit)}
                    </small>
                    <small className="d-block">
                        <span className="fw-bold">Wind: </span>
                        {getWind(forecast.windSpeed, forecast.windDirection, forecast.windUnit)}
                    </small>
                </div>
            </div>
        </div>
    );
}

export default CurrentForecast;