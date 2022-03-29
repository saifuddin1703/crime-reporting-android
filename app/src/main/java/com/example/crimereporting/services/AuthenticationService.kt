package com.example.crimereporting.services

import com.example.crimereporting.retrofit.requestBodies.authentication.SendOTPRequestBody
import com.example.crimereporting.retrofit.requestBodies.authentication.LoginVerifyOTPRequestBody
import com.example.crimereporting.retrofit.requestBodies.authentication.SignupVerifyOTPRequestBody
import com.example.crimereporting.retrofit.responseBodies.authentication.LoginOTPResponse
import com.example.crimereporting.retrofit.responseBodies.authentication.SignupOTPResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("api/auth/sendOTP")
    fun sendOTP(@Body body: SendOTPRequestBody) : Call<String>

    @POST("api/auth/login/verifyOTP")
    fun loginVerifyOTP(@Body body: LoginVerifyOTPRequestBody) : Call<LoginOTPResponse>

    @POST("api/auth/signup/verifyOTP")
    fun signupVerifyOTP(@Body body: SignupVerifyOTPRequestBody) : Call<SignupOTPResponse>
}