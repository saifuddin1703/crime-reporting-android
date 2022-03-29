package com.example.crimereporting.ui.reportCrimeFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.crimereporting.models.Image
import com.example.crimereporting.models.coordinates
import com.example.crimereporting.models.crime
import com.example.crimereporting.repository.CrimeRepository
import com.example.crimereporting.repository.UserRepository
import com.example.crimereporting.retrofit.requestBodies.complaint.CreateComplaintRequestBody
import com.example.crimereporting.retrofit.responseBodies.complaint.createComplaintResponse
import com.example.crimereporting.retrofit.responseBodies.image.ImagesResponse
import com.example.crimereporting.retrofit.responseBodies.image.imageResponse
import com.example.crimereporting.retrofit.responseBodies.user.GetUserResponse
import com.example.crimereporting.services.ImageUploadingService
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReportCrimeViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var crimeRepository: CrimeRepository
    @Inject lateinit var imageUploadingService: ImageUploadingService
    @Inject lateinit var userRepository: UserRepository

    fun reportCrime(title : String,
                    description : String,
                    crimeLocation : String,
                    crimeLocationCoordinate : coordinates,
                    images : List<String>,
                    status : String = "Filed",
                    onReportingSuccess : (crime) ->Unit,
                    onReportingFailed : (errorMessage : String) ->Unit
    )
    {
        userRepository.getCurrentUser().enqueue(
            object : Callback<GetUserResponse>{
                override fun onResponse(
                    call: Call<GetUserResponse>,
                    response: Response<GetUserResponse>
                ) {
                    val user = response.body()?.data
                    val createComplaintRequestBody = CreateComplaintRequestBody(
                        title = title,
                        description = description,
                        crimeLocation = crimeLocation,
                        user = user?._id!!,
                        crimeLocationCoordinate = crimeLocationCoordinate,
                        images = images,
                    )
                    crimeRepository.reportCrime(body = createComplaintRequestBody)
                        .enqueue(
                            object : Callback<createComplaintResponse>{
                                override fun onResponse(
                                    call: Call<createComplaintResponse>,
                                    response: Response<createComplaintResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        val crime = response.body()?.data?.data
                                        crime?.let { onReportingSuccess(it) }
                                    }else{
                                       val errorMessage = response.message()
                                        onReportingFailed(errorMessage)
                                    }
                                }

                                override fun onFailure(
                                    call: Call<createComplaintResponse>,
                                    t: Throwable
                                ) {
                                   Log.d("tag","${t.message.toString()} at line 74 in ReportCrimeViewModel")
                                    onReportingFailed(t.message.toString())
                                }

                            }
                        )
                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                    Log.d("tag","${t.message.toString()} at line 74 in ReportCrimeViewModel")

                }

            }
        )

    }


    fun uploadCrimeImages(
        listOfImageFiles : List<File>,
        onUploadSuccess : (List<String>) ->Unit,
        onUploadFail : (message : String) ->Unit
    ){
//        val bodyMap = HashMap<String, RequestBody>()
        val partList = ArrayList<MultipartBody.Part>()
        listOfImageFiles.forEach {
            val requestBody = it.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("images",it.name,requestBody)
            partList.add(part)
//            bodyMap["images"] = requestBody
        }

        val pathBody = "complaint".toRequestBody("multipart/form-data".toMediaTypeOrNull())

//        Log.d("tag",bodyMap.toString())
        imageUploadingService.uploadMultipleImages(
            bearer_token = "Bearer ${userRepository.getTokenFromSharedPreferences()}",
            path = pathBody,
            images = partList
        ).enqueue(
            object : Callback<ImagesResponse>{
                override fun onResponse(
                    call: Call<ImagesResponse>,
                    response: Response<ImagesResponse>
                ) {

                    if (response.isSuccessful) {
                        val result = response.body()?.result
                        result?.let {allList->
                            val imageList = ArrayList<String>()
                            allList.forEach{
                                imageList.add(it.url)
                            }
                            onUploadSuccess(imageList)
                        }
                    }else{
                        onUploadFail(response.message())
                    }
                }

                override fun onFailure(call: Call<ImagesResponse>, t: Throwable) {
                    Log.d("tag","${t.message.toString()} at line 125 in $this@ReportCrimeViewModel")
                    onUploadFail(t.message.toString())
                }

            }
        )

    }


}