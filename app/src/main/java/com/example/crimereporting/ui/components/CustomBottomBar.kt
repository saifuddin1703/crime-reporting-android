package com.example.crimereporting.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.crimereporting.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crimereporting.ui.components.ui.theme.appThemeColor


@ExperimentalAnimationApi
@Composable
@Preview
fun BottomBarPreview(){
//    BottomBar(rememberNavController())
//    Navigationb
//   BottomBar(navController = rememberNavController())
    NavigationBar(
        modifier = Modifier
            .background(color = Color.White)
    ) {
        val tabs = listOf("Home","Complaints","SOS","Nearby","Profile")
        var selectedIndex by remember{
            mutableStateOf(0)
        }
        tabs.forEachIndexed{index,data ->
            NavigationBarItem(selected = selectedIndex==index,
                onClick = {
                    selectedIndex = index
                },
                icon = {
                    when(index){
                        0 ->{
                            Icon(painter = painterResource(id = R.drawable.noun_home_4573328),
                                contentDescription = "home",
                                modifier = Modifier.size(25.dp)
                            )
                        }
                        1 ->{
                            Icon(painter = painterResource(id = R.drawable.noun_complaint_514081),
                                contentDescription = "home",
                                modifier = Modifier.size(25.dp))
                        }
                        2 ->{
                            Icon(painter = painterResource(id = R.drawable.noun_sos_21424),
                                contentDescription = "home",
                                modifier = Modifier.size(25.dp))
                        }
                        3 ->{
                            Icon(painter = painterResource(id = R.drawable.nearby_police_station),
                                contentDescription = "home",
                                modifier = Modifier.size(25.dp))
                        }
                        4 ->{
                            Icon(painter = painterResource(id = R.drawable.noun_profile_963218),
                                contentDescription = "home",
                                modifier = Modifier.size(25.dp))
                        }
                    }
                },
                label = {
                    Text(text = data,
                        fontSize = 12.sp
                    )
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = appThemeColor,
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Black
                ))
        }
    }

}