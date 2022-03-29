package com.webprog26.worldweatherapp.weather_data

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.webprog26.worldweatherapp.db.WeatherDatabase
import com.webprog26.worldweatherapp.location.LocationProvider
import com.webprog26.worldweatherapp.network.WeatherApi
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class WeatherRepository(private val weatherApi: WeatherApi, context: Context) {

    private val database = WeatherDatabase.getInstance(context).weatherDao();

    suspend fun updateWeatherData(
        weatherData: MutableLiveData<WeatherData>,
        latLng: LocationProvider.LatLng,
        apiKey: String,
        forceNetworkUpdate: Boolean
    ) {
        val cachedWeatherData = database.getWeatherData()

        if (cachedWeatherData == null || forceNetworkUpdate) {
            val weatherDataFromNetwork = weatherApi.getWeatherData(
                latLng.latitude, latLng.longitude, "minutely", "metric",
                apiKey
            )

            weatherData.value = weatherDataFromNetwork

            database.insert(weatherDataFromNetwork)
        } else {
            weatherData.value = cachedWeatherData

        }

    }

    suspend fun updateCityData(
        cityData: MutableLiveData<City>, latLng: LocationProvider.LatLng,
        apiKey: String,
        onCityLoaded: (City) -> Unit
    ) {

        var city: City?

        val cachedCities = database.getCities();
        if (cachedCities.isEmpty().not()) {
            cityData.value = cachedCities[0]
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
                            cityData.value = city
                            onCityLoaded.invoke(city as City)
                        }
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })
    }

    suspend fun saveCityData(city: City) {
        database.insert(city)
    }
}