package com.example.crimereporting.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.SocketKeepalive
import android.os.Looper
import com.example.crimereporting.MainActivity
import com.example.crimereporting.models.user
import com.example.crimereporting.retrofit.requestBodies.authentication.LoginVerifyOTPRequestBody
import com.example.crimereporting.retrofit.requestBodies.authentication.SendOTPRequestBody
import com.example.crimereporting.retrofit.requestBodies.authentication.SignupVerifyOTPRequestBody
import com.example.crimereporting.retrofit.responseBodies.user.GetUserResponse
import com.example.crimereporting.services.AuthenticationService
import com.example.crimereporting.services.UserService
import com.example.crimereporting.utils.DEFAULT_TOKEN
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService,
    @ApplicationContext private val context : Context
) {
//    val currentUser : user = user(
//        0,
//        "saifuddin",
//        "password"
//    )

    var token : String? = null

    fun putTokenIntoSharedPreferences(token: String?){
        val sharedPreferences = context.getSharedPreferences("tokenRegister",Context.MODE_PRIVATE)
        sharedPreferences.edit().apply{
            putString("token",token)
            apply()
        }
    }

    fun getUserCurrentLocation(onSuccess : (LatLng)->Unit){
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            val location= it
            if (location==null){
                requestNewLocationData(context) {
                    location?.let {
                        val latLng = LatLng(location.latitude, location.longitude)
                        onSuccess(latLng)
                    }
                }
            }else{
                val latLng = LatLng(location.latitude,location.longitude)
                onSuccess(latLng)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(context: Context,onresult : (location : Location) ->Unit) {
        // Initializing LocationRequest
        // object with appropriate methods
        val mLocationRequest =  LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        val mLocationCallback =  object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.getLastLocation()
                onresult(location)
            }
        }

        // setting LocationRequest
        // on FusedLocationClient
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper()!!)
    }

    fun getTokenFromSharedPreferences(): String? {
//         val intent = Intent(contex)
        return if (token == null){
//            MainActivity.cla
            val sharedPreferences = context.getSharedPreferences("tokenRegister",Context.MODE_PRIVATE)
            token = sharedPreferences.getString("token", DEFAULT_TOKEN)
            token
        }else{
            token
        }
    }

    fun getCurrentUser() = userService.getCurrentUser(bearer_token = "Bearer ${getTokenFromSharedPreferences()}")

    fun updateProfileImage(
        imageURl : String,
        onSuccess : (updatedUser : user) -> Unit,
        onFailure : (message : String) -> Unit
    ){
        val jsonObject = JsonObject()
        jsonObject.addProperty("profileImage",imageURl)
        userService.updateUser(
            bearer_token = "Bearer ${getTokenFromSharedPreferences()}",
            updateDetails = jsonObject
        ).enqueue(
            object : Callback<GetUserResponse>{
                override fun onResponse(
                    call: Call<GetUserResponse>,
                    response: Response<GetUserResponse>
                ) {
                    if (response.isSuccessful){
                        val updatedUser = response.body()?.data
                        updatedUser?.let {
                            onSuccess(it)
                        }
                    }else{
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                    onFailure(t.message.toString())
                }

            }
        )
    }
//    fun put
}