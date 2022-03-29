package com.example.crimereporting.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.skydoves.landscapist.rememberDrawablePainter

@Composable
fun ImageContainer(
    height : Dp,
    width : Dp,
    imageDrawable : Drawable
){
    Box(modifier = Modifier
        .height(height)
        .width(width)){

        Image(painter = rememberDrawablePainter(drawable = imageDrawable),
            contentDescription = "image",
        modifier = Modifier.fillMaxWidth()
        )

    }

}