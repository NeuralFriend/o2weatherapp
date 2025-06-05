package my.app.o2weatherapp.data.remote

import com.google.gson.annotations.SerializedName

data class ForecastItemDto(
    @SerializedName("dt") val timestamp: Long,
    @SerializedName("main") val main: ForecastMainDto,
    @SerializedName("weather") val weather: List<WeatherDescriptionDto>
)

data class ForecastMainDto(
    @SerializedName("temp") val temp: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double
)

data class ForecastListDto(
    @SerializedName("list") val list: List<ForecastItemDto>
)