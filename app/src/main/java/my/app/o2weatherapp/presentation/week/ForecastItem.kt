package my.app.o2weatherapp.presentation.week

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

import my.app.o2weatherapp.core.utils.toFormattedDate
import my.app.o2weatherapp.domain.model.ForecastDay


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastItem(day: ForecastDay) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = day.date.toFormattedDate(),
                    style = MaterialTheme.typography.titleMedium
                )
                Row (
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        text = "${day.description.replaceFirstChar { it.uppercase() }}, ",
                        color = Color.DarkGray
                    )
                    Text(
                        text = "${day.temperatureDay.toInt()}°C",
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Text(
                    text = "Мин: ${day.temperatureMin}°C / Макс: ${day.temperatureMax}°C",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )

            }

            AsyncImage(
                model = day.iconUrl,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}