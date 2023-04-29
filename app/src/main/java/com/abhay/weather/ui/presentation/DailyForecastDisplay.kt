package com.abhay.weather.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhay.weather.data.remote.Day
import com.abhay.weather.ui.theme.blue

@Composable
fun DailyForecastDisplay(
    data: Day,
    color: Color = blue,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 25.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(Color.Black),
    ){
        Column(
            modifier = modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Today",
                fontSize = 15.sp,
                color = color
            )
            Text(
                text = "Max Temp: 25",
                fontSize = 15.sp,
                color = color
            )

        }
    }

}