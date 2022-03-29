package com.example.crimereporting.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.IllegalStateException


@Composable
fun MyMap(
    modifier: Modifier,
    onMapReady: (googleMap: GoogleMap) -> Unit
){

    val context = LocalContext.current
    val mapView = remember {
        MapView(context)
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    lifecycle.addObserver(rememberMapLifeCycleObserver(mapView = mapView))
    AndroidView(factory = {
        mapView.apply {
            mapView.getMapAsync {googleMap->
                val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
                fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                    val location= it
                        if (location==null){
                            requestNewLocationData(context){
                                location?.let {
                                    val latLng = LatLng(location.latitude, location.longitude)
                                    googleMap.addMarker(MarkerOptions().position(latLng))
                                    googleMap.moveCamera(
                                        CameraUpdateFactory.newLatLngZoom(
                                            latLng,
                                            15f
                                        )
                                    )

                                }
                            }
                        }else{
                            val latLng = LatLng(location.latitude,location.longitude)
                            googleMap.addMarker(MarkerOptions().position(latLng))
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f))
                        }

//                    googleMap.onclick
                }
                onMapReady(googleMap)
            }
        }
    },
    modifier = modifier)
}

@SuppressLint("MissingPermission")
fun requestNewLocationData(context: Context,onresult : (location : Location) ->Unit) {
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
@Composable
fun rememberMapLifeCycleObserver(mapView: MapView) : LifecycleEventObserver= remember {
    LifecycleEventObserver(){_,event ->
        when(event){
            Lifecycle.Event.ON_CREATE ->{
                mapView.onCreate(Bundle())
            }
            Lifecycle.Event.ON_START ->{
                mapView.onStart()
            }
            Lifecycle.Event.ON_RESUME ->{
                mapView.onResume()
            }
            Lifecycle.Event.ON_PAUSE ->{
                mapView.onPause()
            }
            Lifecycle.Event.ON_STOP ->{
                mapView.onStop()
            }
            Lifecycle.Event.ON_DESTROY ->{
                mapView.onDestroy()
            }
            else -> {
                throw IllegalStateException()
            }
        }

    }
}