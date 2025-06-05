package my.app.o2weatherapp.presentation.week

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
import my.app.o2weatherapp.domain.model.ForecastDay
import my.app.o2weatherapp.domain.usecase.GetWeeklyForecastUseCase
import my.app.o2weatherapp.presentation.UiState
import javax.inject.Inject

@HiltViewModel
class WeekViewModel @Inject constructor(
    private val getWeeklyForecast: GetWeeklyForecastUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _forecastState = MutableStateFlow<UiState<List<ForecastDay>>>(UiState.Loading)
    val forecastState: StateFlow<UiState<List<ForecastDay>>> = _forecastState.asStateFlow()

    init {
        startAutoRefresh()
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (true) {
                loadWeeklyForecast()
                delay(60_000L)
            }
        }
    }

    fun loadWeeklyForecast() {
        safeScope.launch {
            _forecastState.value = UiState.Loading
            try {
                val location = locationTracker.getCurrentLocation()
                if (location != null) {
                    val forecast = getWeeklyForecast(location.latitude, location.longitude)
                    _forecastState.value = UiState.Success(forecast)
                } else {
                    _forecastState.value = UiState.Error("Локация недоступна")
                }
            } catch (e: Exception) {
                _forecastState.value = UiState.Error("Ошибка загрузки прогноза: ${e.message}")
            }
        }
    }
}