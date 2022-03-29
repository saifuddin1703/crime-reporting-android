package com.example.crimereporting.models

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("url") val url : String,
    @SerializedName("secure_url") val secure_url : String
)
