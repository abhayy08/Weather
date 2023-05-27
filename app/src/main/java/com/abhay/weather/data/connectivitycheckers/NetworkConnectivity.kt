package com.abhay.weather.data.connectivitycheckers

import android.app.Application
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.abhay.weather.domain.connectivitycheckers.NetworkConnectivityChecker
import javax.inject.Inject

class NetworkConnectivity @Inject constructor(
    val application: Application
): NetworkConnectivityChecker {
    override fun isOnline(): Boolean {
        var available = false
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connections = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if(connections != null){
            if (connections.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                connections.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            ) {
                available = true
            }
        }
        return available
    }

    override fun isGpsEnabled(): Boolean {
        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return isGpsEnabled
    }
}