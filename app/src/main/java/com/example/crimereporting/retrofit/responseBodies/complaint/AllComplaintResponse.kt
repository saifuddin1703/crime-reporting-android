package com.example.crimereporting.retrofit.responseBodies.complaint

import com.example.crimereporting.models.crime
import com.google.gson.annotations.SerializedName

data class AllComplaintResponse(
    @SerializedName("status") val status : String = "",
    @SerializedName("results") val results : Int = 0,
    @SerializedName("data") val data : List<crime> = ArrayList(),
)
