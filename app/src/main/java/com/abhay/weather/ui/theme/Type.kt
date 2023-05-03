package com.abhay.weather.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.abhay.weather.R

val tBlack = FontFamily(
    Font(R.font.tblack)
)
val tBold = FontFamily(
    Font(R.font.tbold)
)
val tLight = FontFamily(
    Font(R.font.tlight)
)
val tRegular = FontFamily(
    Font(R.font.tregular)
)
val tSemiBold = FontFamily(
    Font(R.font.tsemibold)
)

val ledger: FontFamily = FontFamily(
    Font(R.font.ledger)
)

val nunito: FontFamily = FontFamily(
    Font(R.font.nunitoregular)
)


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    titleLarge = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
)