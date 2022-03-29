package com.example.crimereporting.retrofit.responseBodies.complaint

import com.google.gson.annotations.SerializedName

data class UpdateComplaintRequestBody(
    @SerializedName("status") val status : String
)
