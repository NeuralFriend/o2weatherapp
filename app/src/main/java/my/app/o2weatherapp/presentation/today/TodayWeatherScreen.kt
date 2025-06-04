package my.app.o2weatherapp.presentation.today

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import my.app.o2weatherapp.presentation.UiState
import my.app.o2weatherapp.ui.components.RequestLocationPermission

@Composable
fun TodayWeatherScreen(onWeekClick: () -> Unit) {
    val viewModel: TodayViewModel = hiltViewModel()
    val state by viewModel.weatherState.collectAsState()

    val alreadyRequested = remember { mutableStateOf(false) }

    RequestLocationPermission(
        onPermissionGranted = {
            if (!alreadyRequested.value) {
                alreadyRequested.value = true
                viewModel.loadWeather()
            }
        }
    )

    when (state) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is UiState.Error -> {
            val message = (state as UiState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Ошибка: $message")
            }
        }
        is UiState.Success -> {
            val weather = (state as UiState.Success).data
            Column(
                modifier = Modifier
                    .fillMaxSize().background(color = Color.Cyan)
                    .padding(16.dp)
            ) {
                Text("Город: ${weather.name}")
                Text("Температура: ${weather.temperature}°C")
                Text("Влажность: ${weather.humidity}%")
                Text("Описание: ${weather.description}")
                AsyncImage(
                    model = weather.iconUrl,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}