package com.abhay.weather.ui.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhay.weather.data.remote.Day
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier,
) {
    state.weatherInfo?.weatherForecastDetails?.let { data ->

        Column(
            modifier = modifier.padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Weekly Forecast",
                    fontSize = 20.sp,
                    color = Color.LightGray
                )
            }
            Divider(modifier = modifier.fillMaxWidth().padding(0.dp, 10.dp), thickness = 2.dp, color = Color.Gray)
            LazyRow(
                content = {
                items(data) { data ->
                    InfoCard1(
                        data = data,
                        date = LocalDate.parse(data.datetime)
                            .format(DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH)),
                        color = Color.Gray
                    )

                }
            })
        }


    }
}

@Composable
fun InfoCard1(
    modifier: Modifier = Modifier,
    data: Day,
    date: String,
    color: Color = Color.DarkGray
) {
    Card(
        modifier = modifier
            .padding(4.dp, 5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(color),
    ) {

        Column(
            modifier = modifier.padding(15.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = modifier.padding(25.dp, 5.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = "${data.feelslikemax.toInt()}°",
                    fontSize = 25.sp,
                    color = Color.Black,
                    modifier = Modifier.offset(
                        x = (-27).dp,
                        y = (-5).dp
                    )
                )
                Text(
                    text = "/",
                    fontSize = 35.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .offset(
                            x = (2).dp,
                            y = (-4).dp
                        )
                        .rotate(10f)
                )
                Text(
                    text = "${data.feelslikemin.toInt()}°",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.offset(
                        x = 10.dp,
                        y = 10.dp
                    )
                )
            }
            Spacer(modifier = modifier.height(20.dp))
            Text(
                text = date,
                fontSize = 19.sp,
                color = Color.Black
            )
        }
    }
}