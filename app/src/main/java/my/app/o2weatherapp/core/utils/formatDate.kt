package my.app.o2weatherapp.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(unixTimestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale("ru"))
    return Instant.ofEpochSecond(unixTimestamp)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toFormattedDate(): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, d MMM", Locale("ru"))
    return Instant.ofEpochSecond(this)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
        .replaceFirstChar { it.uppercase() } // для красоты
}