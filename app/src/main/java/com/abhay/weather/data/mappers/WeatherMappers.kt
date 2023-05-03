package com.abhay.weather.data.mappers

import androidx.compose.ui.text.input.DeleteSurroundingTextCommand
import com.abhay.weather.data.remote.Day
import com.abhay.weather.data.remote.WeatherDto2
import com.abhay.weather.data.repository.database.Days
import com.abhay.weather.data.repository.database.relations.WeatherDataWithDays
import com.abhay.weather.domain.weather.CurrentWeatherDetails
import com.abhay.weather.domain.weather.WeatherInfo
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale


fun WeatherDto2.toWeatherInfo(): WeatherInfo {
    //val weatherDec = currentConditions.conditions

    val currentHour = days[0].hours.find { hour ->
        val now = LocalTime.now()
        val nearestHour = now.truncatedTo(ChronoUnit.HOURS)
        val formatted = nearestHour.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        formatted == hour.datetime
    }
    val temp = currentHour!!.temp
    val weatherDesc = currentHour.conditions
    val feelsLike = currentHour.feelslike
    val pressure = currentHour.pressure
    val humidity = currentHour.humidity
    val windSpeed = currentHour.windspeed
    val sunrise = currentConditions.sunrise
    val sunset = currentConditions.sunset
    val currentWeatherSummary = days[0].description
    val currentHourlyForecast = days[0].hours
    val tempMax = days[0].feelslikemax
    val tempMin = days[0].feelslikemin
    val visibility = currentHour.visibility
    val listOfDays = days

    val date = days[0].datetime
    val formattedDate =
        LocalDate.parse(date).format(DateTimeFormatter.ofPattern("E, d MMM", Locale.ENGLISH))

    val currentWeather = CurrentWeatherDetails(
        dateAndDay = formattedDate,
        weatherDesc = weatherDesc,
        temp = temp,
        feelsLike = feelsLike,
        pressure = pressure.toInt(),
        humidity = humidity,
        windSpeed = windSpeed,
        sunrise = sunrise,
        sunset = sunset,
        currentWeatherSummary = currentWeatherSummary,
        tempMax = tempMax,
        tempMin = tempMin,
        visibility = visibility
    )
    return WeatherInfo(
        currentWeatherData = currentWeather,
        weatherForecastDetails = listOfDays,
        currentHourlyForecast = currentHourlyForecast,
        locationName = ""
    )
}

fun WeatherDataWithDays.toWeatherInfo(): WeatherInfo {
    val locationName = weatherData.locationName
    val daysList = days
    val date = days[0].datetime
    val formattedDate =
        LocalDate.parse(date).format(DateTimeFormatter.ofPattern("E, d MMM", Locale.ENGLISH))
    val currentWeatherDetails = CurrentWeatherDetails(
        dateAndDay = formattedDate,
        weatherDesc = days[0].conditions,
        temp = days[0].temp,
        tempMax = days[0].feelslikemax,
        tempMin = days[0].feelslikemin,
        feelsLike = days[0].temp,
        visibility = days[0].visibility,
        pressure = days[0].pressure.toInt(),
        humidity = days[0].humidity,
        windSpeed = days[0].windspeed,
        sunrise = days[0].sunrise,
        sunset = days[0].sunset,
        currentWeatherSummary = days[0].description
    )

    return WeatherInfo(
        currentWeatherData = currentWeatherDetails,
        weatherForecastDetails = daysList.map { it.toDay() },
        currentHourlyForecast = emptyList(),
        locationName = locationName
    )
}

fun Days.toDay(): Day {

    return Day(
        conditions = conditions,
        datetime = datetime,
        description = description,
        feelslike = temp,
        feelslikemax = feelslikemax,
        feelslikemin = feelslikemin,
        hours = emptyList(),
        humidity = humidity,
        precip = 0.0,
        precipprob = 0.0,
        pressure = pressure,
        sunrise = sunrise,
        sunset = sunset,
        temp = temp,
        uvindex = 0,
        visibility = visibility,
        windspeed = windspeed
    )
}

fun Day.toDays(id: Int): Days {

    val currentHour = hours.find { hour ->
        val now = LocalTime.now()
        val nearestHour = now.truncatedTo(ChronoUnit.HOURS)
        val formatted = nearestHour.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        formatted == hour.datetime
    }

    return Days(
        dayId= id,
        weatherId = 0,
        conditions = currentHour!!.conditions,
        datetime = datetime,
        description = description,
        feelslikemax = feelslikemax,
        feelslikemin = feelslikemin,
        humidity = currentHour.humidity,
        pressure = currentHour.pressure,
        temp = currentHour.temp,
        visibility = currentHour.visibility,
        windspeed = currentHour.windspeed,
        sunrise = sunrise,
        sunset = sunset
    )
}