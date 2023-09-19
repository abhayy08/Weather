package com.abhay.weather.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherApi {

    @GET("services/timeline/{lat},{lon}")
    suspend fun getCurrentWeatherData(
        @Path("lat") lat: Double,
        @Path("lon") long: Double,
        @Query("unitGroup") unitGroup : String = "metric",
        @Query("key") key : String = "TTUCFFF47RSZYQPHGF7V5CUZN",
        @Query("contentType") contentType: String = "json"
    ): WeatherDto2
}
