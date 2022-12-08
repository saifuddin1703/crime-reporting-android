package com.example.crimereporting.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crimereporting.ui.components.ui.theme.appThemeColor

@Composable
fun CustomBottomSheet(
    send : ()-> Unit
){
    Column(
        Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(color = Color.White)
    ) {
        var sosmessage by remember {
            mutableStateOf("Its an emergency I need help")
        }

        Text(text = "S.O.S Message",
        modifier = Modifier
            .padding(top = 10.dp)
            .align(alignment = CenterHorizontally),
        color = appThemeColor,
        fontWeight = MaterialTheme.typography.headlineMedium.fontWeight,
        fontSize = MaterialTheme.typography.headlineMedium.fontSize)
        OutlinedTextField(
            value = sosmessage,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = appThemeColor
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(top = 20.dp)
                .align(alignment = CenterHorizontally),
            onValueChange = {
            sosmessage = it
        })
        Text(text = "Your current location will also be sent along with \n the message",
        modifier = Modifier.align(alignment = CenterHorizontally),
        fontSize = androidx.compose.material.MaterialTheme.typography.caption.fontSize)

        Button(onClick = send,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = appThemeColor,
            contentColor = Color.White
        ),
            shape = CircleShape,
        modifier = Modifier.padding(top = 40.dp, bottom = 10.dp)
            .align(alignment = CenterHorizontally)) {
            Text(text = "Send")
        }
    }
}

@Composable
@Preview
fun CustomPreview(){
    CustomBottomSheet{

    }
}