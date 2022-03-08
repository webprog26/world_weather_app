package com.webprog26.worldweatherapp.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webprog26.worldweatherapp.network.WeatherApi
import com.webprog26.worldweatherapp.network.createWeatherApi
import com.webprog26.worldweatherapp.weather_data.WeatherData
import kotlinx.coroutines.launch

class WeatherDataViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData>
    get() = _weatherData


    fun updateWeatherData(
        lat: Double,
        lon: Double,
        exclude: String,
        appId: String
    ) {
        val weatherApi = createWeatherApi()
        viewModelScope.launch {
            Log.i("weather_data_deb", Thread.currentThread().name)
            _weatherData.value = weatherApi
                .getWeatherData(lat, lon, exclude, appId)
        }
    }
}