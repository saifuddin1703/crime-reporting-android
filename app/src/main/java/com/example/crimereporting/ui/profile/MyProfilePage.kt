package com.example.crimereporting.ui.profile

import android.util.Log
import android.widget.Button
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crimereporting.R
import com.example.crimereporting.models.coordinates
import com.example.crimereporting.models.user
import com.example.crimereporting.ui.components.FailedScreen
import com.example.crimereporting.ui.components.ui.theme.appThemeColor
import com.example.crimereporting.utils.FileOperations
import com.skydoves.landscapist.glide.GlideImage
import java.io.File

@Composable
fun MyProfilePage(
    viewModel: ProfileViewModel
){
//    val userRepository = UserRepository()
//    val currentUserState = viewModel.currentUser.observeAsState()
    val currentUserState = remember {
        mutableStateOf<user?>(null)
    }

    Box(modifier = Modifier.fillMaxSize()){
        CircularProgressIndicator(
            modifier = Modifier
                .size(40.dp)
                .align(alignment = Center),
            color = appThemeColor
        )
    }

    var isFailed by remember{
        mutableStateOf(false)
    }
    viewModel.getCurrentUser (
        onSuccess = {
            currentUserState.value = it
            Log.d("tag",it.toString())
        },
        onFailure = {
            isFailed = true
        }
            )
    currentUserState.value?.let {currentUser->
        var userProfileImage by remember {
            mutableStateOf(currentUser.profileImage)
        }

        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        )
        {
            GlideImage(
                imageModel = userProfileImage,
                contentDescription ="",
                modifier = Modifier
                    .align(alignment = CenterHorizontally)
                    .padding(top = 50.dp,bottom = 15.dp)
                    .size(100.dp)
                    .border(width = 2.dp, color = Color.Black, shape = CircleShape)
                    .clip(CircleShape),
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(30.dp)
                            .align(alignment = Center)
                    )
                }
            )
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = {uri ->
//                            isUploading = true
                    Log.d("tag",uri.toString())
                    uri?.let {
                        val imageFile = FileOperations.getFile(context, uri)
                        imageFile?.let {
                            Log.d("tag",it.name)
                            viewModel.updateProfileImage(
                                imageFile = it,
                                onSuccess = {user->
                                    userProfileImage = user.profileImage
                                },
                                onFailure = {message->
                                    Log.d("tag",message)
                                }
                            )
                        }
                    }
                })
            Text(text = "edit image",
                modifier = Modifier
                    .align(alignment = CenterHorizontally)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(20.dp))
                    .border(color = Color.Black, width = 1.dp, shape = RoundedCornerShape(20.dp))
                    .clickable {
                        launcher.launch("image/*")
                    }
                    .padding(all = 10.dp)
            )
            InformationBox(field = "Phone Number", value = currentUser.phoneNumber)

            InformationBox(field = "Name", value = currentUser.name)

//            InformationBox(field = "Aadhar Number", value = currentUser.AadharNumber.toString())

            InformationBox(field = "Address", value = if (currentUser.address == null) " Indore" else currentUser.address.toString())

//            Button
        }
    }
    if (isFailed){
        FailedScreen {
            viewModel.getCurrentUser (
                onSuccess = {
                    currentUserState.value = it
                },
                onFailure = {
                    isFailed = false
                }
            )
        }
    }

}


@Composable
fun InformationBox(field : String,value : String){
    Text(text = field,
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
                .align(alignment = CenterStart)
        )
//        Image(painter = painterResource(id = R.drawable.ic_baseline_edit_24),
//            contentDescription = "",
//            modifier = Modifier
//                .padding(end = 10.dp)
//                .size(30.dp)
//                .align(alignment = CenterEnd))
    }


}
@Preview
@Composable
fun MyProfilePagePreview(){
    MyProfilePage(
        viewModel = viewModel()
    )
}