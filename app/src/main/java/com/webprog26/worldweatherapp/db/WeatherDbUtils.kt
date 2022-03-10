package com.webprog26.worldweatherapp.db

import android.text.TextUtils
import androidx.room.TypeConverter
import com.webprog26.worldweatherapp.weather_data.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

private const val KEY_ID = "id"
private const val KEY_MAIN = "main"
private const val KEY_DESCRIPTION = "description"
private const val KEY_ICON = "icon"
private const val KEY_DT = "dt"
private const val KEY_SUNRISE = "sunrise"
private const val KEY_SUNSET = "sunset"
private const val KEY_TEMP = "temp"
private const val KEY_FEELS_LIKE = "feels_like"
private const val KEY_PRESSURE = "pressure"
private const val KEY_HUMIDITY = "humidity"
private const val KEY_DEW_POINT = "dew_point"
private const val KEY_UVI = "uvi"
private const val KEY_CLOUDS = "clouds"
private const val KEY_VISIBILITY = "visibility"
private const val KEY_WIND_SPEED = "wind_speed"
private const val KEY_WIND_DEG = "wind_deg"
private const val KEY_WIND_GUST = "wind_gust"
private const val KEY_WEATHER = "weather"
private const val KEY_POP = "pop"
private const val KEY_DAY = "day"
private const val KEY_MIN = "min"
private const val KEY_MAX = "max"
private const val KEY_NIGHT = "night"
private const val KEY_EVE = "eve"
private const val KEY_MORN = "morn"
private const val KEY_MOONRISE = "moonrise"
private const val KEY_MOONSET = "moonset"
private const val KEY_MOON_PHASE = "moon_phase"

private fun weatherFromJSONString(jsonString: String?): Weather? {
    if (TextUtils.isEmpty(jsonString).not()) {
        try {
            val jsonObject = JSONObject(jsonString!!)
            return Weather(
                jsonObject.getInt(KEY_ID),
                jsonObject.getString(KEY_MAIN),
                jsonObject.getString(KEY_DESCRIPTION),
                jsonObject.getString(KEY_ICON)
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    return null
}

private fun weatherListFromJSONString(jsonString: String): List<Weather> {
    val weatherList = mutableListOf<Weather>()
    try {
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            weatherList.add(weatherFromJSONString(jsonArray.get(i).toString()) as Weather)
        }
        return weatherList

    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return listOf()
}

private fun weatherListToJSONString(weatherList: List<Weather>): String {
    try {
        val jsonArray = JSONArray()
        weatherList.forEach { weather ->
            jsonArray.put(weatherToJSONObject(weather))
        }
        return jsonArray.toString()
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return ""
}

private fun weatherToJSONObject(weather: Weather): JSONObject? {
    try {
        val jsonObject = JSONObject()
        jsonObject.put(KEY_ID, weather.id)
        jsonObject.put(KEY_MAIN, weather.main)
        jsonObject.put(KEY_DESCRIPTION, weather.description)
        jsonObject.put(KEY_ICON, weather.icon)
        return jsonObject
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return null
}

private fun hourlyFromJSONString(jsonString: String): Hourly? {
    if (TextUtils.isEmpty(jsonString).not()) {
        try {
            val jsonObject = JSONObject(jsonString)
            return Hourly(
                jsonObject.getLong(KEY_DT),
                jsonObject.getDouble(KEY_TEMP),
                jsonObject.getDouble(KEY_FEELS_LIKE),
                jsonObject.getInt(KEY_PRESSURE),
                jsonObject.getInt(KEY_HUMIDITY),
                jsonObject.getDouble(KEY_DEW_POINT),
                jsonObject.getDouble(KEY_UVI),
                jsonObject.getInt(KEY_CLOUDS),
                jsonObject.getInt(KEY_VISIBILITY),
                jsonObject.getDouble(KEY_WIND_SPEED),
                jsonObject.getInt(KEY_WIND_DEG),
                jsonObject.getDouble(KEY_WIND_GUST),
                weatherListFromJSONString(jsonObject.getString(KEY_WEATHER)),
                jsonObject.getDouble(KEY_POP)
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    return null
}

private fun hourlyToJSONObject(hourly: Hourly): JSONObject? {
    try {
        val jsonObject = JSONObject()
        jsonObject.put(KEY_DT, hourly.dt)
        jsonObject.put(KEY_TEMP, hourly.temp)
        jsonObject.put(KEY_FEELS_LIKE, hourly.feelsLike)
        jsonObject.put(KEY_PRESSURE, hourly.pressure)
        jsonObject.put(KEY_HUMIDITY, hourly.humidity)
        jsonObject.put(KEY_DEW_POINT, hourly.dewPoint)
        jsonObject.put(KEY_UVI, hourly.uvi)
        jsonObject.put(KEY_CLOUDS, hourly.clouds)
        jsonObject.put(KEY_VISIBILITY, hourly.visibility)
        jsonObject.put(KEY_WIND_SPEED, hourly.windSpeed)
        jsonObject.put(KEY_WIND_DEG, hourly.windDeg)
        jsonObject.put(KEY_WIND_GUST, hourly.windGust)
        jsonObject.put(KEY_WEATHER, weatherListToJSONString(hourly.weather))
        jsonObject.put(KEY_POP, hourly.pop)

        return jsonObject
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    return null
}

private fun tempToJSONOString(temp: Temp): String {
    try {
        val jsonObject = JSONObject()
        jsonObject.put(KEY_DAY, temp.day)
        jsonObject.put(KEY_MIN, temp.min)
        jsonObject.put(KEY_MAX, temp.max)
        jsonObject.put(KEY_NIGHT, temp.night)
        jsonObject.put(KEY_EVE, temp.eve)
        jsonObject.put(KEY_MORN, temp.morn)

        return jsonObject.toString()
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return ""
}

private fun tempFromJSONString(jsonString: String): Temp? {
    if (TextUtils.isEmpty(jsonString).not()) {
        try {
            val jsonObject = JSONObject(jsonString)
            return Temp(
                jsonObject.getDouble(KEY_DAY),
                jsonObject.getDouble(KEY_MIN),
                jsonObject.getDouble(KEY_MAX),
                jsonObject.getDouble(KEY_NIGHT),
                jsonObject.getDouble(KEY_EVE),
                jsonObject.getDouble(KEY_MORN)
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    return null
}

private fun feelsLikeToJSONString(feelsLike: FeelsLike): String {
    try {
        val jsonObject = JSONObject()
        jsonObject.put(KEY_DAY, feelsLike.day)
        jsonObject.put(KEY_NIGHT, feelsLike.night)
        jsonObject.put(KEY_EVE, feelsLike.eve)
        jsonObject.put(KEY_MORN, feelsLike.morn)

        return jsonObject.toString()
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return ""
}

private fun feelsLikeFromJSONString(jsonString: String): FeelsLike? {
    if (TextUtils.isEmpty(jsonString).not()) {
        try {
            val jsonObject = JSONObject(jsonString)
            return FeelsLike(
                jsonObject.getDouble(KEY_DAY),
                jsonObject.getDouble(KEY_NIGHT),
                jsonObject.getDouble(KEY_EVE),
                jsonObject.getDouble(KEY_MORN)
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    return null
}


private fun dailyToJSONObject(daily: Daily): JSONObject? {
    try {
        val jsonObject = JSONObject()
        jsonObject.put(KEY_DT, daily.dt)
        jsonObject.put(KEY_SUNRISE, daily.sunrise)
        jsonObject.put(KEY_SUNSET, daily.sunset)
        jsonObject.put(KEY_MOONRISE, daily.moonrise)
        jsonObject.put(KEY_MOONSET, daily.moonset)
        jsonObject.put(KEY_MOON_PHASE, daily.moonPhase)
        jsonObject.put(KEY_TEMP, tempToJSONOString(daily.temp))
        jsonObject.put(KEY_FEELS_LIKE, feelsLikeToJSONString(daily.feelsLike))
        jsonObject.put(KEY_PRESSURE, daily.pressure)
        jsonObject.put(KEY_HUMIDITY, daily.humidity)
        jsonObject.put(KEY_DEW_POINT, daily.dewPoint)
        jsonObject.put(KEY_WIND_SPEED, daily.windSpeed)
        jsonObject.put(KEY_WIND_DEG, daily.windDeg)
        jsonObject.put(KEY_WIND_GUST, daily.windGust)
        jsonObject.put(KEY_WEATHER, weatherListToJSONString(daily.weather))
        jsonObject.put(KEY_CLOUDS, daily.clouds)
        jsonObject.put(KEY_POP, daily.pop)
        jsonObject.put(KEY_UVI, daily.uvi)

        return jsonObject
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return null
}

private fun dailyFromJSONString(jsonString: String): Daily? {
    if (TextUtils.isEmpty(jsonString).not()) {
        try {
            val jsonObject = JSONObject(jsonString)
            return Daily(
                jsonObject.getLong(KEY_DT),
                jsonObject.getLong(KEY_SUNRISE),
                jsonObject.getLong(KEY_SUNSET),
                jsonObject.getLong(KEY_MOONRISE),
                jsonObject.getLong(KEY_MOONSET),
                jsonObject.getDouble(KEY_MOON_PHASE),
                tempFromJSONString(jsonObject.getString(KEY_TEMP)) as Temp,
                feelsLikeFromJSONString(jsonObject.getString(KEY_FEELS_LIKE)) as FeelsLike,
                jsonObject.getInt(KEY_PRESSURE),
                jsonObject.getInt(KEY_HUMIDITY),
                jsonObject.getDouble(KEY_DEW_POINT),
                jsonObject.getDouble(KEY_WIND_SPEED),
                jsonObject.getDouble(KEY_WIND_DEG),
                jsonObject.getDouble(KEY_WIND_GUST),
                weatherListFromJSONString(jsonObject.getString(KEY_WEATHER)),
                jsonObject.getInt(KEY_CLOUDS),
                jsonObject.getDouble(KEY_POP),
                jsonObject.getDouble(KEY_UVI)
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    return null
}

class CurrentConverter {

    @TypeConverter
    fun currentToJsonString(current: Current): String {
        try {
            val jsonObject = JSONObject()
            jsonObject.put(KEY_DT, current.dt)
            jsonObject.put(KEY_SUNRISE, current.sunrise)
            jsonObject.put(KEY_SUNSET, current.sunset)
            jsonObject.put(KEY_TEMP, current.temp)
            jsonObject.put(KEY_FEELS_LIKE, current.feelsLike)
            jsonObject.put(KEY_PRESSURE, current.pressure)
            jsonObject.put(KEY_HUMIDITY, current.humidity)
            jsonObject.put(KEY_DEW_POINT, current.dewPoint)
            jsonObject.put(KEY_UVI, current.uvi)
            jsonObject.put(KEY_CLOUDS, current.clouds)
            jsonObject.put(KEY_VISIBILITY, current.visibility)
            jsonObject.put(KEY_WIND_SPEED, current.windSpeed)
            jsonObject.put(KEY_WIND_DEG, current.windDeg)
            jsonObject.put(KEY_WIND_GUST, current.windGust)
            jsonObject.put(KEY_WEATHER, weatherListToJSONString(current.weather))

            return jsonObject.toString()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return ""
    }

    @TypeConverter
    fun currentFromJSONString(jsonString: String?): Current? {
        if (TextUtils.isEmpty(jsonString).not()) {
            try {
                val jsonObject = JSONObject(jsonString!!)
                return Current(
                    jsonObject.getLong(KEY_DT),
                    jsonObject.getInt(KEY_SUNRISE),
                    jsonObject.getInt(KEY_SUNSET),
                    jsonObject.getDouble(KEY_TEMP),
                    jsonObject.getDouble(KEY_FEELS_LIKE),
                    jsonObject.getInt(KEY_PRESSURE),
                    jsonObject.getInt(KEY_HUMIDITY),
                    jsonObject.getDouble(KEY_DEW_POINT),
                    jsonObject.getDouble(KEY_UVI),
                    jsonObject.getInt(KEY_CLOUDS),
                    jsonObject.getInt(KEY_VISIBILITY),
                    jsonObject.getDouble(KEY_WIND_SPEED),
                    jsonObject.getInt(KEY_WIND_DEG),
                    jsonObject.getDouble(KEY_WIND_GUST),
                    weatherListFromJSONString(jsonObject.getString(KEY_WEATHER))
                )
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return null
    }
}

class HourlyConverter {

    @TypeConverter
    fun hourlyListToJSONString(hourlyList: List<Hourly>): String {
        try {
            val jsonArray = JSONArray()
            hourlyList.forEach { hourly ->
                jsonArray.put(hourlyToJSONObject(hourly))
            }
            return jsonArray.toString()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return ""
    }

    @TypeConverter
    fun hourlyListFromJSONString(jsonString: String): List<Hourly> {
        val hourlyList = mutableListOf<Hourly>()
        try {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                hourlyList.add(hourlyFromJSONString(jsonArray.get(i).toString()) as Hourly)
            }
            return hourlyList

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return listOf()
    }
}

class DailyConverter {

    @TypeConverter
    fun dailyListToJSONString(dailyList: List<Daily>): String {
        try {
            val jsonArray = JSONArray()
            dailyList.forEach { daily ->
                jsonArray.put(dailyToJSONObject(daily))
            }
            return jsonArray.toString()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return ""
    }

    @TypeConverter
    fun dailyListFromJSONString(jsonString: String): List<Daily> {
        val dailyList = mutableListOf<Daily>()
        try {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                dailyList.add(dailyFromJSONString(jsonArray.get(i).toString()) as Daily)
            }
            return dailyList

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return listOf()
    }
}