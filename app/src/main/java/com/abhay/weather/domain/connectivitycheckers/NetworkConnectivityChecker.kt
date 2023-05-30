package com.abhay.weather.domain.connectivitycheckers


interface NetworkConnectivityChecker {
    fun isOnline(): Boolean
    fun isGpsEnabled(): Boolean
}