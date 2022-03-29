package com.example.crimereporting.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crimereporting.models.Image
import com.example.crimereporting.models.user
import com.example.crimereporting.repository.UserRepository
import com.example.crimereporting.retrofit.responseBodies.image.imageResponse
import com.example.crimereporting.retrofit.responseBodies.user.GetUserResponse
import com.example.crimereporting.services.ImageUploadingService
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel() {
   @Inject lateinit var userRepository: UserRepository
   @Inject lateinit var imageUploadingService: ImageUploadingService
//   val currentUser : MutableLiveData<user> by lazy {
//       MutableLiveData<user>().also {
//           getCurrentUser {
//               currentUser.value = it
////               currentUser.
//           }
//       }
//   }

    private fun uploadImage(
        imageFile : File,
        onUploadSuccess : (imageUrl : Image) ->Unit,
        onUploadFail : (message : String) ->Unit
    ){

        val requestBody = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", filename = imageFile.name, requestBody)
        val pathBody = "profile".toRequestBody("multipart/form-data".toMediaTypeOrNull())

//        Log.d("tag",bodyMap.toString())
        imageUploadingService.uploadImage(
            bearer_token = "Bearer ${userRepository.getTokenFromSharedPreferences()}",
            path = pathBody,
            image = body
        ).enqueue(
            object : Callback<imageResponse>{
                override fun onResponse(
                    call: Call<imageResponse>,
                    response: Response<imageResponse>
                ) {

                    val result = response.body()?.result
                    result?.let {
                        onUploadSuccess(it)
                    }
                }

                override fun onFailure(call: Call<imageResponse>, t: Throwable) {
                    Log.d("tag","${t.message.toString()} at line 74 in ReportCrimeViewModel")
                    onUploadFail(t.message.toString())
                }

            }
        )

    }
    fun updateProfileImage(
        imageFile : File,
        onSuccess : (updatedUser : user) -> Unit,
        onFailure : (message : String) -> Unit
    ){

        uploadImage(
            imageFile = imageFile,
            onUploadSuccess ={
                userRepository.updateProfileImage(
                    it.url, onSuccess, onFailure
                )
            },
            onUploadFail = {
                onFailure(it)
            }
        )

    }
   fun getCurrentUser(
       onSuccess : (user) -> Unit,
       onFailure : (String) -> Unit
   ){
       userRepository.getCurrentUser().enqueue(
           object : Callback<GetUserResponse>{
               override fun onResponse(
                   call: Call<GetUserResponse>,
                   response: Response<GetUserResponse>
               ) {
                    val currentUser = response.body()?.data
                   if (currentUser != null) {
                       onSuccess(currentUser)
                   }
               }

               override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                   Log.d("tag",t.message.toString())
                   onFailure(t.message.toString())
               }

           }
       )
   }
}