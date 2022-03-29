package com.example.crimereporting

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.app.ActivityCompat
import com.example.crimereporting.repository.UserRepository
import com.example.crimereporting.utils.DEFAULT_TOKEN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    @Inject lateinit var userRepository: UserRepository

    private lateinit var locationPermissionRequest : ActivityResultLauncher<Array<String>>
    @OptIn(ExperimentalAnimationApi::class)
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
         locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    val token = userRepository.getTokenFromSharedPreferences()
                    if (token != null && token != DEFAULT_TOKEN) {
                        startActivity(Intent(this@SplashScreenActivity, ComposeActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                        finish()
                    }
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    val token = userRepository.getTokenFromSharedPreferences()
                    if (token != null && token != DEFAULT_TOKEN) {
                        startActivity(Intent(this@SplashScreenActivity, ComposeActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                        finish()
                    }
                } else -> {
                // No location access granted.
                }
            }
        }

    }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onStart() {
        super.onStart()
    }

    @OptIn(ExperimentalAnimationApi::class)
    override fun onResume() {
        super.onResume()

        GlobalScope.launch {
            delay(1000)
            startapp()
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun startapp(){

//            val token =  userRepository.getTokenFromSharedPreferences()
            if (ActivityCompat.checkSelfPermission(
                    this@SplashScreenActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_DENIED
                && ActivityCompat.checkSelfPermission(
                    this@SplashScreenActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_DENIED)
                {
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
                }
            else
                {
                    val token = userRepository.getTokenFromSharedPreferences()
                    if (token != null && token != DEFAULT_TOKEN) {
                        startActivity(Intent(this@SplashScreenActivity, ComposeActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                        finish()
                    }
                }

    }
}