package com.abhay.weather.di

import android.app.Application
import com.abhay.weather.data.remote.WeatherApi
import com.abhay.weather.data.repository.database.WeatherDataDao
import com.abhay.weather.data.repository.database.WeatherDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }


    @Provides
    @Singleton
    fun provideWeatherDataDao(app : Application): WeatherDataDao{
        return WeatherDatabase.getInstance(app).weatherDataDao
    }

}