package com.abhay.weather.data.remote

import com.squareup.moshi.Json


data class Weather(

    var id: Int,
    var main: String,
    var description: String,
    var icon: String

)