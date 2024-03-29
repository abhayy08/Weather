package com.abhay.weather.ui.presentation

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhay.weather.R
import com.abhay.weather.ui.customcomponents.CircularProgressBar
import com.abhay.weather.ui.theme.sec
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt


@Composable
fun WeatherUi2(
    viewModel: WeatherViewModel,
    color: Color,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current
    val vibrator = context.getSystemService(Vibrator::class.java) as Vibrator

    state.value.weatherInfo?.currentWeatherData?.let { data ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 35.dp)
                .background(sec)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = color,
                        shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                    )
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    )
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            vibrator.cancel()
                            vibrator.vibrate(
                                VibrationEffect.createOneShot(
                                    70,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                            isExpanded = !isExpanded
                        }
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = modifier.height(15.dp))
                Text(
                    text = "-${state.value.weatherInfo!!.locationName}-",
                    color = Color.Black,
                    fontSize = 35.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${data.temp.roundToInt()}",
                        fontSize = 80.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "°C",
                        modifier = modifier.offset(y = (-15).dp),
                        fontSize = 35.sp,
                        color = Color.Black
                    )
                }
                Text(
                    text = data.weatherDesc,
                    color = Color.DarkGray,
                    fontSize = 20.sp
                )
                Spacer(modifier = modifier.height(20.dp))
                Text(
                    text = "MaxTemp: ${data.tempMax}  |  MinTemp: ${data.tempMin}",
                    color = Color.Black,
                    fontSize = 20.sp
                )
                if (isExpanded) {
                    Spacer(modifier = modifier.height(15.dp))
                    Divider(
                        thickness = 2.dp,
                        color = Color.DarkGray,
                        modifier = modifier.width(50.dp)
                    )
                    WeatherInfoCard(
                        windSpeed = data.windSpeed.toInt(),
                        humidity = data.humidity.toInt(),
                        feelsLike = data.feelsLike
                    )
                }

                Icon(
                    painter = painterResource(
                        id = if (!isExpanded) R.drawable.dropdown else R.drawable.dropup
                    ),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = modifier.size(35.dp)
                )
            }
            Column(
                modifier = modifier
                    .background(
                        color = sec,
                        shape = RoundedCornerShape(topEnd = 30.dp)
                    )
            ) {
                Spacer(modifier = modifier.height(10.dp))
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(15.dp, 10.dp),
                    colors = CardDefaults.cardColors(Color.DarkGray),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    DailySummary(state = state.value)
                }
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(15.dp, 10.dp),
                    colors = CardDefaults.cardColors(Color.DarkGray),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    WeatherForecast(state = state.value)
                }
                Row(
                    modifier = modifier
                        .padding(5.dp, 10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    DetailCard(
                        res = R.drawable.sunny,
                        text = "UV Index",
                        value = data.uvIndex,
                        unit = ""
                    )
                    DetailCard(
                        res = R.drawable.eye,
                        text = "Visibility",
                        value = data.visibility.toInt(),
                        unit = " km"
                    )
                }
                Row(
                    modifier = modifier
                        .padding(5.dp, 10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    DetailCard(
                        res = R.drawable.waves,
                        text = "Pressure",
                        value = data.pressure.toInt(),
                        unit = " hPa"
                    )
                    DetailCard(
                        res = R.drawable.precip,
                        text = "Precip Probability",
                        value = data.precipProb,
                        unit = " %"
                    )
                }

                SunsetSunriseProgress(
                    sunrise = data.sunrise,
                    sunset = data.sunset,
                )
            }
        }
    }
}

fun calculatePercentageDifference(
    currentTime: LocalTime,
    initialTime: LocalTime,
    finalTime: LocalTime
): Float {
    val currentTimeSeconds = currentTime.toSecondOfDay().toFloat()
    val finalTimeSeconds = finalTime.toSecondOfDay().toFloat()
    val initialTimeSeconds = initialTime.toSecondOfDay().toFloat()
    val howMuchTime = (currentTimeSeconds - initialTimeSeconds)
    val total = (finalTimeSeconds - initialTimeSeconds)


    return (howMuchTime / total)
}

@Composable
fun SunsetSunriseProgress(
    sunrise: String,
    sunset: String,
    modifier: Modifier = Modifier
) {

    val formattedSunrise = LocalTime
        .parse(sunrise, DateTimeFormatter.ofPattern("HH:mm:ss"))
        .format(DateTimeFormatter.ofPattern("h:mm a"))

    val formattedSunset = LocalTime
        .parse(sunset, DateTimeFormatter.ofPattern("HH:mm:ss"))
        .format(DateTimeFormatter.ofPattern("h:mm a"))

    val finalTime = LocalTime.parse(sunset, DateTimeFormatter.ofPattern("HH:mm:ss"))
    val initialTime = LocalTime.parse(sunrise, DateTimeFormatter.ofPattern("HH:mm:ss"))
    val time = LocalTime.now()

    val timeLeftInSunset = calculatePercentageDifference(time, initialTime, finalTime)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp, 10.dp)
            .height(170.dp),
        colors = CardDefaults.cardColors(Color.DarkGray),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = modifier
                .padding(15.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sunrise),
                    contentDescription = null,
                    modifier = modifier
                        .scale(0.65f)
                        .offset(
                            x = (-25).dp,
                            y = (-15).dp
                        )
                )
                Text(
                    text = "Sunrise",
                    fontSize = 25.sp,
                    color = Color.LightGray,
                    modifier = modifier.offset(y = 10.dp)
                )
                Divider(
                    modifier = modifier
                        .width(70.dp)
                        .padding(0.dp, 10.dp)
                        .align(Alignment.CenterHorizontally), thickness = 2.dp, color = Color.Gray
                )
                Text(
                    text = formattedSunrise,
                    fontSize = 20.sp,
                    color = Color.Gray,
                )
            }
            CircularProgressBar(
                modifier = modifier.offset(y = 10.dp),
                radius = 65.dp,
                percentage = if (timeLeftInSunset > 0 && timeLeftInSunset <= 1) timeLeftInSunset else 0f,
                color = Color.Gray
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sunset),
                    contentDescription = null,
                    modifier = modifier
                        .scale(0.65f)
                        .offset(
                            x = 25.dp,
                            y = (-15).dp
                        )
                )
                Text(
                    text = "Sunset",
                    fontSize = 25.sp,
                    color = Color.LightGray,
                    modifier = modifier.offset(y = 10.dp)
                )
                Divider(
                    modifier = modifier
                        .width(70.dp)
                        .padding(0.dp, 10.dp)
                        .align(Alignment.CenterHorizontally), thickness = 2.dp, color = Color.Gray
                )
                Text(
                    text = formattedSunset,
                    fontSize = 20.sp,
                    color = Color.Gray,
                )
            }
        }
    }
}

@Composable
fun DetailCard(
    modifier: Modifier = Modifier,
    @DrawableRes res: Int,
    text: String,
    value: Int,
    unit: String
) {
    Card(
        modifier = modifier
            .padding(10.dp, 0.dp)
            .wrapContentHeight()
            .width(190.dp),
        colors = CardDefaults.cardColors(Color.DarkGray),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = modifier
                .padding(20.dp, 20.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
        ) {
            Icon(painter = painterResource(id = res), contentDescription = null)
            Text(text = text, fontSize = 20.sp, color = Color.LightGray)
            Divider(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp)
                    .align(Alignment.CenterHorizontally), thickness = 2.dp, color = Color.Gray
            )
            Text(text = "$value$unit", fontSize = 20.sp, color = Color.Gray)
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
        modifier = modifier
            .padding(25.dp)
            .fillMaxWidth()
            .wrapContentWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Daily Summary",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.LightGray,
            fontSize = 20.sp
        )
        Divider(
            modifier = modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp)
                .align(Alignment.CenterHorizontally), thickness = 2.dp, color = Color.Gray
        )
        Text(
            text = "${data.currentWeatherSummary} The Temperature is felt in the range of ${data.tempMax}° and ${data.tempMin}°.",
            style = MaterialTheme.typography.labelSmall,
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun WeatherInfoCard(
    modifier: Modifier = Modifier,
    windSpeed: Int,
    humidity: Int,
    feelsLike: Double
) {
    Row(
        modifier = modifier
            .padding(20.dp, 0.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieImage(
                id = R.raw.wind1,
                speed = 1.toFloat()
            )
            Text(
                text = "$windSpeed km/h",
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(
                text = "Wind",
                fontSize = 15.sp,
                color = Color.DarkGray
            )
        }
        Divider(
            modifier = modifier
                .rotate(90f)
                .width(30.dp), thickness = 1.dp, color = Color.Gray
        )
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieImage(
                id = R.raw.drop1,
                speed = 1.2.toFloat(),
                modifier = modifier.size(55.dp)
            )
            Text(
                text = "$humidity %",
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(
                text = "Humidity",
                fontSize = 15.sp,
                color = Color.DarkGray
            )
        }
        Divider(
            modifier = modifier
                .rotate(90f)
                .width(30.dp), thickness = 1.dp, color = Color.DarkGray
        )
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieImage(
                id = R.raw.temp1,
                modifier = modifier
                    .offset(y = (-10).dp)
                    .scale(0.9f),
                speed = 0.8.toFloat()
            )
            Text(
                text = "$feelsLike°C",
                modifier = modifier.offset(y = (-5).dp),
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(
                text = "FeelsLike",
                modifier = modifier.offset(y = (-5).dp),
                fontSize = 15.sp,
                color = Color.DarkGray
            )
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