package com.example.crimereporting.models

import com.google.gson.annotations.SerializedName

data class PoliceStation(
    @SerializedName("coordinates") val coordinates : coordinates,
    @SerializedName("_id") val _id : String,
    @SerializedName("stationName") val stationName : String,
    @SerializedName("headName") val headName : String,
    @SerializedName("phone") val phone : String,
    @SerializedName("address") val address : String
)
