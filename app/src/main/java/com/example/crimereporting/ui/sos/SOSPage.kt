package com.example.crimereporting.ui.sos

import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.crimereporting.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crimereporting.ui.home.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crimereporting.models.SOSContact
import com.example.crimereporting.ui.components.ui.theme.appThemeColor
import com.example.crimereporting.ui.components.ui.theme.darkblueLow


@Composable
fun SOSPage(
//    viewModel: HomeViewModel = viewModel()
){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Image(painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = "")
            }
        }
    ){
        LazyColumn(modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize()
            .background(color = Color.White)){
            items(listOf<SOSContact>(
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
                ),
                SOSContact(
                    name = "Road",
                    contact = 1111111111
                )
            )){item: SOSContact ->

                ContactBox(contact = item)
            }
        }
    }

}


@Composable
fun ContactBox(contact : SOSContact){
    Column(
        modifier = Modifier
            .padding(all = 10.dp)
            .height(60.dp)
            .background(color = appThemeColor, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
    )
    {
        Text(text = contact.name,color = Color.White,
        modifier = Modifier.padding(start = 10.dp, top = 10.dp))

        Text(text = contact.contact.toString(),color = Color.White,
                modifier = Modifier.padding(start = 10.dp, top = 5.dp))
    }
}
@Composable
@Preview
fun SOSPagePreview(){
    SOSPage()
}