package com.webprog26.worldweatherapp.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient

class LocationProvider() {

    private val _lastKnownLocationData = MutableLiveData<LatLng>()
    val lastKnownLocationData: LiveData<LatLng>
        get() = _lastKnownLocationData



    data class LatLng(
        var latitude: Double,
        var longitude: Double
    )

    fun getUserLocation(client: FusedLocationProviderClient) {
        try {
            client.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val latLng = LatLng(location.latitude, location.longitude)
                    _lastKnownLocationData.value = latLng
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}