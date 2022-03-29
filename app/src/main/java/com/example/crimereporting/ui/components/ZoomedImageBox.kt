package com.example.crimereporting.ui.components

import androidx.compose.runtime.Composable
import com.example.crimereporting.utils.BackHandler

@Composable
fun ZoomedImageBox(
    onBackPressed : () -> Unit
){
    BackHandler(enabled = true) {
        onBackPressed()
    }
}