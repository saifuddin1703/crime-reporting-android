package com.example.crimereporting.ui.mycrimes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crimereporting.models.crime
import com.example.crimereporting.models.user
import com.example.crimereporting.repository.CrimeRepository
import com.example.crimereporting.repository.UserRepository
import com.example.crimereporting.retrofit.responseBodies.complaint.AllComplaintResponse
import com.example.crimereporting.retrofit.responseBodies.complaint.UpdateComplaintRequestBody
import com.example.crimereporting.retrofit.responseBodies.complaint.createComplaintResponse
import com.example.crimereporting.retrofit.responseBodies.user.GetUserResponse
//import androidx.compose.runtime.
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MyCrimesViewModel @Inject constructor(): ViewModel() {

    @Inject lateinit var repository: CrimeRepository
    @Inject lateinit var userRepository: UserRepository

    val currentUser : MutableLiveData<user> by lazy {
        MutableLiveData<user>().also {
            userRepository.getCurrentUser().enqueue(
                object : Callback<GetUserResponse>{
                    override fun onResponse(
                        call: Call<GetUserResponse>,
                        response: Response<GetUserResponse>
                    ) {
                        currentUser.value = response.body()?.data
                    }

                    override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                       Log.d("tag",t.toString())
                    }

                }
            )
        }
    }
    private val allComplaints : MutableLiveData<List<crime>> by lazy {
        MutableLiveData<List<crime>>().also {
            getlist(
                onDataResponse = {crimes : List<crime> ->
                    allComplaints.value= crimes
                }
            )
        }
    }

    fun getAllUserComplaints() = allComplaints

//    fun reportCrime() = repos

    private fun getlist(
        onDataResponse : (List<crime>) -> Unit
    ) {
        repository.getAllComplaints().enqueue(object : Callback<AllComplaintResponse>{
            override fun onResponse(
                call: Call<AllComplaintResponse>,
                response: Response<AllComplaintResponse>
            ) {
                response.body()?.data?.let { onDataResponse(it) }
            }

            override fun onFailure(call: Call<AllComplaintResponse>, t: Throwable) {
                Log.d("tag",t.message.toString())
            }

        })
    }

    fun updateComplaintStatus(
        complaintID : String,
        body : UpdateComplaintRequestBody,
        onUpdate : (crime)->Unit)
    {
        repository.updateComplaintStatus(
            complaintID, body
        ).enqueue(
            object : Callback<createComplaintResponse>{
                override fun onResponse(
                    call: Call<createComplaintResponse>,
                    response: Response<createComplaintResponse>
                ) {
                    response.body()?.data?.data?.let { onUpdate(it) }
                }

                override fun onFailure(call: Call<createComplaintResponse>, t: Throwable) {
                   Log.d("tag",t.message.toString())
                }

            }
        )
    }


    fun refresh(){
        getlist {
            allComplaints.value = it
        }
    }
}