package my.app.o2weatherapp.presentation.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import my.app.o2weatherapp.core.LocationTracker
import my.app.o2weatherapp.core.extensions.safeScope
import my.app.o2weatherapp.domain.model.WeatherInfo
import my.app.o2weatherapp.domain.usecase.GetTodayWeatherUseCaseWeatherUseCase
import my.app.o2weatherapp.presentation.UiState
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    private val getCurrentWeather: GetTodayWeatherUseCaseWeatherUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _weatherState = MutableStateFlow<UiState<WeatherInfo>>(UiState.Loading)
    val weatherState: StateFlow<UiState<WeatherInfo>> = _weatherState.asStateFlow()

    init {
        startAutoRefresh()
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (true) {
                loadWeather()
                delay(60_000L)
            }
        }
    }

    fun loadWeather() {
        _weatherState.value = UiState.Loading
        safeScope.launch {
            try {
                val location = locationTracker.getCurrentLocation()
                if (location != null) {
                    val result = getCurrentWeather(location.latitude, location.longitude)
                    _weatherState.value = UiState.Success(result)
                } else {
                    _weatherState.value = UiState.Error("Локация недоступна")
                }
            } catch (e: Exception) {
                _weatherState.value = UiState.Error("Не удалось загрузить погоду: ${e.message}")
            }
        }
    }
}