package com.abhay.weather.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.abhay.weather.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@ExperimentalCoroutinesApi
class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
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

        if (!hasAccessFineLocationPermission || !hasAccessCoarseLocationPermission) {
            return null
        }

        val request = LocationRequest.Builder(PRIORITY_HIGH_ACCURACY,10000)
            .setMinUpdateIntervalMillis(5000)
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
//        suspendCancellableCoroutine { cont ->
//            locationClient.lastLocation.apply {
//                if (isComplete) {
//                    if (isSuccessful) {
//                        cont.resume(result)
//                    } else {
//                        cont.resume(null)
//                    }
//                    return@suspendCancellableCoroutine
//                }
//                addOnSuccessListener {
//                    cont.resume(it)
//                }
//                addOnFailureListener {
//                    cont.resume(null)
//                }
//                addOnCanceledListener {
//                    cont.cancel()
//                }
//            }
//        }
    }
