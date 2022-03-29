package com.example.crimereporting.ui.nearbyPoliceStation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crimereporting.models.PoliceStation
import com.example.crimereporting.R
import com.example.crimereporting.models.coordinates
import com.example.crimereporting.ui.components.ui.theme.appThemeColor

@Composable
fun NearbyPoliceStationPage(
    viewModel: NearbyPoliceStationViewModel
) {

    val allPoliceStation = viewModel.getNearbyPoliceContacts().observeAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .size(40.dp),
            color = appThemeColor
        )
        allPoliceStation?.let {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                items(allPoliceStation) { item: PoliceStation ->
                    PoliceStationBox(policeStation = item)

                }
            }
        }
    }

}

@Composable
fun PoliceStationBox(policeStation: PoliceStation){
    Box(modifier = Modifier
        .padding(all = 5.dp)
        .fillMaxWidth()
        .background(
            color = appThemeColor, shape = RoundedCornerShape(20.dp)
        ))
        {
            Column(
                modifier = Modifier
                    .padding(start = 15.dp, end = 5.dp, bottom = 10.dp)
                    .align(alignment = Alignment.CenterStart)
            )
            {
                Text(
                    text = policeStation.stationName, color = Color.White,
                    modifier = Modifier.padding(
                       top = 5.dp
                    ),
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )

                Text(
                    text = policeStation.address, color = Color.White,
                    modifier = Modifier.padding(
                       top = 5.dp
                    )
                )

                Text(
                    text = buildAnnotatedString
                    {
                      withStyle(style = SpanStyle(
                          color = Color.White, fontWeight = FontWeight.Bold
                      ))   {
                          append("Phone : ")
                      }

                      withStyle(style = SpanStyle(color = Color.White)){
                          append(policeStation.phone.toString())
                      }
                    }, color = Color.White,
                    modifier = Modifier.padding(
                        top = 5.dp
                    )
                )
            }

            val context = LocalContext.current
            Image(painter = painterResource(id = R.drawable.ic_baseline_call_24),
                contentDescription = "call"
            , modifier = Modifier
                    .padding(end = 15.dp)
                    .size(30.dp)
                    .clip(shape = CircleShape)
                    .align(alignment = Alignment.CenterEnd)
                    .clickable {
                        val intent = Intent(Intent.ACTION_CALL)
                        intent.data = Uri.parse(policeStation.phone)
                        context.startActivity(intent)
                    })
        }
}
@Preview
@Composable
fun NearbyPoliceStationPreview(){
    NearbyPoliceStationPage(viewModel = viewModel())
}