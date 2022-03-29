package com.example.crimereporting.ui

import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun test(){

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        var widthState by remember {
            mutableStateOf(100.dp)
        }
        var heightState by remember {
            mutableStateOf(100.dp)
        }

        val width by animateDpAsState(targetValue = widthState)
        var isClicked by remember {
            mutableStateOf(false)
        }
        val height by animateDpAsState(targetValue = heightState,
        finishedListener = {
            isClicked = true
            widthState = 100.dp
            heightState = 100.dp
        })


        LazyRow(content = {
            items(5) {
                Box(
                    modifier = Modifier
                        .padding(all = 5.dp)
                        .width(width)
                        .height(height)
                        .clickable {
                            widthState = maxWidth
                            heightState = maxHeight
                        }
                        .background(color = Color.Red)
                )
            }
        })
        if (isClicked)
            ZoomedBox()
    }
}

@Composable
fun ZoomedBox(){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Red))
}
@Composable
fun ComposeMenu(context: Context){
    var expanded = remember { mutableStateOf(false) }
    val items = listOf("Compose", "Rows", "Columns", "Box", "Button", "Text")
    val disabledValue = "B"
    var selectedIndex = remember { mutableStateOf(0) }
    Box(modifier = Modifier   .fillMaxSize()
    ) {

        Column(
            modifier= Modifier
                .align(alignment = Alignment.Center)
                .padding(all = 0.dp),
        ) {

            Button(
                modifier=Modifier.width(200.dp),
                border = BorderStroke(width = 1.dp,color = Color.Red),
                onClick = {
                    expanded.value = true
                },content = {
                    Text(items[selectedIndex.value])
                })

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier
                    .background(Color.White,)
                    .border(BorderStroke(width = 1.dp, color = Color.Red))
                    .shadow(elevation = 2.dp)
                    .width(200.dp),
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        selectedIndex.value = index
                        expanded.value = false
                    }, text =  {

                        Text(text = s )

                    })
                }
            }
        }

    }
}


@Composable
@Preview
fun testPreview(){
    test()
}