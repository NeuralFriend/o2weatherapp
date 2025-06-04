package my.app.o2weatherapp.domain.model

data class WeatherInfo(
    val city: String,
    val temperature: Double,
    val humidity: Int,
    val description: String,
    val iconUrl: String
)