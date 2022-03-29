package com.example.crimereporting.ui.home

import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crimereporting.R
import com.example.crimereporting.ui.components.MyMap
import com.example.crimereporting.ui.components.ui.theme.appThemeColor

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home(
    fabOnClick : () -> Unit
){
    var location by remember{
        mutableStateOf(Location("Google"))
    }
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.padding(bottom = 70.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Log.d("TAG", "fabclicked")
                fabOnClick()
            }, backgroundColor = appThemeColor) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = ""
                )
            }
        })
    {

        MyMap(modifier = Modifier.fillMaxSize()) {

        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun HomePagePreview(){
    Home(){

    }
}