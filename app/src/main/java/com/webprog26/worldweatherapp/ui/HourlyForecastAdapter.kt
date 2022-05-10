package com.webprog26.worldweatherapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.webprog26.worldweatherapp.R
import com.webprog26.worldweatherapp.databinding.ViewForecastHourlyBinding
import com.webprog26.worldweatherapp.utils.convertMillisToHhMm
import com.webprog26.worldweatherapp.weather_data.Hourly

class HourlyForecastAdapter(
    private val hourlyForecastList: List<Hourly>
) : RecyclerView.Adapter<HourlyForecastAdapter.HourlyForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder {
        val binding =
            ViewForecastHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        holder.bind(hourlyForecastList[position])
    }

    override fun getItemCount(): Int {
        return hourlyForecastList.size
    }

    class HourlyForecastViewHolder(
        private val binding: ViewForecastHourlyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hourlyForecast: Hourly) {
            val dateTime = hourlyForecast.dt
            binding.tvHourlyTime.text = convertMillisToHhMm(dateTime)
            binding.tvHourlyTemp.text =
                itemView.context.getString(R.string.current_temp, hourlyForecast.temp.toInt())
        }
    }
}