package com.example.crimereporting.retrofit.responseBodies.image

import com.example.crimereporting.models.Image
import com.google.gson.annotations.SerializedName

data class imageResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("result") val result : Image
)
