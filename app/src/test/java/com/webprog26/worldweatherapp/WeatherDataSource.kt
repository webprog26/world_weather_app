package com.webprog26.worldweatherapp

import com.webprog26.worldweatherapp.weather_data.*

const val ONE_CALL_WEATHER_JSON_RAW = """{
  "lat": 48.45,
  "lon": 32.21,
  "timezone": "Europe/Kiev",
  "timezone_offset": 10800,
  "current": {
    "dt": 1653314407,
    "sunrise": 1653271320,
    "sunset": 1653327245,
    "temp": 15.58,
    "feels_like": 14.66,
    "pressure": 1008,
    "humidity": 56,
    "dew_point": 6.85,
    "uvi": 1.44,
    "clouds": 100,
    "visibility": 10000,
    "wind_speed": 3.9,
    "wind_deg": 3,
    "wind_gust": 4.42,
    "weather": [
      {
        "id": 500,
        "main": "Rain",
        "description": "light rain",
        "icon": "10d"
      }
    ],
    "rain": {
      "1h": 0.24
    }
  },
  "hourly": [
    {
      "dt": 1653314400,
      "temp": 15.58,
      "feels_like": 14.66,
      "pressure": 1008,
      "humidity": 56,
      "dew_point": 6.85,
      "uvi": 1.44,
      "clouds": 100,
      "visibility": 10000,
      "wind_speed": 3.9,
      "wind_deg": 3,
      "wind_gust": 4.42,
      "weather": [
        {
          "id": 500,
          "main": "Rain",
          "description": "light rain",
          "icon": "10d"
        }
      ],
      "pop": 0.55,
      "rain": {
        "1h": 0.24
      }
    },
    {
      "dt": 1653318000,
      "temp": 15.5,
      "feels_like": 14.59,
      "pressure": 1008,
      "humidity": 57,
      "dew_point": 7.03,
      "uvi": 0.7,
      "clouds": 99,
      "visibility": 10000,
      "wind_speed": 4.68,
      "wind_deg": 10,
      "wind_gust": 5.16,
      "weather": [
        {
          "id": 500,
          "main": "Rain",
          "description": "light rain",
          "icon": "10d"
        }
      ],
      "pop": 0.59,
      "rain": {
        "1h": 0.18
      }
    }
  ],
  "daily": [
    {
      "dt": 1653296400,
      "sunrise": 1653271320,
      "sunset": 1653327245,
      "moonrise": 1653262260,
      "moonset": 1653298980,
      "moon_phase": 0.77,
      "temp": {
        "day": 11.63,
        "min": 8.93,
        "max": 15.61,
        "night": 9.06,
        "eve": 15.5,
        "morn": 9.34
      },
      "feels_like": {
        "day": 10.89,
        "night": 7.97,
        "eve": 14.59,
        "morn": 7.42
      },
      "pressure": 1008,
      "humidity": 78,
      "dew_point": 7.94,
      "wind_speed": 6.31,
      "wind_deg": 320,
      "wind_gust": 9.01,
      "weather": [
        {
          "id": 500,
          "main": "Rain",
          "description": "light rain",
          "icon": "10d"
        }
      ],
      "clouds": 100,
      "pop": 1,
      "rain": 2.44,
      "uvi": 5.04
    },
    {
      "dt": 1653382800,
      "sunrise": 1653357660,
      "sunset": 1653413717,
      "moonrise": 1653349860,
      "moonset": 1653389940,
      "moon_phase": 0.8,
      "temp": {
        "day": 17.41,
        "min": 7.28,
        "max": 17.94,
        "night": 9.6,
        "eve": 16.84,
        "morn": 8.96
      },
      "feels_like": {
        "day": 16.36,
        "night": 8.63,
        "eve": 15.73,
        "morn": 8.96
      },
      "pressure": 1013,
      "humidity": 44,
      "dew_point": 5.14,
      "wind_speed": 5.76,
      "wind_deg": 353,
      "wind_gust": 6.22,
      "weather": [
        {
          "id": 500,
          "main": "Rain",
          "description": "light rain",
          "icon": "10d"
        }
      ],
      "clouds": 20,
      "pop": 0.38,
      "rain": 0.1,
      "uvi": 5.41
    }
  ]
}"""

fun getWeatherJSONRaw(cityName: String) = """{
  "coord": {
    "lon": 32.21,
    "lat": 48.45
  },
  "weather": [
    {
      "id": 500,
      "main": "Rain",
      "description": "light rain",
      "icon": "10d"
    }
  ],
  "base": "stations",
  "main": {
    "temp": 15.2,
    "feels_like": 14.29,
    "temp_min": 15.2,
    "temp_max": 15.2,
    "pressure": 1008,
    "humidity": 58,
    "sea_level": 1008,
    "grnd_level": 989
  },
  "visibility": 10000,
  "wind": {
    "speed": 4.68,
    "deg": 10,
    "gust": 5.16
  },
  "rain": {
    "1h": 0.19
  },
  "clouds": {
    "all": 96
  },
  "dt": 1653317785,
  "sys": {
    "country": "UA",
    "sunrise": 1653271320,
    "sunset": 1653327245
  },
  "timezone": 10800,
  "id": 705812,
  "name": $cityName,
  "cod": 200
}"""

val ONE_CALL_WEATHER_DATA: WeatherData = WeatherData(
    id = 0,
    lat = 48.45,
    lon = 32.21,
    timezone = "Europe/Kiev",
    timezoneOffset = 10800,
    current = Current(
        dt = 1653314407,
        sunrise = 1653271320,
        sunset = 1653327245,
        temp = 15.58,
        feelsLike = 14.66,
        pressure = 1008,
        humidity = 56,
        dewPoint = 6.85,
        uvi = 1.44,
        clouds = 100,
        visibility = 10000,
        windSpeed = 3.9,
        windDeg = 3,
        windGust = 4.42,
        weather = listOf(
            Weather(
                id = 500,
                main = "Rain",
                description = "light rain",
                icon = "10d"
            )
        )
    ),
    hourly = listOf(
        Hourly(
            dt = 1653314400,
            temp = 15.58,
            feelsLike = 14.66,
            pressure = 1008,
            humidity = 56,
            dewPoint = 6.85,
            uvi = 1.44,
            clouds = 100,
            visibility = 10000,
            windSpeed = 3.9,
            windDeg = 3,
            windGust = 4.42,
            weather = listOf(
                Weather(
                    id = 500,
                    main = "Rain",
                    description = "light rain",
                    icon = "10d"
                )
            ),
            pop = 0.55
        ),
        Hourly(
            dt = 1653318000,
            temp = 15.5,
            feelsLike = 14.59,
            pressure = 1008,
            humidity = 57,
            dewPoint = 7.03,
            uvi = 0.7,
            clouds = 99,
            visibility = 10000,
            windSpeed = 4.68,
            windDeg = 10,
            windGust = 5.16,
            weather = listOf(
                Weather(
                    id = 500,
                    main = "Rain",
                    description = "light rain",
                    icon = "10d"
                )
            ),
            pop = 0.59
        )
    ),
    daily = listOf(
        Daily(
            dt = 1653296400,
            sunrise = 1653271320,
            sunset = 1653327245,
            moonrise = 1653262260,
            moonset = 1653298980,
            moonPhase = 0.77,
            temp = Temp(
                day = 11.63,
                min = 8.93,
                max = 15.61,
                night = 9.06,
                eve = 15.5,
                morn = 9.34
            ),
            feelsLike = FeelsLike(
                day = 10.89,
                night = 7.97,
                eve = 14.59,
                morn = 7.42
            ),
            pressure = 1008,
            humidity = 78,
            dewPoint = 7.94,
            windSpeed = 6.31,
            windDeg = 320.0,
            windGust = 9.01,
            weather = listOf(
                Weather(
                    id = 500,
                    main = "Rain",
                    description = "light rain",
                    icon = "10d"
                )
            ), clouds = 100, pop = 1.0, uvi = 5.04
        ),
        Daily(
            dt = 1653382800,
            sunrise = 1653357660,
            sunset = 1653413717,
            moonrise = 1653349860,
            moonset = 1653389940,
            moonPhase = 0.8,
            temp = Temp(
                day = 17.41,
                min = 7.28,
                max = 17.94,
                night = 9.6,
                eve = 16.84,
                morn = 8.96
            ),
            feelsLike = FeelsLike(
                day = 16.36,
                night = 8.63,
                eve = 15.73,
                morn = 8.96
            ),
            pressure = 1013,
            humidity = 44,
            dewPoint = 5.14,
            windSpeed = 5.76,
            windDeg = 353.0,
            windGust = 6.22,
            weather = listOf(
                Weather(
                    id = 500,
                    main = "Rain",
                    description = "light rain",
                    icon = "10d"
                )
            ), clouds = 20, pop = 0.38, uvi = 5.41
        )
    )
)