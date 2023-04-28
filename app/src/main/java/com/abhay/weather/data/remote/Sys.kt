package com.abhay.weather.data.remote

import com.squareup.moshi.Json


data class Sys(

    var type: Int,
    var id: Int,
    var country: String,
    var sunrise: Int,
    var sunset: Int

)