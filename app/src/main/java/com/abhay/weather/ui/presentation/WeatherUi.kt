package com.abhay.weather.ui.presentation

import android.annotation.SuppressLint
import androidx.annotation.RawRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
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

    state.weatherInfo?.currentWeatherData?.let {data->
        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = color
        ) {
            Column(
                modifier = modifier.fillMaxSize(),
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
                Spacer(modifier = modifier.height(20.dp))
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
                            text = "Friday, 20 January",
                            fontSize = 15.sp,
                            color = color,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = modifier.padding(10.dp, 2.dp, 10.dp, 2.dp)
                        )
                    }
                }
                Spacer(modifier = modifier.height(16.dp))
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
                Spacer(modifier = modifier.height(16.dp))
                WeatherInfoCard(
                    color = color,
                    windSpeed = data.windSpeed.toInt(),
                    humidity = data.humidity.toInt()
                )

            }
        }
    }
}

@Composable
fun WeatherInfoCard(
    modifier: Modifier = Modifier,
    color: Color,
    windSpeed: Int,
    humidity: Int,
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .wrapContentSize()
            .padding(25.dp, 0.dp, 25.dp, 0.dp),
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
                    color = color
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
                    color = color
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
                    text = "1.6 km",
                    fontSize = 20.sp,
                    color = color
                )
                Text(
                    text = "Visibility",
                    fontSize = 15.sp,
                    color = color
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
            fontSize = 160.sp,
            color = Color.Black
        )
    }
}