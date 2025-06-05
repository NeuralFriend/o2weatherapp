package my.app.o2weatherapp.presentation.today

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import my.app.o2weatherapp.presentation.UiState
import my.app.o2weatherapp.ui.components.RequestLocationPermission

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayWeatherScreen(onWeekClick: () -> Unit) {
    val viewModel: TodayViewModel = hiltViewModel()
    val state by viewModel.weatherState.collectAsState()
    val requestPermissionAgain = remember { mutableStateOf(false) }
    val alreadyRequested = remember { mutableStateOf(false) }

    val refreshingState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val isRefreshing = state is UiState.Loading
    val scrollState = rememberScrollState()


    RequestLocationPermission(
        forceRequest = requestPermissionAgain.value,
        onPermissionGranted = {
            alreadyRequested.value = true
            requestPermissionAgain.value = false
            viewModel.loadWeather()
        },
        onPermissionDenied = {
            alreadyRequested.value = true
            requestPermissionAgain.value = false
        }
    )

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = refreshingState,
        onRefresh = {
            coroutineScope.launch { viewModel.loadWeather() }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Прогноз на сегодня") },
                )
            },
            bottomBar = {
                Button(
                    onClick = {
                        if (!alreadyRequested.value) {
                            viewModel.loadWeather()
                        } else {
                            requestPermissionAgain.value = true
                        }
                        onWeekClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1565C0),
                        contentColor = Color.White
                    )
                ) {
                    Text("Прогноз на 7 дней", style = MaterialTheme.typography.titleMedium)
                }
            },
            containerColor = Color(0xFFE3F2FD)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    when (state) {
                        is UiState.Loading -> {
                            CircularProgressIndicator(color = Color(0xFF1976D2))
                        }

                        is UiState.Error -> {
                            val message = (state as UiState.Error).message
                            Text(
                                text = "Ошибка: $message",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        is UiState.Success -> {
                            val weather = (state as UiState.Success).data
                            Surface(
                                modifier = Modifier
                                    .padding(24.dp)
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                shape = RoundedCornerShape(20.dp),
                                shadowElevation = 12.dp,
                                tonalElevation = 6.dp,
                                color = Color.White
                            ) {
                                Column(
                                    modifier = Modifier.padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = weather.name,
                                        style = MaterialTheme.typography.headlineMedium.copy(
                                            color = Color(
                                                0xFF0D47A1
                                            )
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    AsyncImage(
                                        model = weather.iconUrl,
                                        contentDescription = weather.description,
                                        modifier = Modifier.size(96.dp)
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "${weather.temperature.toInt()}°C",
                                        style = MaterialTheme.typography.displaySmall.copy(
                                            color = Color(
                                                0xFF0D47A1
                                            )
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Влажность: ${weather.humidity}%",
                                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = weather.description.replaceFirstChar { it.uppercase() },
                                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}