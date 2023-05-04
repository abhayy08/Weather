package com.abhay.weather.ui.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.abhay.weather.ui.theme.WeatherTheme
import com.abhay.weather.ui.theme.blue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
        installSplashScreen().apply {
            setKeepVisibleCondition{
                viewModel.state.value.isLoading
            }
        }
        setContent {
            val color by remember {
                mutableStateOf(blue)
            }
            WeatherTheme(color) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = color
                ) {
                    WeatherUi(
                        viewModel = viewModel,
                        color = color
                    )
                }
            }
        }
    }
}
