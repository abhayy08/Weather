package com.abhay.weather.ui.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhay.weather.data.mappers.toDays
import com.abhay.weather.data.mappers.toWeatherInfo
import com.abhay.weather.data.repository.database.Days
import com.abhay.weather.data.repository.database.WeatherData
import com.abhay.weather.data.repository.database.WeatherDataDao
import com.abhay.weather.domain.location.LocationTracker
import com.abhay.weather.domain.repository.WeatherRepository
import com.abhay.weather.domain.util.Resource
import com.abhay.weather.domain.weather.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val dao: WeatherDataDao
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            val weatherDataList = dao.getWeatherDataWithDays()
            val data: WeatherInfo? = if (weatherDataList.isNotEmpty()) {
                dao.getWeatherDataWithDays()[0].toWeatherInfo()
            } else null

            state = state.copy(
                weatherInfo = data,
                isLoading = data == null,
                error = null
            )

            locationTracker.getCurrentLocation()?.let { location ->
                val locationName = repository.getLocationName(location.latitude, location.longitude)
                when (val result =
                    repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data.also {
                                it?.locationName = locationName
                            },
                            isLoading = false,
                            error = null
                        )
                        val weatherData = WeatherData(
                            id = 0,
                            locationName = state.weatherInfo!!.locationName,
                            temp = state.weatherInfo!!.currentWeatherData!!.temp,
                            tempMax =state.weatherInfo!!.currentWeatherData!!.tempMax,
                            tempMin =state.weatherInfo!!.currentWeatherData!!.tempMin,
                            feelsLike =state.weatherInfo!!.currentWeatherData!!.temp,
                            visibility =state.weatherInfo!!.currentWeatherData!!.visibility,
                            pressure =state.weatherInfo!!.currentWeatherData!!.pressure,
                            humidity =state.weatherInfo!!.currentWeatherData!!.humidity,
                            windSpeed =state.weatherInfo!!.currentWeatherData!!.windSpeed,
                            sunrise =state.weatherInfo!!.currentWeatherData!!.sunrise,
                            sunset =state.weatherInfo!!.currentWeatherData!!.sunset,
                            currentWeatherSummary =state.weatherInfo!!.currentWeatherData!!.currentWeatherSummary
                        )
                        dao.insertWeatherData(weatherData)

                        result.data!!.weatherForecastDetails!!.forEachIndexed { index, day ->
                            dao.insertDay(day.toDays(index + 1))
                        }

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Make Sure you have granted location permission and turned on your location"
                )
            }
        }
    }
}