package com.abhay.weather.ui.presentation

import android.icu.util.LocaleData
import android.print.PrintAttributes.Margins
import android.util.SparseArray
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhay.weather.data.remote.Day
import com.abhay.weather.ui.theme.blue
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier,
    color: Color = blue
) {
    state.weatherInfo?.weatherForecastDetails?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Weekly Forecast",
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    text = "---->    ",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
//            LazyColumn(
//                modifier = Modifier
//                    .height(270.dp)
//                    .fillMaxWidth(),
//                userScrollEnabled = true,
//                content = {
//                    items(data) {
//                        InfoCards(
//                            data = it,
//                            date = LocalDate.parse(it.datetime)
//                                .format(DateTimeFormatter.ofPattern("E, dd MMM", Locale.ENGLISH))
//                        )
//                    }
//                }
//            )

            LazyRow(content = {
                items(data) { data ->
                    InfoCard1(
                        data = data,
                        date = LocalDate.parse(data.datetime)
                            .format(DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH))
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
    color: Color = blue
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .border(
                width = 3.dp,
                color = Color.Black,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(color),
    ) {

        Column(
            modifier = modifier.padding(15.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = modifier.padding(30.dp,5.dp,0.dp,0.dp)
            ){
                Text(
                    text = "${data.feelslikemax.toInt()}°",
                    fontSize = 25.sp,
                    color = Color.Black,
                    modifier = Modifier.offset(
                        x = (-25).dp,
                        y = (-10).dp
                    )
                )
                Text(
                    text = "/",
                    fontSize = 35.sp,
                    color = Color.Black,
                    modifier = Modifier.offset(
                        x = (-2).dp,
                        y = (-5).dp
                    )
                )
                Text(
                    text = "${data.feelslikemin.toInt()}°",
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.offset(
                        x = 10.dp,
                        y = 10.dp
                    )
                )
            }
            Spacer(modifier = modifier.height(5.dp))
            Text(
                text = date,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}

//@Composable
//fun InfoCards(
//    modifier: Modifier = Modifier,
//    data: Day,
//    date: String,
//    color: Color = blue
//) {
//    Card(
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(color),
//        modifier = Modifier
//            .padding(vertical = 5.dp)
//            .fillMaxWidth()
//            .border(
//                width = 4.dp,
//                color = Color.Black,
//                shape = RoundedCornerShape(12.dp)
//            )
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(17.dp)
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceAround
//        ) {
//            Text(
//                text = date,
//                fontSize = 25.sp,
//                color = Color.Black
//            )
//            Row(
//                modifier = Modifier.width(100.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Text(
//                    text = data.conditions,
//                    fontSize = 15.sp,
//                    color = Color.Black,
//                    style = MaterialTheme.typography.labelSmall
//                )
//            }
//            Box {
//                val size = 25.sp
//                Text(
//                    text = "${data.feelslikemax.toInt()}°",
//                    fontSize = size,
//                    color = Color.Black,
//                    modifier = Modifier.offset(
//                        x = (-25).dp,
//                        y = (-10).dp
//                    )
//                )
//                Text(
//                    text = "/",
//                    fontSize = 30.sp,
//                    color = Color.Black,
//                    modifier = Modifier.offset(
//                        x = (-2).dp
//                    )
//                )
//                Text(
//                    text = "${data.feelslikemin.toInt()}°",
//                    fontSize = size,
//                    color = Color.Black,
//                    modifier = Modifier.offset(
//                        x = 10.dp,
//                        y = 10.dp
//                    )
//                )
//            }
//        }
//    }
//}