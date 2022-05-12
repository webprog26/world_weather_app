package com.webprog26.worldweatherapp.location

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.*

@RunWith(AndroidJUnit4::class)
class LocationProviderTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `getUserLocation() updates device location`() {

        val locationProvider = LocationProvider()

        val latLng = LocationProvider.LatLng(1.205, 2.105)

        val client: FusedLocationProviderClient = mock()

        val location: Location = mock()

        val lastLocation: Task<Location> = mock()

        val locationObserver: Observer<LocationProvider.LatLng> = mock()

        locationProvider.lastKnownLocationData.observeForever(locationObserver)

        whenever(lastLocation.result).thenReturn(location)

        whenever(location.latitude).thenReturn(latLng.latitude)
        whenever(location.longitude).thenReturn(latLng.longitude)

        whenever(client.lastLocation).thenReturn(lastLocation)

        whenever(client.lastLocation.addOnSuccessListener(any()))
            .thenAnswer { invocation ->
                if (invocation != null) {
                    val listener = invocation.arguments[0] as OnSuccessListener<Location>
                    listener.onSuccess(location)
                }
                null
            }

        locationProvider.getUserLocation(client)
        verify(locationObserver).onChanged(eq(latLng))

    }
}