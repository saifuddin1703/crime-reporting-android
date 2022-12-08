package com.example.crimereporting.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crimereporting.models.SOSContact
import com.example.crimereporting.models.user
import com.example.crimereporting.repository.UserRepository
import com.example.crimereporting.retrofit.responseBodies.user.GetUserResponse
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    @Inject lateinit var userRepository: UserRepository

    val sosContactList by lazy {
        MutableLiveData<List<SOSContact>>().also {
            getSosContactList(
                onSuccess = {sosContacts: List<SOSContact> ->
                   it.value = sosContacts
                },
                onFailure = {

                }
            )
        }
    }

    private fun getSosContactList(
        onSuccess: (sosContacts: List<SOSContact>) -> Unit,
        onFailure: (message: String) -> Unit
    ){
        userRepository.getCurrentUser().enqueue(
            object : Callback<GetUserResponse>{
                override fun onResponse(
                    call: Call<GetUserResponse>,
                    response: Response<GetUserResponse>
                ) {
//                    response.body().data.
                    if (response.isSuccessful){
                        onSuccess(response.body()?.data?.sosContacts!!)
                    }

                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                    onFailure(t.message.toString())
                }

            }
        )
    }
    fun updateSosContactsList(list: List<SOSContact>,onSuccess : (updatedUser : user) -> Unit,
                              onFailure : (message : String) -> Unit){
        userRepository.updateSosContactList(list, onSuccess = onSuccess, onFailure = onFailure)
    }
}