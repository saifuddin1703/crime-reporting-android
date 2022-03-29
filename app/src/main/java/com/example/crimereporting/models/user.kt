package com.example.crimereporting.models

import com.google.gson.annotations.SerializedName

data class user(
    @SerializedName("_id") val _id : String,
    @SerializedName("name") val name : String = "",
    @SerializedName("phone") val phoneNumber : String = "",
    @SerializedName("role") val role: String = "",
    @SerializedName("AadharNo") val AadharNumber: String? = "",
    @SerializedName("address")  val address : String? = "",
    @SerializedName("homeCoordinates")  val homeCoordinates : coordinates,
    @SerializedName("sosContacts")  val sosContacts : ArrayList<SOSContact> ,
    @SerializedName("profileImage")  val profileImage : String = "",
)
