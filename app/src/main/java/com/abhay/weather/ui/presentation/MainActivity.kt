package com.abhay.weather.ui.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.abhay.weather.ui.theme.WeatherTheme
import com.abhay.weather.ui.theme.sec
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    
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
            setKeepVisibleCondition {
                viewModel.state.value.isLoading
            }
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContent {
            val state = viewModel.state.collectAsState()
            val swipeRefreshState =
                rememberSwipeRefreshState(isRefreshing = state.value.isRefreshing)

            WeatherTheme(Color.Gray) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { paddingValues->
                    SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = viewModel::refresh,
                        indicator = { state, refreshTrigger ->
                            SwipeRefreshIndicator(
                                state = state,
                                refreshTriggerDistance = refreshTrigger,
                                backgroundColor = Color.DarkGray,
                                contentColor = Color.Gray,
                                fade = true
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Gray)
                                
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 35.dp)
                                    .padding(paddingValues)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                WeatherUi2(
                                    viewModel = viewModel,
                                    color = Color.Gray
                                )
                            }
                            if (state.value.isLoading) {
                                CircularProgressIndicator(
                                    color = Color.Gray, strokeWidth = 5.dp,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                            state.value.error?.let { error ->
                                if (viewModel.isWifiOn()) {
                                    Toast.makeText(
                                        LocalContext.current,
                                        error,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        LocalContext.current,
                                        "Turn on mobile data or connect to wifi",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
