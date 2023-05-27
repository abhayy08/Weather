package com.abhay.weather.domain.connectivitycheckers

import android.content.Context

interface NetworkConnectivityChecker {
    fun isOnline(): Boolean
    fun isGpsEnabled(): Boolean
}