package com.example.crimereporting.retrofit.requestBodies.authentication

import com.google.gson.annotations.SerializedName

data class SendOTPRequestBody(
    @SerializedName("phone") val phone : String = ""
)
