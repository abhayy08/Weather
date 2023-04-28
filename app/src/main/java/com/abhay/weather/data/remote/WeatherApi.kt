package com.abhay.weather.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherApi {

    @GET("services/timeline/{lat},{lon}")
    suspend fun getCurrentWeatherData(
        @Path("lat") lat: Double,
        @Path("lon") long: Double,
        //@Query("appid") key: String = "30569a3f3eb6ff881b848e3783dd2de8",
        //@Query("units") unit: String = "metric"
        @Query("unitGroup") unitGroup : String = "metric",
        @Query("key") key : String = "TTUCFFF47RSZYQPHGF7V5CUZN",
        @Query("contentType") contentType: String = "json"
    ): WeatherDto2
}
