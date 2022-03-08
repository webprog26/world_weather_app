package com.webprog26.worldweatherapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webprog26.worldweatherapp.WorldWeatherApplication
import com.webprog26.worldweatherapp.location.LocationProvider
import com.webprog26.worldweatherapp.network.createWeatherApi
import com.webprog26.worldweatherapp.weather_data.City
import com.webprog26.worldweatherapp.weather_data.WeatherData
import com.webprog26.worldweatherapp.weather_data.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class WeatherDataViewModel : ViewModel() {

    private val _cityData = MutableLiveData<City>()
    val cityData: LiveData<City>
        get() = _cityData

    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData>
        get() = _weatherData


    fun updateWeatherData(
        latLng: LocationProvider.LatLng,
        appId: String
    ) {
        val weatherRepository =
            WeatherRepository(createWeatherApi(), WorldWeatherApplication.appContext)
        val weatherDataDeferred = viewModelScope.async {
            weatherRepository.updateWeatherData(_weatherData, latLng, appId)
        }

        val cityDataDeferred = viewModelScope.async {
            weatherRepository.updateCityData(_cityData, latLng, appId) { city ->
                viewModelScope.launch {
                    weatherRepository.saveCityData(city)
                }
            }
        }
        viewModelScope.launch {
            awaitAll(weatherDataDeferred, cityDataDeferred)
        }
    }
}