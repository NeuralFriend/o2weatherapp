package my.app.o2weatherapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import my.app.o2weatherapp.presentation.today.TodayWeatherScreen
import my.app.o2weatherapp.presentation.week.WeekWeatherScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.TODAY
    ) {
        composable(NavRoutes.TODAY) {
            TodayWeatherScreen(onWeekClick = {
                navController.navigate(NavRoutes.WEEK)
            })
        }
        composable(NavRoutes.WEEK) {
            WeekWeatherScreen(onBackClick = {
                navController.popBackStack()
            })
        }
    }
}