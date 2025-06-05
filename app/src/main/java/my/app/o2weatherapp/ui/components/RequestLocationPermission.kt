package my.app.o2weatherapp.ui.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun RequestLocationPermission(
    forceRequest: Boolean = false,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit = {}
) {
    var permissionRequested by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    )

    LaunchedEffect(forceRequest) {
        if (!permissionRequested || forceRequest) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            permissionRequested = true
        }
    }
}