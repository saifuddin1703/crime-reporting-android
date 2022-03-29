package com.example.crimereporting.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crimereporting.R
import com.example.crimereporting.ui.components.ui.theme.appThemeColor

@Composable
fun FailedScreen(
    onBack : () -> Unit
){

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
    ){

        Column(modifier = Modifier.align(alignment = Alignment.Center)) {

            Image(painter = painterResource(id = R.drawable.oops),
                contentDescription = "oops",
            modifier = Modifier.align(alignment = CenterHorizontally))

            Text(
                text = "Something wrong has happened",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )

            Button(
                onClick = {
                          onBack()
                          },
                shape = CircleShape,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .align(alignment = CenterHorizontally),
                border = BorderStroke(color = appThemeColor, width = 2.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Text(text = "Go back",
                modifier = Modifier.padding(5.dp),
                color = appThemeColor)
            }
        }
    }
}

@Preview
@Composable
fun FailedScreenPreview(){
    FailedScreen {

    }
}