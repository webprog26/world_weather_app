package com.webprog26.worldweatherapp.network

import com.webprog26.worldweatherapp.weather_data.WeatherData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    companion object {
        private const val LATITUDE = "lat"
        private const val LONGITUDE = "lon"
        private const val UNITS = "units"
        private const val APP_ID = "appid"
        private const val EXCLUDE = "exclude"
    }

    @GET("onecall")
    suspend fun getWeatherData(
        @Query(LATITUDE) lat: Double,
        @Query(LONGITUDE) lon: Double,
        @Query(EXCLUDE) exclude: String,
        @Query(UNITS) units: String,
        @Query(APP_ID) appId: String
    ): WeatherData

    @GET("weather")
    fun getCityData(
        @Query(LATITUDE) lat: Double,
        @Query(LONGITUDE) lon: Double,
        @Query(UNITS) units: String,
        @Query(APP_ID) appId: String
    ): Call<String>

}

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

fun createWeatherApi(): WeatherApi {

    val interceptor = HttpLoggingInterceptor()
    interceptor.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(WeatherApi::class.java)
}