package com.abhay.weather.data.remote

import com.squareup.moshi.Json


data class Wind(

    var speed: Double,
    var deg: Int,
    var gust: Double

)