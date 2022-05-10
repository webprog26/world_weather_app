package com.webprog26.worldweatherapp.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.webprog26.worldweatherapp.R
import java.text.SimpleDateFormat
import java.util.*


const val INCORRECT_ICON_CODE = -1
const val INCORRECT_WIND_DIRECTION_CODE = -1

fun convertMillisToHhMm(seconds: Long): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return simpleDateFormat.format(Date(seconds * 1000).time)
}

fun convertMillisToDateString(dateTime: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = (dateTime * 1000)
    val simpleDateFormat = SimpleDateFormat("EEE, MMM dd", Locale.getDefault())
    return simpleDateFormat.format(Date(dateTime * 1000).time)
}

@DrawableRes
fun getWeatherIconRes(iconCode: String): Int {
    return when (iconCode) {
        "01d" -> {
            return R.drawable.weather_clear
        }
        "02d" -> {
            return R.drawable.weather_clouds
        }
        "03d" -> {
            return R.drawable.weather_cloud
        }
        "04d" -> {
            return R.drawable.weather_few_clouds
        }
        "09d" -> {
            return R.drawable.weather_rain_heavy
        }
        "10d" -> {
            return R.drawable.weather_rain_light
        }
        "11d" -> {
            return R.drawable.weather_thunder_shower
        }
        "13d" -> {
            return R.drawable.weather_snow
        }
        "50d" -> {
            return R.drawable.weather_clouds
        }
        "01n" -> {
            return R.drawable.weather_clear_night
        }
        "02n" -> {
            return R.drawable.weather_clouds_night
        }
        "03n" -> {
            return R.drawable.weather_few_clouds_night
        }
        "04n" -> {
            return R.drawable.weather_few_clouds_night
        }
        "09n" -> {
            return R.drawable.weather_rain_heavy
        }
        "10n" -> {
            return R.drawable.weather_rain_light
        }
        "11n" -> {
            return R.drawable.weather_thunder_shower
        }
        "13n" -> {
            return R.drawable.weather_snow
        }
        "50n" -> {
            return R.drawable.weather_clouds
        }
        else -> {
            INCORRECT_ICON_CODE
        }
    }
}

@StringRes
fun getWindDirection(code: Int) : Int {
    val index = ((code/22.5)+.5).toInt()
    val windDirectionArray = arrayOf(
    R.string.wind_direction_north,
    R.string.wind_direction_north,
    R.string.wind_direction_north_east,
    R.string.wind_direction_east,
    R.string.wind_direction_east,
    R.string.wind_direction_east,
    R.string.wind_direction_south_east,
    R.string.wind_direction_south,
    R.string.wind_direction_south,
    R.string.wind_direction_south,
    R.string.wind_direction_south_west,
    R.string.wind_direction_west,
    R.string.wind_direction_west,
    R.string.wind_direction_west,
    R.string.wind_direction_north_west,
    R.string.wind_direction_north)
    return windDirectionArray[(index % windDirectionArray.size)]
}
