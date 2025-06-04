package my.app.o2weatherapp.data.remote

import com.google.gson.annotations.SerializedName

data class WeeklyForecastDto(
    @SerializedName("list") val days: List<ForecastDayDto>
)

data class ForecastDayDto(
    @SerializedName("dt") val timestamp: Long,
    @SerializedName("temp") val temp: TemperatureDto,
    @SerializedName("weather") val weather: List<WeatherDescriptionDto>
)

data class TemperatureDto(
    val day: Double,
    val min: Double,
    val max: Double
)