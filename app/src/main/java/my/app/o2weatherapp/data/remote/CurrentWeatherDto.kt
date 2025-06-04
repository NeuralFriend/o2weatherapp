package my.app.o2weatherapp.data.remote

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    @SerializedName("main") val main: MainDto,
    @SerializedName("weather") val weather: List<WeatherDescriptionDto>,
    @SerializedName("name") val cityName: String
)

data class MainDto(
    val temp: Double,
    val humidity: Int
)

data class WeatherDescriptionDto(
    val description: String,
    val icon: String
)