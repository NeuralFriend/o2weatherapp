package my.app.o2weatherapp.data.mapper

import my.app.o2weatherapp.data.remote.CurrentWeatherDto
import my.app.o2weatherapp.data.remote.ForecastDayDto
import my.app.o2weatherapp.domain.model.ForecastDay
import my.app.o2weatherapp.domain.model.WeatherInfo

fun CurrentWeatherDto.toDomain(): WeatherInfo {
    return WeatherInfo(
        city = cityName,
        temperature = main.temp,
        humidity = main.humidity,
        description = weather.firstOrNull()?.description.orEmpty(),
        iconUrl = "https://openweathermap.org/img/wn/${weather.firstOrNull()?.icon}@2x.png"
    )
}

fun ForecastDayDto.toDomain(): ForecastDay {
    return ForecastDay(
        date = timestamp,
        temperatureDay = temp.day,
        temperatureMin = temp.min,
        temperatureMax = temp.max,
        description = weather.firstOrNull()?.description.orEmpty(),
        iconUrl = "https://openweathermap.org/img/wn/${weather.firstOrNull()?.icon}@2x.png"
    )
}