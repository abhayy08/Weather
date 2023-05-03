package com.abhay.weather.ui.presentation

import android.annotation.SuppressLint
import androidx.annotation.RawRes
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhay.weather.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WeatherUi(
    state: WeatherState,
    color: Color,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.currentWeatherData?.let { data ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
                .background(color),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = state.weatherInfo.locationName,
                    fontSize = 35.sp,
                    color = Color.Black
                )
            }
            Spacer(modifier = modifier.height(15.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = modifier,
                    colors = CardDefaults.cardColors(Color.Black)
                ) {
                    Text(
                        text = data.dateAndDay,
                        fontSize = 15.sp,
                        color = color,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = modifier.padding(5.dp)
                    )
                }
            }
            Spacer(modifier = modifier.height(15.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = data.weatherDesc,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black
                )
            }
            Temp(temp = data.temp)
            DailySummary(
                state = state,
            )
            Spacer(modifier = modifier.height(16.dp))
            WeatherInfoCard(
                color = color,
                windSpeed = data.windSpeed.toInt(),
                humidity = data.humidity.toInt(),
                visibility = data.visibility.toDouble()
            )
            WeatherForecast(state = state, color = color)
        }

    }
}

@Composable
fun DailySummary(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    val data = state.weatherInfo!!.currentWeatherData!!

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Daily Summary",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = data.currentWeatherSummary,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = "The Temperature is felt in the range of ${data.tempMax}° and ${data.tempMin}°.",
            style = MaterialTheme.typography.labelSmall,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@Composable
fun WeatherInfoCard(
    modifier: Modifier = Modifier,
    color: Color,
    windSpeed: Int,
    humidity: Int,
    visibility: Double
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .wrapContentSize()
            .fillMaxWidth()
            .padding(15.dp),
        colors = CardDefaults.cardColors(Color.Black),

        ) {
        Row(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieImage(
                    id = R.raw.wind_blue,
                    speed = 1.toFloat()
                )
                Text(
                    text = "$windSpeed km/h",
                    fontSize = 20.sp,
                    color = color
                )
                Text(
                    text = "Wind",
                    fontSize = 15.sp,
                    color = color.copy(alpha = 0.80f)
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieImage(
                    id = R.raw.drop_blue,
                    speed = 1.2.toFloat(),
                    modifier = modifier.size(55.dp)
                )
                Text(
                    text = "$humidity %",
                    fontSize = 20.sp,
                    color = color
                )
                Text(
                    text = "Humidity",
                    fontSize = 15.sp,
                    color = color.copy(alpha = 0.80f)
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieImage(
                    id = R.raw.eye_blue,
                    modifier = modifier.offset(y = (-10).dp),
                    speed = 0.8.toFloat()
                )
                Text(
                    text = "$visibility km",
                    fontSize = 20.sp,
                    color = color
                )
                Text(
                    text = "Visibility",
                    fontSize = 15.sp,
                    color = color.copy(alpha = 0.80f)
                )
            }
        }

    }
}

@Composable
fun LottieImage(
    @RawRes id: Int,
    modifier: Modifier = Modifier,
    speed: Float
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(id))
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier.size(60.dp),
        speed = speed,
        alignment = Alignment.Center
    )
}

@Composable
fun Temp(
    modifier: Modifier = Modifier,
    temp: Double
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${temp.toInt()}°C",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 170.sp,
            color = Color.Black
        )
    }
}