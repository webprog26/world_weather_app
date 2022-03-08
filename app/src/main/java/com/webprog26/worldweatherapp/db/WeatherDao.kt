package com.webprog26.worldweatherapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.webprog26.worldweatherapp.weather_data.City
import com.webprog26.worldweatherapp.weather_data.WeatherData

@Dao
interface WeatherDao {

    @Query("SELECT * FROM cities")
    suspend fun getCities(): List<City>

    @Insert(entity = City::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: City)

    @Query("SELECT * FROM weather_data")
    suspend fun getWeatherData(): WeatherData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherData: WeatherData)

    @Query("DELETE FROM weather_data")
    suspend fun clear()
}