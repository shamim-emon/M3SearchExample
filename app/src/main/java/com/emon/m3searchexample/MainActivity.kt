package com.emon.m3searchexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.emon.m3searchexample.ui.theme.M3SearchExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            M3SearchExampleTheme {
                LandingScreen()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    M3SearchExampleTheme {
        LandingScreen()
    }
}