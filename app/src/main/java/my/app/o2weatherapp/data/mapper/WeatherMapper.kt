package my.app.o2weatherapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import my.app.o2weatherapp.data.remote.CurrentWeatherDto
import my.app.o2weatherapp.data.remote.ForecastItemDto
import my.app.o2weatherapp.domain.model.ForecastDay
import my.app.o2weatherapp.domain.model.WeatherInfo
import java.time.Instant
import java.time.ZoneId

fun CurrentWeatherDto.toDomain(): WeatherInfo {
    return WeatherInfo(
        name = cityName,
        temperature = main.temp,
        humidity = main.humidity,
        description = weather.firstOrNull()?.description.orEmpty(),
        iconUrl = "https://openweathermap.org/img/wn/${weather.firstOrNull()?.icon}@2x.png"
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun List<ForecastItemDto>.toDomainList(): List<ForecastDay> {
    return this
        .groupBy { item ->
            val instant = Instant.ofEpochSecond(item.timestamp)
            val date = instant.atZone(ZoneId.systemDefault()).toLocalDate()
            date
        }
        .map { (date, items) ->
            val avgTemp = items.map { it.main.temp }.average()
            val minTemp = items.minOf { it.main.tempMin }
            val maxTemp = items.maxOf { it.main.tempMax }
            val firstWeather = items.firstOrNull()?.weather?.firstOrNull()

            ForecastDay(
                date = date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond(),
                temperatureDay = avgTemp,
                temperatureMin = minTemp,
                temperatureMax = maxTemp,
                description = firstWeather?.description.orEmpty(),
                iconUrl = "https://openweathermap.org/img/wn/${firstWeather?.icon}@2x.png"
            )
        }
}