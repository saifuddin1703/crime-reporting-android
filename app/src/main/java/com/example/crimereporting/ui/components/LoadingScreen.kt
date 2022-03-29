package com.example.crimereporting.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.crimereporting.R
import com.example.crimereporting.ui.components.ui.theme.appThemeColor


@Composable
fun LoadingScreen(

){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)){

        Column(
            modifier = Modifier.align(alignment = Alignment.Center),
            ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.complaint_registering))
            LottieAnimation(
                composition = composition,
                modifier = Modifier
                    .size(200.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                restartOnPlay = true,
                iterations = LottieConstants.IterateForever,
                alignment = Alignment.Center,
                clipToCompositionBounds = true)

            Text(
                text = "Please wait.. \nWe are registering your complaint",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(alignment = CenterHorizontally)
            )
        }
    }

}

@Preview
@Composable
fun LoadingScreenPreview(){
    LoadingScreen()
}