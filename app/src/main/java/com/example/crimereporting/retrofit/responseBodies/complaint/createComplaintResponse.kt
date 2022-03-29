package com.example.crimereporting.retrofit.responseBodies.complaint

import com.google.gson.annotations.SerializedName

data class createComplaintResponse(
    @SerializedName("status") val status : String,
    @SerializedName("data") val data: data
)
