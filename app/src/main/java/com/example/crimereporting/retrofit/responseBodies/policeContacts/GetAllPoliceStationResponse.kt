package com.example.crimereporting.retrofit.responseBodies.policeContacts

import com.example.crimereporting.models.PoliceStation
import com.google.gson.annotations.SerializedName

data class GetAllPoliceStationResponse(
    @SerializedName("status") val status : String,
    @SerializedName("results") val results : Int,
    @SerializedName("data") val data : ArrayList<PoliceStation>
)
