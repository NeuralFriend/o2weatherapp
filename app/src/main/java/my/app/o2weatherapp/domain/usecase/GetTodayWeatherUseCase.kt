package my.app.o2weatherapp.domain.usecase

import my.app.o2weatherapp.domain.model.WeatherInfo
import my.app.o2weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetTodayWeatherUseCaseWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): WeatherInfo {
        return repository.getCurrentWeather(lat, lon)
    }
}