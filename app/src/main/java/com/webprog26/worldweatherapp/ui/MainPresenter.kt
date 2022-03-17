package com.webprog26.worldweatherapp.ui

import com.webprog26.worldweatherapp.weather_data.City
import com.webprog26.worldweatherapp.weather_data.WeatherData

class MainPresenter(private val mainView: MainView) {

    fun onCurrentCityAvailable(city: City) {
        mainView.onCurrentCityAvailable(city)
    }

    fun onWeatherDataAvailable(weatherData: WeatherData) {
        mainView.onWeatherDataAvailable(weatherData)
    }

    fun onLoadingStarted() {
        mainView.onLoadingStarted()
    }

    fun onLoadingCompleted() {
        mainView.onLoadingCompleted()
    }
}