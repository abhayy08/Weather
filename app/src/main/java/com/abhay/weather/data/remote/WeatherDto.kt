package com.abhay.weather.data.remote

import com.squareup.moshi.Json

data class WeatherDto(
    var coord: Coord,
    var weather: List<Weather>,
    var base: String,
    var main: Main,
    var visibility: Int,
    var wind: Wind,
    var clouds: Clouds,
    var dt: Int,
    var sys: Sys,
    var timezone: Int,
    var id: Int,
    var name: String,
    var cod: Int,
)