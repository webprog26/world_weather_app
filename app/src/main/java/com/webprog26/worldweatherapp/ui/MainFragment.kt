package com.webprog26.worldweatherapp.ui

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.webprog26.worldweatherapp.R
import com.webprog26.worldweatherapp.databinding.FragmentMainBinding
import com.webprog26.worldweatherapp.ui.weather_views.DailyForecastView
import com.webprog26.worldweatherapp.utils.convertMillisToHhMm
import com.webprog26.worldweatherapp.utils.getWeatherIconRes
import com.webprog26.worldweatherapp.utils.getWindDirection
import com.webprog26.worldweatherapp.weather_data.City
import com.webprog26.worldweatherapp.weather_data.WeatherData

class MainFragment : Fragment(), MainView {

    interface OnWeatherDataUpdateRequestedListener {
        fun onWeatherDataUpdateRequested()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnWeatherDataUpdateRequestedListener) {
            this.onWeatherDataUpdateRequestedListener = context
        }
    }

    private lateinit var onWeatherDataUpdateRequestedListener: OnWeatherDataUpdateRequestedListener

    private lateinit var binding: FragmentMainBinding

    private lateinit var hourlyForecastAdapter: HourlyForecastAdapter

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        onWeatherDataUpdateRequestedListener.onWeatherDataUpdateRequested()
        binding.refreshLayout.isRefreshing = true
        binding.refreshLayout.isRefreshing = false
    }

    companion object {

        private var instance: MainFragment? = null

        fun getInstance(): MainFragment {
            if (instance == null) {
                val fragment = MainFragment()
                instance = fragment
            }
            return instance as MainFragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.refreshLayout.setOnRefreshListener(refreshListener)
    }

    override fun onCurrentCityAvailable(city: City) {
        val cityName = city.name
        if (TextUtils.isEmpty(cityName).not()) {
            binding.tvCurrentCity.text = city.name
        }

    }

    override fun onWeatherDataAvailable(weatherData: WeatherData) {
        val currentForecast = weatherData.current
        val currentDailyForecast = weatherData.daily[0]

        val currentTemp = currentForecast.temp.toInt()

        val dailyMinTemp = currentDailyForecast.temp.min.toInt()
        val dailyMaxTemp = currentDailyForecast.temp.max.toInt()
        val dailyWeatherDescription = currentForecast.weather[0].main
        val lastUpdatedTime =
            convertMillisToHhMm(currentDailyForecast.dt + weatherData.timezoneOffset)

        binding.tvCurrentTemp.text = getString(R.string.current_temp, currentTemp)
        binding.tvMinMaxTemp.text = getString(R.string.min_max_temp, dailyMinTemp, dailyMaxTemp)
        binding.ivCurrentWeatherIcon.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                getWeatherIconRes(weatherData.current.weather[0].icon)
            )
        )
        binding.tvWeatherDescription.text = dailyWeatherDescription
        binding.tvLastUpdated.text = getString(R.string.last_updated_time, lastUpdatedTime)

        binding.rvHourlyForecast.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        hourlyForecastAdapter =
            HourlyForecastAdapter(weatherData.hourly.filterIndexed { index, _ ->
                index in 1..24
            })
        binding.rvHourlyForecast.adapter = hourlyForecastAdapter

        val dailyForecastsContainer = binding.dailyForecastsContainer

        dailyForecastsContainer.removeAllViews()

        for (i in 1 until weatherData.daily.size) {
            val dailyForecastView = LayoutInflater.from(requireContext())
                .inflate(
                    R.layout.view_forecast_daily,
                    dailyForecastsContainer,
                    false
                ) as DailyForecastView
            dailyForecastView.setDailyForecastData(weatherData.daily[i])
            dailyForecastsContainer.addView(dailyForecastView)
        }

        ObjectAnimator.ofInt(
            binding.humidityProgress, "progress",
            0, weatherData.current.humidity
        ).apply {
            interpolator = LinearInterpolator()
            duration = 3000L

        }.run {
            start()
        }

        Log.i("weather_data_deb" ,"wind direction: ${getString(getWindDirection(weatherData.current.windDeg))}")

        binding.tvTempFeelsLike.text = getString(R.string.temp_feels_like_text, weatherData.current.feelsLike.toInt())
        binding.tvVisibility.text = getString(R.string.visibility_text, weatherData.current.visibility)

        binding.tvWindDirection.text = getString(R.string.wind_direction, getString(getWindDirection(weatherData.current.windDeg)))
        binding.tvWindSpeed.text = getString(R.string.wind_speed, weatherData.current.windSpeed.toInt())
    }

    override fun onLoadingStarted() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    override fun onLoadingCompleted() {
        binding.pbLoading.visibility = View.GONE
        binding.currentWeatherLayout.visibility = View.VISIBLE
        binding.rvHourlyForecast.visibility = View.VISIBLE
        binding.dailyForecastsContainer.visibility = View.VISIBLE
        binding.comfortLevelDataContainer.visibility = View.VISIBLE
        binding.windDataContainer.visibility = View.VISIBLE
    }
}