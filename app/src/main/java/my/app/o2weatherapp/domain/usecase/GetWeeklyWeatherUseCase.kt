package my.app.o2weatherapp.domain.usecase

import my.app.o2weatherapp.domain.model.ForecastDay
import my.app.o2weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeeklyForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): List<ForecastDay> {
        return repository.getWeeklyForecast(lat, lon)
    }
}