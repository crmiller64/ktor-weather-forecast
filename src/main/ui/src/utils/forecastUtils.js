// A collection of utilities used to format forecast data received from the KTOR backend.

/**
 * 
 * @param {string} dateTimeString ISO-8601 format string representation of a date.
 * @returns the time from the given datetime string formatted in short form.
 */
export const getTime = dateTimeString => {
    const d = new Date(dateTimeString);
    const o = new Intl.DateTimeFormat([], { timeStyle: "short" });
    return o.format(d);
}

/**
 * 
 * @param {string} dateTimeString ISO-8601 format string representation of a date.
 * @returns the date from the given datetime string formatted in short form.
 */
export const getDate = dateTimeString => {
    const d = new Date(dateTimeString);
    const o = new Intl.DateTimeFormat([], { dateStyle: "short" });
    return o.format(d);
}

/**
 * 
 * @param {string} dateTimeString ISO-8601 format string representation of a date.
 * @returns the date and time from the given datetime string formatted in full form.
 */
export const getDateTime = dateTimeString => {
    const d = new Date(dateTimeString);
    const o = new Intl.DateTimeFormat([], { dateStyle: "full", timeStyle: "short" });
    return o.format(d);
}

/**
 * 
 * @param {number} temperature the temperature.
 * @param {string} temperatureUnit the abbreviation for the unit used to measure the temperature (i.e. "F" for farenheit).
 * @returns a formatted string for the given temperature and temperature unit.
 */
export const getTemperature = (temperature, temperatureUnit) => `${Math.round(temperature)}°${temperatureUnit}`

/**
 * 
 * @param {number} windSpeed the wind speed.
 * @param {string} windDirection the wind direction abbreviation (i.e. "NW" for a direction of north west).
 * @param {string} windUnit the abbreviation for the unit used to measure wind speed (i.e. "mph" for miles per hour).
 * @returns a formatted string for the given wind speed, direction, and unit.
 */
export const getWind = (windSpeed, windDirection, windUnit) => `${Math.round(windSpeed)} ${windUnit} ${windDirection}`