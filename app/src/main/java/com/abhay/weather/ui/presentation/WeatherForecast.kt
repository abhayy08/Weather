package com.abhay.weather.ui.presentation

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
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
                .padding(horizontal = 20.dp)
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
            LazyColumn(
                modifier = Modifier.height(275.dp),
                userScrollEnabled = true,
                content = {
                    items(data) { day ->
                        InfoCards(data = day)
                    }
                })

        }
    }
}

@Composable
fun InfoCards(
    modifier: Modifier = Modifier,
    data: Day,
    color: Color = blue
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(color),
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .border(
                width = 4.dp,
                color = Color.Black,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tomorrow",
                fontSize = 25.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(30.dp))
            Row(
                modifier = Modifier.width(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = data.conditions,
                    fontSize = 16.sp,
                    color = Color.Black,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.width(60.dp))
            Box {
                val size = 25.sp
                Text(
                    text = "${data.feelslikemax.toInt()}°",
                    fontSize = size,
                    color = Color.Black,
                    modifier = Modifier.offset(
                        x = (-25).dp,
                        y = (-10).dp
                    )
                )
                Text(
                    text = "/",
                    fontSize = 30.sp,
                    color = Color.Black,
                    modifier = Modifier.offset(
                        x = (-2).dp
                    )
                )
                Text(
                    text = "${data.feelslikemin.toInt()}°",
                    fontSize = size,
                    color = Color.Black,
                    modifier = Modifier.offset(
                        x = 10.dp,
                        y = 10.dp
                    )
                )
            }
        }
    }
}