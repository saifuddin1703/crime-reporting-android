package com.example.crimereporting.ui.landing

import android.content.IntentSender
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crimereporting.R
import com.example.crimereporting.ui.components.ui.theme.appThemeColor
import com.example.crimereporting.ui.home.Home
import com.example.crimereporting.ui.mycrimes.MyCrimePage
import com.example.crimereporting.ui.mycrimes.MyCrimesViewModel
import com.example.crimereporting.ui.nearbyPoliceStation.NearbyPoliceStationPage
import com.example.crimereporting.ui.nearbyPoliceStation.NearbyPoliceStationViewModel
import com.example.crimereporting.ui.profile.MyProfilePage
import com.example.crimereporting.ui.profile.ProfileViewModel
import com.example.crimereporting.ui.reportCrimeFragment.ReportCrimePage
import com.example.crimereporting.ui.sos.SOSPage

@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalAnimationApi
@Composable
fun LandingPageComposable() {
    val navController = rememberNavController()

//    LocalContext.current.regis
    var isNavigatedToReportCrimePage by remember {
        mutableStateOf(false)
    }
    var isNavigatedToSOSePage by remember {
        mutableStateOf(false)
    }

    var isBackHandlerEnabled by remember {
        mutableStateOf(false)
    }

    var selectedIndex by remember{
        mutableStateOf(0)
    }

    var enabled by remember {
        mutableStateOf(false)
    }

    var issosPageEnabled by remember {
        mutableStateOf(false)
    }

    var isDetailPageOpen by remember {
        mutableStateOf(false)
    }

//    val value by animateDpAsState(targetValue = )
    LocalConfiguration.current.screenHeightDp
    LocalConfiguration.current.screenWidthDp


    BackHandler(enabled = isBackHandlerEnabled || issosPageEnabled) {
        if (isNavigatedToReportCrimePage){
            // logic to animate back to landing page
            isBackHandlerEnabled = false
            enabled = false
        }
        if (isNavigatedToSOSePage){
            // logic to animate back to landing page
//            isBackHandlerEnabled = false
            issosPageEnabled = false
        }
    }
    var topBarTitle by remember {
        mutableStateOf("Crime Reporting")
    }
    navController.enableOnBackPressed(enabled = false)


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
                TopAppBar(modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(text = topBarTitle, color = Color.White)
                },
                backgroundColor = appThemeColor,
                navigationIcon = {
                    if (isNavigatedToReportCrimePage || isNavigatedToSOSePage){
                        Image(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = "go back",
                        modifier = Modifier.size(30.dp)
                            .clickable {
                                if (isNavigatedToReportCrimePage){
                                    // logic to animate back to landing page
                                        isNavigatedToReportCrimePage =false
                                    isBackHandlerEnabled = false
                                    enabled = false
                                }
                                if (isNavigatedToSOSePage){
                                    // logic to animate back to landing page
//            isBackHandlerEnabled = false
                                    issosPageEnabled = false
                                    isNavigatedToSOSePage = false
                                }
                            })
                    }
                }
                )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .background(color = Color.White)
            ) {
                val tabs = listOf("Home","Complaints","Nearby","Profile")

                tabs.forEachIndexed{index,data ->
                    NavigationBarItem(selected = selectedIndex==index,
                        onClick = {
                            selectedIndex = index
                            when(selectedIndex){
                                0->{
                                   navController.navigate("home")
                                    topBarTitle = "Crime Reporting"
                                }
                                1->{
                                    navController.navigate("my_complaints")
                                    topBarTitle = "Complaints"

                                }
                                2->{
                                    navController.navigate("nearbyPoliceStation")
                                    topBarTitle = "Nearby Police Contacts"

                                }
                                3->{
                                    navController.navigate("myProfile")
                                    topBarTitle = "Profile"

                                }

                            }
                        },
                        icon = {
                            when(index){
                                0 ->{
                                    androidx.compose.material3.Icon(
                                        painter = painterResource(id = R.drawable.noun_home_4573328),
                                        contentDescription = "home",
                                        modifier = Modifier.size(35.dp)
                                    )
                                }
                                1 ->{
                                    androidx.compose.material3.Icon(
                                        painter = painterResource(id = R.drawable.noun_complaint_514081),
                                        contentDescription = "home",
                                        modifier = Modifier.size(35.dp)
                                    )
                                }
                                2 ->{
                                    androidx.compose.material3.Icon(
                                        painter = painterResource(id = R.drawable.nearby_police_station),
                                        contentDescription = "home",
                                        modifier = Modifier.size(35.dp)
                                    )
                                }
                                3 ->{
                                    androidx.compose.material3.Icon(
                                        painter = painterResource(id = R.drawable.noun_profile_963218),
                                        contentDescription = "home",
                                        modifier = Modifier.size(35.dp)
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .background(color = Color.White),
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = appThemeColor,
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.Black,
                        ))
                }
            }
                    }
    ){

        NavHost(navController = navController, startDestination = "home"){
            composable("home"){
                Home(
                    fabOnClick = {
                        isNavigatedToSOSePage = true
                        issosPageEnabled = true

                    }
                )
            }
            composable("my_complaints"){
                val viewmodel = hiltViewModel<MyCrimesViewModel>()
                MyCrimePage(
                    fabOnClick = {
                        Log.d("TAG","fab callback")
                        isBackHandlerEnabled = true
                        isNavigatedToReportCrimePage = true
                        enabled = true
                        isDetailPageOpen = false
                    },
                    myCrimesViewModel = viewmodel
                )
            }
            composable("nearbyPoliceStation"){
                val viewModel = hiltViewModel<NearbyPoliceStationViewModel>()
                NearbyPoliceStationPage(viewModel = viewModel)
            }
            composable("myProfile"){
                val viewModel = hiltViewModel<ProfileViewModel>()
                MyProfilePage(viewModel = viewModel)
            }
        }

        when(selectedIndex){
            0 ->{
                navController.navigate("home")
            }
            1 ->{
                navController.navigate("my_complaints")
            }
            2 ->{
                navController.navigate("nearbyPoliceStation")
            }
            3 ->{
                navController.navigate("myProfile")
            }
        }

    }

    NavigationContainer(enabled = enabled)
    SosPageContainer(enabled = issosPageEnabled)
}



@Composable
fun NavigationContainer(enabled: Boolean){
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val y by animateDpAsState(targetValue = if (enabled) 0.dp else maxHeight,
        animationSpec = tween(1500))

        Box(modifier = Modifier
            .fillMaxSize()
            .offset(x = 0.dp, y = y)) {
            ReportCrimePage()
        }
    }
}

@Composable
fun SosPageContainer(enabled: Boolean){
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val y by animateDpAsState(targetValue = if (enabled) 0.dp else maxHeight,
            animationSpec = tween(1500))

        Box(modifier = Modifier
            .fillMaxSize()
            .offset(x = 0.dp, y = y)) {
            SOSPage()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalAnimationApi
@Preview
@Composable
fun LandingPagePreview(){
    LandingPageComposable()
}