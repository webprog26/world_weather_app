package com.webprog26.worldweatherapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.webprog26.worldweatherapp.weather_data.City
import com.webprog26.worldweatherapp.weather_data.WeatherData

@Database(entities = [WeatherData::class, City::class], version = 1, exportSchema = false)
@TypeConverters(
    CurrentConverter::class,
    HourlyConverter::class, DailyConverter::class,
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        private const val DB_MANE = "weather_data.db"

        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            if (INSTANCE == null) {
                synchronized(WeatherDatabase::class) {
                    INSTANCE = buildRoomDb(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildRoomDb(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java,
                DB_MANE
            ).build()

    }
}