package com.example.crimereporting.models

import com.google.gson.annotations.SerializedName

data class coordinates(
    @SerializedName("type") val type : String = "Point",
    @SerializedName("coordinates") val coordinates: ArrayList<Double> = ArrayList(2), //0 for latitude 1 for longitude,
    @SerializedName("address") val address : String = "Default Address",
    @SerializedName("description") val description : String = "Default address description",
)
