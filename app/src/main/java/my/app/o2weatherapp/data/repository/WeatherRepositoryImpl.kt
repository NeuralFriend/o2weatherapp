package my.app.o2weatherapp.data.repository

import my.app.o2weatherapp.data.mapper.toDomain
import my.app.o2weatherapp.data.remote.WeatherApi
import my.app.o2weatherapp.domain.model.ForecastDay
import my.app.o2weatherapp.domain.model.WeatherInfo
import my.app.o2weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    private val apiKey = "e1dc7d86bf84e048ea65f4502a5de381"

    override suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherInfo {
        return api.getCurrentWeather(lat, lon, apiKey).toDomain()
    }

    override suspend fun getWeeklyForecast(lat: Double, lon: Double): List<ForecastDay> {
        return api.getWeeklyForecast(lat, lon, apiKey = apiKey).days.map { it.toDomain() }
    }
}