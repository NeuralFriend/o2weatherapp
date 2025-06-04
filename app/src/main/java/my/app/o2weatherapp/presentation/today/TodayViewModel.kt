package my.app.o2weatherapp.presentation.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import my.app.o2weatherapp.domain.model.WeatherInfo
import my.app.o2weatherapp.domain.usecase.GetTodayWeatherUseCaseWeatherUseCase
import my.app.o2weatherapp.presentation.UiState
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
private val getCurrentWeather: GetTodayWeatherUseCaseWeatherUseCase
) : ViewModel() {

    private val _weatherState = MutableStateFlow<UiState<WeatherInfo>>(UiState.Loading)
    val weatherState: StateFlow<UiState<WeatherInfo>> = _weatherState.asStateFlow()

    init {
        loadWeather(1.1, 1.1)
    }

    fun loadWeather(lat: Double, lon: Double) {
        _weatherState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val result = getCurrentWeather(lat, lon)
                _weatherState.value = UiState.Success(result)
            } catch (e: Exception) {
                _weatherState.value = UiState.Error("Не удалось загрузить погоду: ${e.message}")
            }
        }
    }
}