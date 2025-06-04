package my.app.o2weatherapp.domain.model

data class ForecastDay(
    val date: Long,
    val temperatureDay: Double,
    val temperatureMin: Double,
    val temperatureMax: Double,
    val description: String,
    val iconUrl: String
)