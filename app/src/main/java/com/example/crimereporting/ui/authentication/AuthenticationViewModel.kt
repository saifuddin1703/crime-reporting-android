package com.example.crimereporting.ui.authentication

import androidx.lifecycle.ViewModel
import com.example.crimereporting.repository.UserRepository
import com.example.crimereporting.retrofit.requestBodies.authentication.LoginVerifyOTPRequestBody
import com.example.crimereporting.retrofit.requestBodies.authentication.SendOTPRequestBody
import com.example.crimereporting.retrofit.requestBodies.authentication.SignupVerifyOTPRequestBody
import com.example.crimereporting.services.AuthenticationService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(): ViewModel() {

    @Inject lateinit var authenticationService: AuthenticationService
    @Inject lateinit var userRepository : UserRepository

    fun sendOTP(body: SendOTPRequestBody) = authenticationService.sendOTP(body)

    fun loignverifyOTP(body: LoginVerifyOTPRequestBody) = authenticationService.loginVerifyOTP(body)

    fun signupverifyOTP(body: SignupVerifyOTPRequestBody) = authenticationService.signupVerifyOTP(body)

    fun updateOrPutToken(token: String?) {
        userRepository.putTokenIntoSharedPreferences(token)
    }

    fun getToken() = userRepository.getTokenFromSharedPreferences()
}