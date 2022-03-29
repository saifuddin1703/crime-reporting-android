package com.example.crimereporting.ui.aadharVerification

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crimereporting.ui.components.ui.theme.appThemeColor

@Composable
fun animator(){
    Column {
        BoxWithConstraints(modifier = Modifier.size(100.dp)) {

            var line1XState by remember {
                mutableStateOf(0.dp)
            }

            val line1x by animateDpAsState(
                targetValue = line1XState,
                animationSpec = tween(2000)
            )

            // line 2

            var line2YState by remember {
                mutableStateOf(0.dp)
            }
            val line2y by animateDpAsState(
                targetValue = line2YState,
                animationSpec = tween(2000)
            )

            //line 3
            var line3XState by remember {
                mutableStateOf(maxWidth)
            }

            val line3x by animateDpAsState(
                targetValue = line3XState,
                animationSpec = tween(2000)
            )

            //line 4

            var line4YState by remember {
                mutableStateOf(maxHeight)
            }
            val line4y by animateDpAsState(
                targetValue = line4YState,
                animationSpec = tween(2000)
            )


            Canvas(modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White), onDraw = {

                drawLine(
                    color = appThemeColor,
                    start = Offset(0f, 0f),
                    end = Offset(x = line1x.toPx(), y = 0f),
                    strokeWidth = 10.dp.toPx()
                )

                drawLine(
                    color = appThemeColor,
                    start = Offset(x = maxWidth.toPx(), y = 0f),
                    end = Offset(x = maxWidth.toPx(), y = line2y.toPx()),
                    strokeWidth = 10.dp.toPx()
                )

                drawLine(
                    color = appThemeColor,
                    end = Offset(x = line3x.toPx(), y = maxHeight.toPx()),
                    start = Offset(x = maxWidth.toPx(), y = maxHeight.toPx()),
                    strokeWidth = 10.dp.toPx()
                )

                drawLine(
                    color = appThemeColor,
                    start = Offset(x = 0f, y = maxHeight.toPx()),
                    end = Offset(x = 0f, y = line4y.toPx()),
                    strokeWidth = 10.dp.toPx()
                )
            })
            Button(onClick =
            {
                if (
                line1XState == maxWidth&&
                line2YState == maxHeight&&
                line3XState == 0.dp&&
                line4YState == 0.dp){

                line1XState = 0.dp
                line2YState = 0.dp
                line3XState = maxWidth
                line4YState = maxHeight
            }else{
                    line1XState = maxWidth
                            line2YState = maxHeight
                            line3XState = 0.dp
                            line4YState = 0.dp
                }
            },
                modifier = Modifier.align(alignment = Alignment.Center)) {
                Text(text = "Click")
            }
        }

    }
}

@Composable
@Preview
fun AnimatorPreview(){
    animator()
}