package com.webprog26.worldweatherapp.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
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

        val weatherData = WeatherData(
            id = 0,
            lat = 37.4234,
            lon = -122.084,
            timezone = "America/Los_Angeles",
            timezoneOffset = -25200,
            current = Current(
                dt = 1652198659,
                sunrise = 1652187822,
                sunset = 1652238350,
                temp = 11.29,
                feelsLike = 10.15,
                pressure = 1021,
                humidity = 64,
                dewPoint = 4.74,
                uvi = 1.67,
                clouds = 20,
                visibility = 10000,
                windSpeed = 5.14,
                windDeg = 290,
                windGust = 0.0,
                weather = listOf(
                    Weather(
                        id = 801,
                        main = "Clouds",
                        description = "few clouds",
                        icon = "02d"
                    )
                )
            ),
            hourly = listOf(
                Hourly(
                    dt = 1652198400,
                    temp = 11.29,
                    feelsLike = 10.15,
                    pressure = 1021,
                    humidity = 64,
                    dewPoint = 4.74,
                    uvi = 1.67,
                    clouds = 20,
                    visibility = 10000,
                    windSpeed = 1.87,
                    windDeg = 297,
                    windGust = 2.65,
                    weather = listOf(
                        Weather(
                            id = 801,
                            main = "Clouds",
                            description = "few clouds",
                            icon = "02d"
                        )
                    ), pop = 0.11
                )
            ),
            listOf(
                Daily(
                    dt = 1652212800,
                    sunrise = 1652187822,
                    sunset = 1652238350,
                    moonrise = 1652217480,
                    moonset = 1652177880,
                    moonPhase = 0.31,
                    temp = Temp(
                        day = 14.08,
                        min = 6.96,
                        max = 15.21,
                        night = 8.39,
                        eve = 11.46,
                        morn = 9.86
                    ),
                    feelsLike = FeelsLike(
                        day = 12.61,
                        night = 7.11,
                        eve = 10.12,
                        morn = 9.86
                    ),
                    pressure = 1022,
                    humidity = 41,
                    dewPoint = 1.04,
                    windSpeed = 6.2,
                    windDeg = 292.0,
                    windGust = 8.09,
                    weather = listOf(
                        Weather(
                            id = 500,
                            main = "Rain",
                            description = "light rain",
                            icon = "10d"
                        )
                    ),
                    clouds = 22,
                    pop = 0.4,
                    uvi = 6.49
                )
            )
        )

        viewModel.weatherData.observeForever(weatherDataObserver)

        runBlocking {
            whenever(repository.updateWeatherData())
                .thenReturn(weatherData)
        }

        viewModel.updateWeatherData()

        verify(weatherDataObserver).onChanged(eq(weatherData))
    }
}
