package com.webprog26.worldweatherapp.location

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices

class LocationProvider(private val activity: AppCompatActivity) {

    private val _lastKnownLocationData = MutableLiveData<LatLng>()
    val lastKnownLocationData: LiveData<LatLng>
    get() = _lastKnownLocationData

    private val client by lazy {
        LocationServices.getFusedLocationProviderClient(activity)
    }

    data class LatLng(
        var latitude: Double,
        var longitude: Double
    )

    fun getUserLocation() {
       try {
           client.lastLocation.addOnSuccessListener { location ->
                val latLng = LatLng(location.latitude, location.longitude)
               _lastKnownLocationData.value = latLng
           }
       } catch (e: SecurityException) {
           e.printStackTrace()
       }
    }
}