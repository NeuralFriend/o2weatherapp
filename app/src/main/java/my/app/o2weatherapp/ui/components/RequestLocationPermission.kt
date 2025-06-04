package my.app.o2weatherapp.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    onPermissionGranted: () -> Unit
) {
    val permissionState = rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        } else {
            onPermissionGranted()
        }
    }

    if (permissionState.status.isGranted) {
        onPermissionGranted()
    } else {
        // Можешь показать UI с просьбой выдать разрешение
        Text("Для работы приложения необходимо разрешение на геолокацию.")
    }
}