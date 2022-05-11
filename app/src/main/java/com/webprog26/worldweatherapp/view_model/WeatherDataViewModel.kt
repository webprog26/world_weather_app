package com.webprog26.worldweatherapp.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webprog26.worldweatherapp.location.LocationProvider
import com.webprog26.worldweatherapp.weather_data.City
import com.webprog26.worldweatherapp.weather_data.WeatherData
import com.webprog26.worldweatherapp.weather_data.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class WeatherDataViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _cityData = MutableLiveData<City>()
    val cityData: LiveData<City>
        get() = _cityData

    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData>
        get() = _weatherData


    fun updateWeatherData(
        latLng: LocationProvider.LatLng = LocationProvider.LatLng(0.0, 0.0),
        appId: String = "",
        forceNetworkUpdate: Boolean = false
    ) {
        val weatherDataDeferred = viewModelScope.async {
            _weatherData.value = repository.updateWeatherData(latLng,
                appId, forceNetworkUpdate)
        }

        val cityDataDeferred = viewModelScope.async {
            val result = repository.updateCityData(latLng, appId)
            result?.let {
                _cityData.value = it
            }
        }
        viewModelScope.launch {
            awaitAll(weatherDataDeferred, cityDataDeferred)
        }
    }
}