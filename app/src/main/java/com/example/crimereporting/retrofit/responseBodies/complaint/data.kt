package com.example.crimereporting.retrofit.responseBodies.complaint

import com.example.crimereporting.models.crime
import com.google.gson.annotations.SerializedName

data class data(
    @SerializedName("data") val data : crime
)
