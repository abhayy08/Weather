package com.abhay.weather.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.abhay.weather.R


val customFont: FontFamily = FontFamily(
    Font(R.font.nunitoregular)
)


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = customFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = customFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    titleLarge = TextStyle(
        fontFamily = customFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = customFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
)