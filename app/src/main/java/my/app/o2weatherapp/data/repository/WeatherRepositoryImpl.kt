package my.app.o2weatherapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Stable
import my.app.o2weatherapp.data.mapper.toDomain
import my.app.o2weatherapp.data.mapper.toDomainList
import my.app.o2weatherapp.data.remote.WeatherApi
import my.app.o2weatherapp.domain.model.ForecastDay
import my.app.o2weatherapp.domain.model.WeatherInfo
import my.app.o2weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton


@Stable
@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    private val apiKey = "35972bf38cf39e7a70eb3e94db4cd50f"

    override suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherInfo {
        return api.getCurrentWeather(lat, lon, apiKey).toDomain()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeeklyForecast(lat: Double, lon: Double): List<ForecastDay> {
        return api.getForecast(lat, lon, apiKey = apiKey)
            .list
            .toDomainList()
    }
}