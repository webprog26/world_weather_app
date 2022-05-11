package com.webprog26.worldweatherapp.weather_data

import android.text.TextUtils
import com.webprog26.worldweatherapp.db.WeatherDao
import com.webprog26.worldweatherapp.location.LocationProvider
import com.webprog26.worldweatherapp.network.WeatherApi
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

interface WeatherRepository {

    suspend fun updateWeatherData(
        latLng: LocationProvider.LatLng= LocationProvider.LatLng(
            0.0,
            0.0
        ),
        apiKey: String = "",
        forceNetworkUpdate: Boolean = false
    ): WeatherData

    suspend fun updateCityData(
        latLng: LocationProvider.LatLng = LocationProvider.LatLng(
            0.0,
            0.0
        ),
        apiKey: String = ""
    ): City?
}

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    override suspend fun updateWeatherData(
        latLng: LocationProvider.LatLng,
        apiKey: String,
        forceNetworkUpdate: Boolean
    ): WeatherData {
        val cachedWeatherData = dao.getWeatherData()

        return if (cachedWeatherData == null || forceNetworkUpdate) {
            val weatherDataFromNetwork = weatherApi.getWeatherData(
                latLng.latitude, latLng.longitude, "minutely", "metric",
                apiKey
            )
            dao.insert(weatherDataFromNetwork)
            weatherDataFromNetwork
        } else {
            cachedWeatherData
        }

    }

    override suspend fun updateCityData(
        latLng: LocationProvider.LatLng,
        apiKey: String,

        ): City? {
        var city: City? = null

        val cachedCities = dao.getCities();
        cachedCities.let {
            if (it.isNotEmpty()) {
                city = cachedCities[0]

            }
        }

        weatherApi.getCityData(latLng.latitude, latLng.longitude, "metric", apiKey)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        val jsonString = response.body()
                        if (TextUtils.isEmpty(jsonString).not()) {
                            val jsonObject = JSONObject(jsonString!!);
                            val cityName = jsonObject.getString("name")
                            city = City(0, cityName)
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })

        if (city != null) {
            dao.insert(city as City)
        }

        return city
    }
}