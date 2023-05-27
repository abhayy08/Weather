package com.abhay.weather.data.location

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import com.abhay.weather.domain.connectivitycheckers.NetworkConnectivityChecker
import com.abhay.weather.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application,
    private val connectivityChecker: NetworkConnectivityChecker
) : LocationTracker {

    override suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


        val isGpsEnabled = connectivityChecker.isGpsEnabled()

        if (!hasAccessFineLocationPermission || !hasAccessCoarseLocationPermission || !isGpsEnabled) {
            return null
        }

        val request = LocationRequest.Builder(PRIORITY_BALANCED_POWER_ACCURACY, 20 * 60 * 1000)
            .setMinUpdateIntervalMillis(10 * 60 * 1000)
            .build()

        val deferred = CompletableDeferred<Location?>()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.locations.firstOrNull()
                location?.let {
                    deferred.complete(it)
                    locationClient.removeLocationUpdates(this)
                    Log.d("Location123", "Latitude: ${it.latitude}, Longitude: ${it.longitude}")
                }
            }
        }

        locationClient.requestLocationUpdates(request, locationCallback, null)

        return deferred.await()
    }
}
