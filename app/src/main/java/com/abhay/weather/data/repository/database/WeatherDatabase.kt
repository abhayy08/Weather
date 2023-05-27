package com.abhay.weather.data.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        WeatherData::class,
        Days::class
    ],
    version = 2
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract val weatherDataDao: WeatherDataDao

    companion object {

        @Volatile
        private var INSTANCE : WeatherDatabase? = null

        fun getInstance(context : Context): WeatherDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "WeatherDb"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also {
                    INSTANCE = it
                }
            }
        }
    }
}