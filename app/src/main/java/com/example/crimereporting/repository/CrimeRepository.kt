package com.example.crimereporting.repository

import com.example.crimereporting.retrofit.requestBodies.complaint.CreateComplaintRequestBody
import com.example.crimereporting.retrofit.responseBodies.complaint.AllComplaintResponse
import com.example.crimereporting.retrofit.responseBodies.complaint.UpdateComplaintRequestBody
import com.example.crimereporting.retrofit.responseBodies.complaint.createComplaintResponse
import com.example.crimereporting.services.ComplaintService
import retrofit2.Call
import javax.inject.Inject

class CrimeRepository @Inject constructor(
    private val complaintService : ComplaintService,
    private val userRepository: UserRepository
) {

    fun getAllComplaints(): Call<AllComplaintResponse> {
       return complaintService.getMyComplaints(bearer_token = "Bearer ${userRepository.getTokenFromSharedPreferences()}")
    }

    fun reportCrime(body : CreateComplaintRequestBody): Call<createComplaintResponse> {
        return complaintService.createComplaint(body = body, bearer_token = "Bearer ${userRepository.getTokenFromSharedPreferences()}")
    }

    fun updateComplaintStatus(complaintID : String,body : UpdateComplaintRequestBody) = complaintService.updateComplaint(
        bearer_token = "Bearer ${userRepository.getTokenFromSharedPreferences()}",
        updateComplaintRequestBody = body,
        id = complaintID
    )

}