package com.example.crimereporting.models

import com.google.gson.annotations.SerializedName

data class crime(
    @SerializedName("_id") val _id : String,
    @SerializedName("title") val crimeTitle : String,
    @SerializedName("description") val crimeDescription : String,
    @SerializedName("user") val user : String,
    @SerializedName("crimeLocation") val crimeLocation : String,
    @SerializedName("crimeLocationCoordinates") val crimeLocationCoordinate : coordinates,
    @SerializedName("images") val images : ArrayList<String>,
    @SerializedName("status") val status : String = "Filed",
    @SerializedName("assignedOfficer") val assignedOfficer : String,
    @SerializedName("headquarter") val headQuarter : String
    )
