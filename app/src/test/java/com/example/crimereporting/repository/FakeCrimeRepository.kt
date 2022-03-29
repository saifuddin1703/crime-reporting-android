package com.example.crimereporting.repository

import com.example.crimereporting.models.coordinates
import com.example.crimereporting.models.crime

class FakeCrimeRepository(
    private val crimeList: ArrayList<crime>
) {
    fun getAllComplaints(): ArrayList<crime> {
       return crimeList
    }

    fun reportCrime() {
        val crime = crime(
            _id = "1",
            crimeTitle = "title",
            crimeDescription = "description",
            user = "userId",
            crimeLocation = "address",
            crimeLocationCoordinate = coordinates(coordinates = ArrayList()),
            images = ArrayList(),
            status = "filed",
            assignedOfficer = "officer id",
            headQuarter = "rajmoholla"
        )

        crimeList.add(crime)
    }

    fun updateComplaintStatus(complaintID: String) {
        crimeList.sortedWith(
            comparator = { t, t2 ->
                t._id.toInt() - t2._id.toInt()
            }
        )
        crimeList.binarySearch {
            when {
                it._id == complaintID -> 0
                it._id.toInt() < complaintID.toInt() -> 1
                else -> -1
            }

        }
    }
}

