package com.example.crimereporting.retrofit.requestBodies.complaint

import com.example.crimereporting.models.Image
import com.example.crimereporting.models.coordinates
import com.google.gson.annotations.SerializedName

data class CreateComplaintRequestBody(
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("crimeLocation") val crimeLocation : String,
    @SerializedName("user") val user : String,
    @SerializedName("crimeLocationCoordinates") val crimeLocationCoordinate : coordinates,
    @SerializedName("images") val images : List<String>,
)
