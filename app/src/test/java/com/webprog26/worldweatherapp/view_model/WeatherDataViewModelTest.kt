package com.webprog26.worldweatherapp.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.webprog26.worldweatherapp.ONE_CALL_WEATHER_DATA
import com.webprog26.worldweatherapp.weather_data.*
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.*

@RunWith(AndroidJUnit4::class)
class WeatherDataViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `updateWeatherData updates city data`() {
        val repository: WeatherRepository = mock()
        val viewModel = WeatherDataViewModel(repository)
        val cityDataObserver: Observer<City> = mock()

        viewModel.cityData.observeForever(cityDataObserver)

        val city = City(0, "Toronto")

        runBlocking {
            whenever(repository.updateCityData()).thenReturn(city)
        }

        viewModel.updateWeatherData()

        verify(cityDataObserver).onChanged(eq(city))
    }

    @Test
    fun `updateWeatherData updates weather data`() {
        val repository: WeatherRepository = mock()
        val viewModel = WeatherDataViewModel(repository)
        val weatherDataObserver: Observer<WeatherData> = mock()

        viewModel.weatherData.observeForever(weatherDataObserver)

        runBlocking {
            whenever(repository.updateWeatherData())
                .thenReturn(ONE_CALL_WEATHER_DATA)
        }

        viewModel.updateWeatherData()

        verify(weatherDataObserver).onChanged(eq(ONE_CALL_WEATHER_DATA))
    }
}
