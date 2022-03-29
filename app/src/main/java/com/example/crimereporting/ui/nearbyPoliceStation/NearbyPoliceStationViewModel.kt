package com.example.crimereporting.ui.nearbyPoliceStation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crimereporting.models.PoliceStation
import com.example.crimereporting.models.crime
import com.example.crimereporting.repository.PoliceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
//import com.example.crimereporting.repository.

@HiltViewModel
class NearbyPoliceStationViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var policeRepository : PoliceRepository

    private val allPoliceContact : MutableLiveData<List<PoliceStation>>by lazy {
        MutableLiveData<List<PoliceStation>>().also {
            policeRepository.getNearbyPoliceContacts { list ->
                allPoliceContact.value = list
            }
        }
    }

    fun getNearbyPoliceContacts() = allPoliceContact

}