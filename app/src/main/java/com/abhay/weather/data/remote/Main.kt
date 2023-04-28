package com.abhay.weather.data.remote

import com.squareup.moshi.Json


data class Main(

    var temp: Double,
    @field:Json(name = "feels_like") var feelsLike: Double,
    @field:Json(name = "temp_min") var tempMin: Double,
    @field:Json(name = "temp_max") var tempMax: Double,
    var pressure: Int,
    var humidity: Int,
    @field:Json(name = "sea_level") var seaLevel: Int,
    @field:Json(name = "grnd_level") var grndLevel: Int

)