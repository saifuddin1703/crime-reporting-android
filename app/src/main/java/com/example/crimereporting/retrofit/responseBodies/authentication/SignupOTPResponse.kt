package com.example.crimereporting.retrofit.responseBodies.authentication

import com.google.gson.annotations.SerializedName

data class SignupOTPResponse(
    @SerializedName("status") val status : String = "",
    @SerializedName("message") val message : String = "",
    @SerializedName("token") val token : String = "",

)
