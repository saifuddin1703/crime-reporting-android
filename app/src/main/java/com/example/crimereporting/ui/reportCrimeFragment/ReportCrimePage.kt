package com.example.crimereporting.ui.reportCrimeFragment

import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crimereporting.R
import com.example.crimereporting.models.coordinates
import com.example.crimereporting.models.crime
import com.example.crimereporting.ui.components.FailedScreen
import com.example.crimereporting.ui.components.LoadingScreen
import com.example.crimereporting.ui.components.MyMap
import com.example.crimereporting.ui.components.ui.theme.appThemeColor
import com.example.crimereporting.ui.mycrimes.CrimeDetailPage
import com.example.crimereporting.utils.FileOperations
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.skydoves.landscapist.glide.GlideImage
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


const val DEFAULT_LOADING_VALUE = false
const val DEFAULT_FAILED_VALUE = false
const val DEFAULT_TITLE_VALUE = "Title of the crime"
const val DEFAULT_DESCRIPTION_VALUE = "Description of the app"
const val DEFAULT_ADDRESS_VALUE = "Set Location"

@Composable
fun ReportCrimePage(
    viewModel: ReportCrimeViewModel = viewModel()
){

    var isLoading by remember {
        mutableStateOf(false)
    }

    var isFailed by remember {
        mutableStateOf(false)
    }

    var isBackHandlerEnabled by remember {
        mutableStateOf(false)
    }

    var enabled by remember {
        mutableStateOf(false)
    }


    var isDetailPageOpen by remember {
        mutableStateOf(false)
    }
//    val value by animateDpAsState(targetValue = )
    LocalConfiguration.current.screenHeightDp
    LocalConfiguration.current.screenWidthDp

    BackHandler(enabled = isBackHandlerEnabled) {
        if (enabled){
            // logic to animate back to landing page
            isBackHandlerEnabled = false
            enabled = false
            isDetailPageOpen = false
        }
    }
    val context = LocalContext.current

    var address by remember {
        mutableStateOf("Choose Location")
    }

    var latlng by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    var crimeItem by remember {
        mutableStateOf(crime(
            _id = "",
            crimeTitle = "title",
            crimeDescription = "description",
            crimeLocation = "location",
            user = "",
            crimeLocationCoordinate = coordinates(coordinates = ArrayList()),
            images = ArrayList(),
            status = "Filed",
            assignedOfficer = "",
            headQuarter = "headQuarter"
        ))
    }

    if (isDetailPageOpen){
        Box {
//            isBackHandlerEnabled = false
//            enabled = false
            BackHandler(enabled = isDetailPageOpen) {
                isDetailPageOpen = false
            }
            Box(modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxSize()) {
                CrimeDetailPage(crime = crimeItem)
            }
        }

    }else {
        Scaffold(modifier = Modifier
            .fillMaxSize(),
            topBar = {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = {
                        Text(text = "Crime Reporting", color = Color.White)
                    },
                    backgroundColor = appThemeColor
                )
            }) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Heading/Title of the crime",
                    modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                )

                var title by remember {
                    mutableStateOf("this is title")
                }
                var description by remember {
                    mutableStateOf("this is description")
                }

                CustomTextField(
                    value = title,
                    onValueChange = { text: String ->
                        title = text
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 5.dp)
                        .padding(top = 5.dp)
                        .height(60.dp)
                        .background(shape = RoundedCornerShape(15.dp), color = Color.White)
                        .align(alignment = CenterHorizontally)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                )

                Text(
                    text = "Description of crime",
                    modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                )


                CustomTextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 5.dp)
                        .padding(top = 5.dp)
                        .height(230.dp)
                        .background(shape = RoundedCornerShape(15.dp), color = Color.White)
                        .align(alignment = CenterHorizontally)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                )

                val listOfImageFiles = remember {
                    mutableStateListOf<File>()
                }

//                Log.d("tag",listOfImages)
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = {uri ->
//                            isUploading = true
                        Log.d("tag",uri.toString())
                        uri?.let {
                         val imageFile = FileOperations.getFile(context, uri)
                            imageFile?.let {
                                Log.d("tag",it.name)
                                listOfImageFiles.add(it)
                            }
                        }
                    })
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clickable {
                            launcher.launch("image/*")
                        }
                ) {


//                    listOfImages.lis

                    Row(modifier = Modifier.align(alignment = Center)) {
                        Image(
                            painter = painterResource(id = R.drawable.noun_add_image_2370063),
                            contentDescription = "add image icon",
                            modifier = Modifier
                                .align(alignment = CenterVertically)
                                .size(40.dp),
                            contentScale = ContentScale.FillHeight
                        )

                        Text(
                            text = "Upload Image",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .align(CenterVertically)

                        )
                    }
                }

//            val contract = ActivityResultContract.
                LazyRow(content = {
                    items(listOfImageFiles) { imageFile ->
                        Box(modifier = Modifier.padding(end = 5.dp)) {

                            GlideImage(
                                imageModel = imageFile,
                                modifier = Modifier.size(100.dp),
                                loading = {
                                    CircularProgressIndicator(
                                        color = appThemeColor
                                    )
                                }
                            )
                            Image(painter = painterResource(id = R.drawable.ic_baseline_cancel_24),
                                contentDescription = "",
                                modifier = Modifier
                                    .align(alignment = TopEnd)
                                    .padding(all = 2.dp)
                                    .clickable {
                                        listOfImageFiles.remove(imageFile)
                                    })
                        }

                    }
                },
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                )

                Text(
                    text = "Set crime location",
                    modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                )
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable {
                        enabled = true
                        isBackHandlerEnabled = true
                    }
                    .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp)
                    )) {

                    Row(modifier = Modifier.align(alignment = Center)) {
                        Image(
                            painter = painterResource(id = R.drawable.noun_location_2443033),
                            contentDescription = "add image icon",
                            modifier = Modifier
                                .align(alignment = CenterVertically)
                                .size(40.dp),
                            contentScale = ContentScale.FillHeight
                        )

                        Text(
                            text = address,
                            fontSize = 18.sp,
                            modifier = Modifier.align(CenterVertically)
                        )
                    }
                }

                Button(
                    onClick = {

                        // uploading images
                        isLoading = true

                        viewModel.uploadCrimeImages(
                            listOfImageFiles = listOfImageFiles.toList(),
                            onUploadSuccess = { images ->

                            Log.d("tag",images.toString())
                            viewModel.reportCrime(
                                title = title,
                                description = description,
                                crimeLocation = address,
                                crimeLocationCoordinate = coordinates(
                                    type = "Point",
                                    coordinates = ArrayList<Double>().also {
                                        it.add(latlng.latitude)
                                        it.add(latlng.longitude)
                                    }
                                ),
                                images = images,
                                status = "Filed",
                                onReportingSuccess = {crime ->
                                isLoading = false
                                isDetailPageOpen = true
                                crimeItem = crime
                                Toast.makeText(
                                    context,
                                    "Complaint Registered",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                                onReportingFailed = {
                                    isLoading = false
                                    isFailed = true
                                })
                        },
                        onUploadFail = {
                            isLoading = false
                            isFailed = true
                        })

                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = appThemeColor
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .padding(top = 45.dp)
                        .background(color = appThemeColor, shape = RoundedCornerShape(10.dp))
                        .align(alignment = CenterHorizontally)
                )
                {
                    Text(text = "File", color = Color.White)
                }
            }

            if (isLoading) {
                LoadingScreen()
            }

            if (isFailed) {
                FailedScreen{
                    isFailed = false
                }
            }

            SetLocationContainer(enabled = enabled) {
                isBackHandlerEnabled = false
                enabled = false

                val geocoder = Geocoder(context, Locale.getDefault())
                val location = geocoder.getFromLocation(it.latitude, it.longitude, 1)[0]
                address = "${location.locality},${location.adminArea},${location.postalCode}"
                latlng = it
            }
        }
    }

}

@Composable
fun CustomTextField(value:String,
                    onValueChange : (String)->Unit,
                    modifier: Modifier)
{
    Box(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 10.dp)
        )
    }
}

@Composable
fun SetLocationContainer(enabled : Boolean,onClick : (LatLng)->Unit){
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val y by animateDpAsState(targetValue = if (enabled) 0.dp else maxHeight,
            animationSpec = tween(1500)
        )

        Box(modifier = Modifier
            .fillMaxSize()
            .offset(x = 0.dp, y = y)) {
            SetLocation(onClick)
        }
    }
}

@Composable
fun SetLocation(onClick: (LatLng) -> Unit) {
    MyMap(modifier = Modifier.fillMaxSize(), onMapReady = {map->
        map.setOnMapClickListener {
            map.addMarker(MarkerOptions().position(it).title("Your Selected Location"))
            onClick(it)
        }
    })
}


@Preview
@Composable
fun LoadingScreenPreview(){
    SetLocation {

    }
}
@Preview
@Composable
fun ReportCrimePagePreview(){
    ReportCrimePage()
}