package com.example.crimereporting.services

import com.example.crimereporting.retrofit.responseBodies.image.ImagesResponse
import com.example.crimereporting.retrofit.responseBodies.image.imageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ImageUploadingService {

    @Multipart
    @POST("api/util/upload/images")
    fun uploadMultipleImages(
        @Header("Authorization") bearer_token : String,
        @Part("path") path : RequestBody,
        @Part images : List<MultipartBody.Part>
    ) : Call<ImagesResponse>

    @Multipart
    @POST("api/util/upload/image")
    fun uploadImage(
        @Header("Authorization") bearer_token : String,
        @Part("path") path : RequestBody,
        @Part image: MultipartBody.Part
    ) : Call<imageResponse>

}