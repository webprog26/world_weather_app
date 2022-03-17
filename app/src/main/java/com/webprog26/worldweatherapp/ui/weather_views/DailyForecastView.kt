package com.webprog26.worldweatherapp.ui.weather_views

import android.widget.RelativeLayout
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.webprog26.worldweatherapp.R
import com.webprog26.worldweatherapp.databinding.ViewForecastDailyBinding
import com.webprog26.worldweatherapp.utils.INCORRECT_ICON_CODE
import com.webprog26.worldweatherapp.utils.convertMillisToDateString
import com.webprog26.worldweatherapp.utils.getWeatherIconRes
import com.webprog26.worldweatherapp.weather_data.Daily

class DailyForecastView : RelativeLayout {

    private lateinit var binding: ViewForecastDailyBinding

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0)
            : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int)
            : super(context, attrs, defStyleAttr)


    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = ViewForecastDailyBinding.bind(this)
    }

    fun setDailyForecastData(daily: Daily) {
        val weather = daily.weather[0]
        binding.tvDailyForecastDate.text = convertMillisToDateString(daily.dt)
        val forecastIconId = getWeatherIconRes(weather.icon)
        if (forecastIconId != INCORRECT_ICON_CODE) {
            binding.dailyForecastIcon.setImageDrawable(ContextCompat.getDrawable(context, forecastIconId))
        }
        binding.tvDailyForecastTemp.text = context.getString(R.string.current_temp, daily.temp.day.toInt())

    }
}