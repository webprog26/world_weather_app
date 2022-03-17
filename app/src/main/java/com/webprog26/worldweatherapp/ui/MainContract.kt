package com.webprog26.worldweatherapp.ui

import com.webprog26.worldweatherapp.weather_data.City
import com.webprog26.worldweatherapp.weather_data.WeatherData

interface MainView {

    fun onLoadingStarted()

    fun onLoadingCompleted()

    fun onCurrentCityAvailable(city: City)

    fun onWeatherDataAvailable(weatherData: WeatherData)
}