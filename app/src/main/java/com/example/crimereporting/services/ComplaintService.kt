package com.example.crimereporting.services

import com.example.crimereporting.retrofit.requestBodies.complaint.CreateComplaintRequestBody
import com.example.crimereporting.retrofit.responseBodies.complaint.AllComplaintResponse
import com.example.crimereporting.retrofit.responseBodies.complaint.UpdateComplaintRequestBody
import com.example.crimereporting.retrofit.responseBodies.complaint.createComplaintResponse
import retrofit2.Call
import retrofit2.http.*

interface ComplaintService {

    @POST("api/complaint")
    fun createComplaint(@Header("Authorization") bearer_token : String,
                        @Body body : CreateComplaintRequestBody) : Call<createComplaintResponse>

    @GET("api/complaint/me")
    fun getMyComplaints(@Header("Authorization") bearer_token : String) : Call<AllComplaintResponse>

    // for user whose role is police officer
    @GET("api/complaint/assigned/me")
    fun getAssignedComplaints(@Header("Authorization") bearer_token: String)
    
    @PATCH("api/complaint/me/assigned/{id}")
    fun updateComplaint(
        @Header("Authorization")bearer_token: String,
        @Path("id") id : String,
        @Body updateComplaintRequestBody: UpdateComplaintRequestBody
    ) : Call<createComplaintResponse>

}