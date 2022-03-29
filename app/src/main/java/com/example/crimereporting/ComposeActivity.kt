package com.example.crimereporting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.crimereporting.ui.landing.LandingPageComposable
import com.example.crimereporting.ui.theme.CrimeReportingTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CrimeReportingTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LandingPageComposable()

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG","ComposeActivity on start")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG","ComposeActivity on resume")

    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG","ComposeActivity on pause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG","ComposeActivity on stop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG","ComposeActivity on destory")

    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    CrimeReportingTheme {
        Greeting2("Android")
    }
}