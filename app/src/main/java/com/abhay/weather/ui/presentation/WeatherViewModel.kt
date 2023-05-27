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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val dao: WeatherDataDao
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state

    fun loadWeatherInfo() {
        viewModelScope.launch {
            val weatherDataList = dao.getWeatherDataWithDays()
            val data: WeatherInfo? = if (weatherDataList.isNotEmpty()) {
                dao.getWeatherDataWithDays()[0].toWeatherInfo()
            } else null

            _state.update {
                it.copy(
                    weatherInfo = data,
                    isLoading = data == null,
                    error = null
                )
            }

            locationTracker.getCurrentLocation()?.let { location ->
                val locationName = repository.getLocationName(location.latitude, location.longitude)
                when (val result =
                    repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        _state.update { it ->
                            it.copy(
                                weatherInfo = result.data.also {
                                    it!!.locationName = locationName
                                },
                                isLoading = false,
                                error = null
                            )
                        }
                        val weatherData = WeatherData(
                            id = 0,
                            locationName = state.value.weatherInfo?.locationName ?: "",
                            temp = state.value.weatherInfo!!.currentWeatherData!!.temp,
                            tempMax = state.value.weatherInfo!!.currentWeatherData!!.tempMax,
                            tempMin = state.value.weatherInfo!!.currentWeatherData!!.tempMin,
                            feelsLike = state.value.weatherInfo!!.currentWeatherData!!.feelsLike,
                            visibility = state.value.weatherInfo!!.currentWeatherData!!.visibility,
                            pressure = state.value.weatherInfo!!.currentWeatherData!!.pressure,
                            humidity = state.value.weatherInfo!!.currentWeatherData!!.humidity,
                            windSpeed = state.value.weatherInfo!!.currentWeatherData!!.windSpeed,
                            sunrise = state.value.weatherInfo!!.currentWeatherData!!.sunrise,
                            sunset = state.value.weatherInfo!!.currentWeatherData!!.sunset,
                            currentWeatherSummary = state.value.weatherInfo!!.currentWeatherData!!.currentWeatherSummary,
                            weatherDesc = state.value.weatherInfo!!.currentWeatherData!!.weatherDesc
                        )
                        dao.insertWeatherData(weatherData)

                        result.data!!.weatherForecastDetails!!.forEachIndexed { index, day ->
                            dao.insertDay(day.toDays(index + 1))
                        }


                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                weatherInfo = null,
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
            } ?: kotlin.run {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Make Sure you have granted location permission and turned on your location"
                    )
                }
            }
        }
    }
}