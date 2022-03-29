package com.example.crimereporting.ui.mycrimes

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crimereporting.models.coordinates
import com.example.crimereporting.models.crime
import com.example.crimereporting.ui.components.ZoomedImageBox
import com.example.crimereporting.ui.components.ui.theme.appThemeColor
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.rememberDrawablePainter


const val DEFAULT_BOX_HEIGHT = 100
const val  DEFAULT_BOX_WIDTH = 100
const val DEFAULT_IMAGE_HEIGHT = 100
const val DEFAULT_IMAGE_WIDTH = 100

@Composable
fun CrimeDetailPage(crime: crime,
viewModel: MyCrimesViewModel = viewModel()){


    var isUpdateClicked by remember{
        mutableStateOf(false)
    }
    val user = viewModel.currentUser.observeAsState().value
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)) {


            var boxWidthState by remember {
                mutableStateOf(DEFAULT_BOX_WIDTH.dp)
            }

            var boxHeightState by remember {
                mutableStateOf(DEFAULT_BOX_HEIGHT.dp)
            }

            var imageWidthState by remember {
                mutableStateOf(DEFAULT_IMAGE_WIDTH.dp)
            }

            var imageHeightState by remember {
                mutableStateOf(DEFAULT_IMAGE_HEIGHT.dp)
            }

            var isClicked by remember{
                mutableStateOf(false)
            }
            val boxWidth by animateDpAsState(targetValue = boxWidthState)
            val boxHeight by animateDpAsState(targetValue = boxHeightState, finishedListener = {
                isClicked = true
            })
            val imageWidth by animateDpAsState(targetValue = imageWidthState)
            val imageHeight by animateDpAsState(targetValue = imageHeightState)

            DetailBox(heading = "Title", value = crime.crimeTitle)
            DetailBox(heading = "Description", value = crime.crimeDescription)
            DetailBox(heading = "Address", value = crime.crimeLocation)
            if (isUpdateClicked)
                UpdateBox()
            else
                DetailBox(heading = "Status", value = crime.status)
            user?.let {
                if (it.role == "Police") {
                    Button(onClick = {
                        if (isUpdateClicked){
                            // done updating
//                                     viewModel.up
                        }else{
                            isUpdateClicked = true
                        }
                    },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = appThemeColor
                        ),
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .align(alignment = CenterHorizontally)) {
                        Text(text = if (isUpdateClicked)"Done" else "Update Status")
                    }
                }
            }
            LazyRow(content = {
                items(crime.images) { image->
                    Box(modifier = Modifier
                        .padding(end = 5.dp)
                        .width(width = boxWidth)
                        .height(height = boxHeight)
                        .background(color = Color.LightGray)) {

                        val containerWidthState by remember {
                            mutableStateOf(0.dp)
                        }
                        val containerHeightState by remember {
                            mutableStateOf(0.dp)
                        }

                        val containerWidth by animateDpAsState(targetValue = containerWidthState)
                        val containerHeight by animateDpAsState(targetValue = containerHeightState)
                        GlideImage( // CoilImage, FrescoImage
                            imageModel = image,
                            // draw a resized image.
                            success = { imageState ->
                                imageState.drawable?.let {
                                    Image(
                                        painter = rememberDrawablePainter(drawable = it),
                                        modifier = Modifier
                                            .width(imageWidth)
                                            .height(imageHeight)
                                            .clickable {

                                                boxWidthState = screenWidth
                                                boxHeightState = screenHeight
                                                imageHeightState = 300.dp
                                                imageWidthState = screenWidth
                                            },
                                        contentDescription = ""
                                    )
                                }
                            },
                            loading = {
                                // do something
                            },
                        )
                    }

                }
            },
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, top = 5.dp)
            )

            if (isClicked)
                ZoomedImageBox{
                    isClicked = false
                    boxHeightState = DEFAULT_BOX_HEIGHT.dp
                    boxWidthState = DEFAULT_BOX_WIDTH.dp
                    imageHeightState = DEFAULT_IMAGE_HEIGHT.dp
                    imageWidthState = DEFAULT_IMAGE_WIDTH.dp
                }

        }
    }
}

@Composable
fun DetailBox(heading: String,value : String){
    Text(text = heading,
        modifier = Modifier
            .padding(top = 20.dp, start = 25.dp))

    Box(
        modifier = Modifier
            .padding(top = 5.dp, start = 10.dp, end = 10.dp)
            .height(60.dp)
            .fillMaxWidth()
            .background(color = appThemeColor, shape = RoundedCornerShape(15.dp))
    ) {
        Text(text = value,
            color = Color.White,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(alignment = Alignment.CenterStart)
        )

    }
}

@Composable
fun UpdateBox(){
    Text(text = "Status",
        modifier = Modifier
            .padding(top = 20.dp, start = 25.dp))

    Box(
        modifier = Modifier
            .padding(top = 5.dp, start = 10.dp, end = 10.dp)
            .height(60.dp)
            .fillMaxWidth()
            .background(color = appThemeColor, shape = RoundedCornerShape(15.dp))
    ) {
        Text(text = "value",
            color = Color.White,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(alignment = Alignment.CenterStart)
        )
//        Drop
    }
}
@Preview
@Composable
fun CrimeDetailPagePreview(){
    CrimeDetailPage(
        crime(
        _id = "",
        crimeTitle = "title",
        crimeDescription = "description",
        crimeLocation = "location",
            user = "",
            crimeLocationCoordinate = coordinates(coordinates = ArrayList()),
            images = ArrayList(),
            status = "Filed",
            assignedOfficer = "",
            headQuarter = "headQuater"
    ))
}