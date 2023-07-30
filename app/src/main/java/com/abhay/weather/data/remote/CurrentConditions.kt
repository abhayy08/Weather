package com.abhay.weather.data.remote

data class CurrentConditions(
    val conditions: String,
    val datetime: String,
    val datetimeEpoch: Int,
    val dew: Double,
    val feelslike: Double,
    val humidity: Double,
    val icon: String,
    val moonphase: Double,
    val precip: Double?,
    val precipprob: Int,
    val preciptype: Any,
    val pressure: Double,
    val snow: Int,
    val snowdepth: Int,
    val source: String,
    val stations: List<String>,
    val sunrise: String,
    val sunriseEpoch: Int,
    val sunset: String,
    val sunsetEpoch: Int,
    val temp: Double,
    val uvindex: Int,
    val visibility: Double,
    val winddir: Int,
    val windgust: Any,
    val windspeed: Double
)