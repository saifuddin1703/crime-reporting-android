package com.example.crimereporting.ui.sos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crimereporting.R
import com.example.crimereporting.models.SOSContact
import com.example.crimereporting.ui.components.AddSoSBottomSheet
import com.example.crimereporting.ui.components.CustomBottomSheet
import com.example.crimereporting.ui.components.EditContactBottomSheet
import com.example.crimereporting.ui.components.ui.theme.appThemeColor
import com.example.crimereporting.ui.home.HomeViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SOSPage(
    viewModel: HomeViewModel = viewModel()
){
    val sosContactList = viewModel.sosContactList.observeAsState(initial = ArrayList())
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
    var sendClick by remember {
        mutableStateOf(true)
    }

    var editClick by remember {
        mutableStateOf(false)
    }

    var addClick by remember {
        mutableStateOf(false)
    }
    val sosList = remember {
        mutableStateListOf<SOSContact>(
            SOSContact(
                name = "Rupesh",
                contact = 1111111111
            ),
            SOSContact(
                name = "Lokesh",
                contact = 1111111111
            ),
            SOSContact(
                name = "Sumit",
                contact = 1111111111
            )
        )
    }
    BottomSheetScaffold(
        modifier = Modifier.padding(bottom = 0.dp),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetBackgroundColor = Color.White,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            if (sendClick)
           CustomBottomSheet {

           }

            if (editClick)
                EditContactBottomSheet {

                }

            if (addClick)
                AddSoSBottomSheet {
                    sosList.add(it)
                }
        },
        sheetElevation = 10.dp
    ){

        val coroutineScope = rememberCoroutineScope()
        Box(modifier = Modifier
            .padding(top = 50.dp, bottom = 0.dp)
            .fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ){
                LazyColumn(
                    modifier = Modifier
                        .background(color = Color.White)
                ) {
                    items(sosContactList.value)
                    { item: SOSContact ->

                        ContactBox(contact = item,
                            edit = {
                                sendClick = false
                                editClick = true
                                addClick = false

                                coroutineScope.launch {
                                    if (scaffoldState.bottomSheetState.isCollapsed) {
                                        scaffoldState.bottomSheetState.expand()
                                    } else {
                                        scaffoldState.bottomSheetState.collapse()
                                    }
                                }
                            },
                            delete = {

                            })
                    }
                }
                if (sosList.size < 5)
                FloatingActionButton(
                    onClick = {
                        sendClick = false
                        editClick = false
                        addClick = true
                        coroutineScope.launch {
                            if (scaffoldState.bottomSheetState.isCollapsed) {
                                scaffoldState.bottomSheetState.expand()
                            } else {
                                scaffoldState.bottomSheetState.collapse()
                            }
                        }
                    },
                    contentColor = appThemeColor,
                    modifier = Modifier
                        .padding(bottom = 20.dp, end = 10.dp, top = 10.dp)
                        .border(color = appThemeColor, width = 2.dp, shape = CircleShape)
                        .align(alignment = CenterHorizontally),
                    backgroundColor = Color.White
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add_blue),
                        contentDescription = ""
                    )
                }
            }

            Button(onClick = {
                sendClick = true
                editClick = false
                addClick = false
                coroutineScope.launch {
                    if (scaffoldState.bottomSheetState.isCollapsed) {
                        scaffoldState.bottomSheetState.expand()
                    } else {
                        scaffoldState.bottomSheetState.collapse()
                    }
                }
                             },
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(alignment = Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = appThemeColor,
                contentColor = Color.White
            )) {
                Text(text = "Send S.O.S")
            }


        }
    }

}


@Composable
fun ContactBox(
    contact : SOSContact,
    edit : () ->Unit,
    delete : () -> Unit
    ){

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(all = 10.dp)
        .height(60.dp)
        .background(color = appThemeColor, shape = RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .padding(bottom = 10.dp)
        )
        {
            Text(text = contact.name,color = Color.White,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp))

            Text(text = contact.contact.toString(),color = Color.White,
                modifier = Modifier.padding(start = 10.dp, top = 5.dp))
        }

        Row(modifier = Modifier
            .align(alignment = Alignment.TopEnd)
            .padding(start = 5.dp, end = 10.dp, top = 10.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_edit_24),
                contentDescription = "edit",
                modifier = Modifier
                    .padding(start = 1.dp, end = 1.dp)
                    .size(20.dp)
                    .clickable {
                        edit()
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                contentDescription = "delete",
                modifier = Modifier.size(20.dp)
                    .clickable {
                        delete()
                    }
            )
        }
    }

}
@Composable
@Preview
fun SOSPagePreview(){
    SOSPage()
}