package com.example.crimereporting.ui.mycrimes

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crimereporting.R
import com.example.crimereporting.models.Location
import com.example.crimereporting.models.coordinates
import com.example.crimereporting.models.crime
import com.example.crimereporting.ui.components.ui.theme.appThemeColor
import com.example.crimereporting.ui.components.ui.theme.darkblueLow
import com.example.crimereporting.utils.BackHandler

@Composable
fun MyCrimePage(
    fabOnClick : () -> Unit,
    myCrimesViewModel: MyCrimesViewModel = viewModel()
){
    var isDetailPageOpen by remember {
        mutableStateOf(false)
    }
    var isLoading by remember{
        mutableStateOf(true)
    }


//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent(),
//        onResult = {
////            it.data.da
//        })
//    launcher.launch("image/*")
    BackHandler(enabled = isDetailPageOpen) {
        isDetailPageOpen = false
        myCrimesViewModel.refresh()
    }
    var crimeItem by remember {
        mutableStateOf(
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
            )
        )
    }
    Column(modifier = Modifier
        .padding(bottom = 80.dp)
        .fillMaxSize()
        .background(color = Color.White)) {
        CustomSearchBar(modifier =
        Modifier
            .padding(all = 5.dp)
            .height(40.dp)
            .fillMaxWidth()
            .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp)))
        Scaffold(floatingActionButton = {
            FloatingActionButton(onClick = {
                Log.d("TAG","fabclicked")
                fabOnClick()
            }, backgroundColor = appThemeColor) {
                Image(painter = painterResource(id = R.drawable.ic_baseline_add_24)
                    , contentDescription = "")
            }
        }) {
            var isLoaded by remember {
                mutableStateOf(false)
            }
            Column(modifier = Modifier.fillMaxSize()) {
                AnimatedVisibility(
                    modifier = Modifier.align(CenterHorizontally),
                    visible = !isLoaded,
                    enter = expandVertically(expandFrom = Alignment.Top),
                    exit = shrinkVertically(shrinkTowards = Alignment.Top)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .size(30.dp)
                            .align(alignment = CenterHorizontally),
                        color = appThemeColor
                    )
                }
                val list = myCrimesViewModel.getAllUserComplaints().observeAsState()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                ) {

                    list.value?.let {
                        itemsIndexed(it) { index, crime: crime ->
                            if (!isLoaded)
                                isLoaded = true

                            CrimeBox(crime = crime) {
                                crimeItem = it
                                isDetailPageOpen = true
                            }
                        }
                    }

                }
            }
        }
    }

    DetailPageContainer(enable = isDetailPageOpen, crime = crimeItem)
}

@Composable
fun DetailPageContainer(
    enable : Boolean,
    crime: crime
){
    AnimatedVisibility(visible = enable,
    enter = expandHorizontally(
        initialWidth = {
            0
        }
    ) + expandVertically(
        initialHeight = {
            0
        }
    ),
    exit = shrinkHorizontally(
        targetWidth = {
            0
        }
    ) + shrinkVertically(targetHeight = {
        0
    })) {
        CrimeDetailPage(crime = crime)
    }
}

@Composable
fun CustomSearchBar(modifier : Modifier){

    Box(modifier = modifier) {
        TextField(
            value = "search here",
            onValueChange = {

            },
            leadingIcon = {
                          Image(painter = painterResource(id = R.drawable.ic_baseline_search_24),
                              contentDescription = "search")
            },
            placeholder = {
                          Text(text = "search here")
            },
            label = {
                    Text(text = "search here")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {

                }
            ),
            modifier = Modifier
                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 10.dp)
                .fillMaxSize(),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.LightGray
        )
        )
    }
}

@Composable
fun CrimeBox(crime: crime,onclick : (crime)->Unit){

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(5.dp)
        .clickable {
            onclick(crime)
        }
        .background(color = darkblueLow, shape = RoundedCornerShape(20.dp))
        .border(width = 2.dp, color = darkblueLow, shape = RoundedCornerShape(20.dp))
    ){
        Box(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .size(50.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(50)
                )
        ) {
            Canvas(modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(50)
                ), onDraw = {
                drawCircle(
                    color = Color.Green,
                    radius = 25.dp.toPx(),
                    center = Offset(25.dp.toPx(), 25.dp.toPx()),
                    style = Stroke(
                        width = 3.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )

            })

            Image(painter = painterResource(id = R.drawable.police_icon),
                contentDescription = "",
            modifier = Modifier
                .size(47.dp)
                .clip(shape = CircleShape)
                .align(alignment = Center))
        }
        Column(modifier = Modifier
            .padding(all = 10.dp)) {

            Row(
                modifier = Modifier.padding(bottom = 5.dp)
            ){
                Text(text = "Title : ", fontWeight = FontWeight.Bold,color= Color.Black)
                Text(text = crime.crimeTitle,color = Color.Black)
            }
            Row {
                Text(text = "Status : ", fontWeight = FontWeight.Bold,color = Color.Black)
                Text(text = crime.status, color = Color.Black)
            }
        }
    }
}
@Preview
@Composable
fun MyCrimePagePreview(){
    MyCrimePage({})
}