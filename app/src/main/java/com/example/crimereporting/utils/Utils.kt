package com.example.crimereporting.utils

import android.content.Context
import com.example.crimereporting.models.Location
import com.example.crimereporting.models.crime

fun Int.toPx(context: Context)=
    this* context.resources.displayMetrics.density

fun getContacts(context: Context){
    val cursor = context.contentResolver
//    val uri =
}
