package com.example.crimereporting.services

import com.example.crimereporting.retrofit.responseBodies.user.GetUserResponse
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

interface UserService {

    @GET("api/user/me")
    fun getCurrentUser(@Header("Authorization") bearer_token : String) : Call<GetUserResponse>

    @PATCH("api/user/update/me")
    fun updateUser(
        @Header("Authorization") bearer_token: String,
        @Body updateDetails :  JSONObject
    ) : Call<GetUserResponse>
}