package com.example.crimereporting.models

import com.google.gson.annotations.SerializedName

data class SOSContact(
    @SerializedName("name") val name : String,
    @SerializedName("contact")   val contact : Long
)
