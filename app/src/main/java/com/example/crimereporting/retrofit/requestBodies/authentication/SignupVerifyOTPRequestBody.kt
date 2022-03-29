package com.example.crimereporting.retrofit.requestBodies.authentication

import com.google.gson.annotations.SerializedName

data class SignupVerifyOTPRequestBody(
    @SerializedName("phone") val phone : String = "",
    @SerializedName("code") val code : String = "",
    @SerializedName("name") val name : String = "",
)
