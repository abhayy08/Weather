package com.abhay.weather.data.repository

import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import com.abhay.weather.data.mappers.toWeatherInfo
import com.abhay.weather.data.remote.WeatherApi
import com.abhay.weather.domain.repository.WeatherRepository
import com.abhay.weather.domain.util.Resource
import com.abhay.weather.domain.weather.WeatherInfo
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val app: Application
) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getCurrentWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun getLocationName(lat: Double, long: Double): String {
        return suspendCoroutine { cont ->
            val geocoder = Geocoder(app, Locale.getDefault())
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                val addressList = geocoder.getFromLocation(lat, long, 1)
                 if (addressList!!.isNotEmpty()) {
                    val address = addressList[0]
                    cont.resume(address.locality)
                } else {
                   cont.resume("")
                }
            } else {
                geocoder.getFromLocation(lat, long, 1,
                object : Geocoder.GeocodeListener {
                    override fun onGeocode(results: MutableList<Address>) {
                        if (results.isNotEmpty()) {
                            val address = results[0]
                            val city = address.subLocality
                            cont.resume(city)
                        } else {
                            cont.resume("")
                        }
                    }

                })
            }
        }


    }
}