package com.example.crimereporting.repository

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.example.crimereporting.models.PoliceStation
import com.example.crimereporting.retrofit.responseBodies.complaint.AllComplaintResponse
import com.example.crimereporting.retrofit.responseBodies.policeContacts.GetAllPoliceStationResponse
import com.example.crimereporting.services.PoliceContactService
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import kotlin.Comparator


class PoliceRepository @Inject constructor(
    private val policeContactService: PoliceContactService,
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context
){
    fun getNearbyPoliceContacts(onResult : (List<PoliceStation>)->Unit){
        policeContactService.getAllContacts(bearer_token = "Bearer ${userRepository.getTokenFromSharedPreferences()}")
            .enqueue(object : Callback<GetAllPoliceStationResponse>{
                override fun onResponse(
                    call: Call<GetAllPoliceStationResponse>,
                    response: Response<GetAllPoliceStationResponse>
                ) {
                    val listOfStation = response.body()?.data
//                    listOfStation?.sortedWith(
//                        comparator = Comparator { t, t2 ->
//                            userRepository.getUserCurrentLocation {
//                                t.crimeLocationCoordinate.coordinates
//                                val geocoder = Geocoder(context, Locale.getDefault())
////                                geocoder.
//                            }
//                            1
//                        }
//                    )
                    listOfStation?.let { onResult(it) }

                }

                override fun onFailure(call: Call<GetAllPoliceStationResponse>, t: Throwable) {
                    Log.d("tag",t.message.toString())
                }

            })
    }
}