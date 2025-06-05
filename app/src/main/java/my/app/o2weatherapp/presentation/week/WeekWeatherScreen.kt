package my.app.o2weatherapp.presentation.week

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import kotlinx.coroutines.launch
import my.app.o2weatherapp.presentation.UiState
import my.app.o2weatherapp.ui.components.RequestLocationPermission

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekWeatherScreen(onBackClick: () -> Unit) {
    val viewModel: WeekViewModel = hiltViewModel()
    val state by viewModel.forecastState.collectAsState()
    val alreadyRequested = remember { mutableStateOf(false) }

    val refreshingState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val isRefreshing = state is UiState.Loading

    RequestLocationPermission(
        onPermissionGranted = {
            if (!alreadyRequested.value) {
                alreadyRequested.value = true
                viewModel.loadWeeklyForecast()
            }
        }
    )
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = refreshingState,
        onRefresh = {
            coroutineScope.launch { viewModel.loadWeeklyForecast() }
        }
    ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Прогноз на неделю") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFBBDEFB))
                    .padding(padding)
            ) {
                when (state) {
                    is UiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is UiState.Error -> {
                        Text(
                            text = (state as UiState.Error).message,
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is UiState.Success -> {
                        val forecast = (state as UiState.Success).data
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(forecast) { day ->
                                ForecastItem(day)
                            }
                        }
                    }
                }
            }
        }
    }
}