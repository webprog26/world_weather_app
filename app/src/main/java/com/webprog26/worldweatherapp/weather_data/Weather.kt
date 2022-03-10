package com.webprog26.worldweatherapp.weather_data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather_data")

data class WeatherData(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "lat")
    @SerializedName("lat") var lat: Double,

    @ColumnInfo(name = "lon")
    @SerializedName("lon") var lon: Double,

    @ColumnInfo(name = "timezone")
    @SerializedName("timezone") var timezone: String,

    @ColumnInfo(name = "timezone_offset")
    @SerializedName("timezone_offset") var timezoneOffset: Int,

    @ColumnInfo(name = "current")
    @SerializedName("current") var current: Current,

    @ColumnInfo(name = "hourly")
    @SerializedName("hourly") var hourly: List<Hourly>,

    @ColumnInfo(name = "daily")
    @SerializedName("daily") var daily: List<Daily>

)

data class Weather(

    @SerializedName("id") var id: Int,
    @SerializedName("main") var main: String,
    @SerializedName("description") var description: String,
    @SerializedName("icon") var icon: String

)

data class Current(

    @SerializedName("dt") var dt: Long,
    @SerializedName("sunrise") var sunrise: Int,
    @SerializedName("sunset") var sunset: Int,
    @SerializedName("temp") var temp: Double,
    @SerializedName("feels_like") var feelsLike: Double,
    @SerializedName("pressure") var pressure: Int,
    @SerializedName("humidity") var humidity: Int,
    @SerializedName("dew_point") var dewPoint: Double,
    @SerializedName("uvi") var uvi: Double,
    @SerializedName("clouds") var clouds: Int,
    @SerializedName("visibility") var visibility: Int,
    @SerializedName("wind_speed") var windSpeed: Double,
    @SerializedName("wind_deg") var windDeg: Int,
    @SerializedName("wind_gust") var windGust: Double,
    @SerializedName("weather") var weather: List<Weather>

)

data class Hourly(

    @SerializedName("dt") var dt: Long,
    @SerializedName("temp") var temp: Double,
    @SerializedName("feels_like") var feelsLike: Double,
    @SerializedName("pressure") var pressure: Int,
    @SerializedName("humidity") var humidity: Int,
    @SerializedName("dew_point") var dewPoint: Double,
    @SerializedName("uvi") var uvi: Double,
    @SerializedName("clouds") var clouds: Int,
    @SerializedName("visibility") var visibility: Int,
    @SerializedName("wind_speed") var windSpeed: Double,
    @SerializedName("wind_deg") var windDeg: Int,
    @SerializedName("wind_gust") var windGust: Double,
    @SerializedName("weather") var weather: List<Weather>,
    @SerializedName("pop") var pop: Double

)

data class Temp(

    @SerializedName("day") var day: Double,
    @SerializedName("min") var min: Double,
    @SerializedName("max") var max: Double,
    @SerializedName("night") var night: Double,
    @SerializedName("eve") var eve: Double,
    @SerializedName("morn") var morn: Double

)

data class FeelsLike(

    @SerializedName("day") var day: Double,
    @SerializedName("night") var night: Double,
    @SerializedName("eve") var eve: Double,
    @SerializedName("morn") var morn: Double

)

data class Daily(

    @SerializedName("dt") var dt: Long,
    @SerializedName("sunrise") var sunrise: Long,
    @SerializedName("sunset") var sunset: Long,
    @SerializedName("moonrise") var moonrise: Long,
    @SerializedName("moonset") var moonset: Long,
    @SerializedName("moon_phase") var moonPhase: Double,
    @SerializedName("temp") var temp: Temp,
    @SerializedName("feels_like") var feelsLike: FeelsLike,
    @SerializedName("pressure") var pressure: Int,
    @SerializedName("humidity") var humidity: Int,
    @SerializedName("dew_point") var dewPoint: Double,
    @SerializedName("wind_speed") var windSpeed: Double,
    @SerializedName("wind_deg") var windDeg: Double,
    @SerializedName("wind_gust") var windGust: Double,
    @SerializedName("weather") var weather: List<Weather>,
    @SerializedName("clouds") var clouds: Int,
    @SerializedName("pop") var pop: Double,
    @SerializedName("uvi") var uvi: Double

)