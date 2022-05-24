package com.webprog26.worldweatherapp.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.webprog26.worldweatherapp.ONE_CALL_WEATHER_DATA
import com.webprog26.worldweatherapp.ONE_CALL_WEATHER_JSON_RAW
import com.webprog26.worldweatherapp.getWeatherJSONRaw
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class WeatherApiTest {

    @get:Rule
    val mockWebServer = MockWebServer()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val weatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }

    @Test
    fun `getWeatherData loads WeatherData`() {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(ONE_CALL_WEATHER_JSON_RAW)
                .setResponseCode(HttpURLConnection.HTTP_OK)
        )
        runBlocking {
            val weatherData = weatherApi.getWeatherData(
                0.0, 0.0,
                "", "", ""
            )
            Assert.assertNotNull(weatherData)
            Assert.assertEquals(ONE_CALL_WEATHER_DATA, weatherData)
        }
    }

    @Test
    fun `getCityData loads JSON data including city name`() {
        val expectedCityName = "Kropyvnytskyi"
        mockWebServer.enqueue(
            MockResponse()
                .setBody(getWeatherJSONRaw(expectedCityName))
                .setResponseCode(HttpURLConnection.HTTP_OK)
        )

        runBlocking {
            var actualCityName: String?

            val response = weatherApi.getCityData(0.0, 0.0, "", "").execute()
            val responseBody = response.body() as String
            val jsonObject = JSONObject(responseBody)
            actualCityName = jsonObject.getString("name")

            Assert.assertNotNull(response)
            Assert.assertEquals(HttpURLConnection.HTTP_OK, response.code())
            Assert.assertNotNull(responseBody)
            Assert.assertNotNull(actualCityName)
            Assert.assertEquals(expectedCityName, actualCityName)
        }
    }
}