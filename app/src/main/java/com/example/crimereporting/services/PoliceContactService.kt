package com.example.crimereporting.services

import com.example.crimereporting.retrofit.responseBodies.complaint.AllComplaintResponse
import com.example.crimereporting.retrofit.responseBodies.policeContacts.GetAllPoliceStationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface PoliceContactService {

    @GET("api/contact")
    fun getAllContacts(@Header("Authorization") bearer_token : String) : Call<GetAllPoliceStationResponse>
}