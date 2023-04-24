package com.abhay.weather.ui.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhay.weather.ui.theme.WeatherTheme
import com.abhay.weather.ui.theme.blue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val color by remember{
                mutableStateOf(blue)
            }
            WeatherTheme(color) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(10.dp)
                ){
                    WeatherUi(color)
                }
            }
        }
    }
}
