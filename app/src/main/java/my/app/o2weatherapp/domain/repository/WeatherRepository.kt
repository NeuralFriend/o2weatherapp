package my.app.o2weatherapp.domain.repository

import my.app.o2weatherapp.domain.model.ForecastDay
import my.app.o2weatherapp.domain.model.WeatherInfo


interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherInfo
    suspend fun getWeeklyForecast(lat: Double, lon: Double): List<ForecastDay>
}