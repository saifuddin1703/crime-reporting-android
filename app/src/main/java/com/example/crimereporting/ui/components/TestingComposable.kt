package com.example.crimereporting.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TestingComposable(){

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)){

        Box(modifier = Modifier.fillMaxWidth()
            .height(200.dp)
            .align(alignment = Alignment.BottomCenter)
            .background(color = Color.Gray))
//            .swipeable(
//                anchors = mapOf(
//
//                )
//            ))
    }
}

@Composable
@Preview
fun TestPreview(){
    TestingComposable()
}