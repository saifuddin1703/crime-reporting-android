package com.example.crimereporting.retrofit.responseBodies.user

import com.example.crimereporting.models.user
import com.google.gson.annotations.SerializedName

data class GetUserResponse(
    @SerializedName("status") val status : String,
    @SerializedName("data") val data : user
)
