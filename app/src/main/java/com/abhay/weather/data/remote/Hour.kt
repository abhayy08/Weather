package com.abhay.weather.data.remote

data class Hour(
    val cloudcover: Double,
    val conditions: String,
    val datetime: String,
    val datetimeEpoch: Int,
    val dew: Double,
    val feelslike: Double,
    val humidity: Double,
    val icon: String,
    val precip: Double?,
    val precipprob: Double?,
    //val preciptype: List<String>,
    val pressure: Double,
    val severerisk: Int,
    val solarradiation: Double,
    val temp: Double,
    val uvindex: Int,
    val visibility: Double,
    val winddir: Double,
    val windgust: Double,
    val windspeed: Double
)