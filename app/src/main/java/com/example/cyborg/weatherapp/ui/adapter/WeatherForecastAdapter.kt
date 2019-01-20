package com.example.cyborg.weatherapp.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.cyborg.weatherapp.databinding.ForecastSingleItemBinding
import com.example.cyborg.weatherapp.network.model.Forecastday
import com.example.cyborg.weatherapp.util.getDayName
import com.example.cyborg.weatherapp.util.getRobotoRegularTypeFace

class WeatherForecastAdapter(var forecastDayList: List<Forecastday>?) :
    RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>() {

    private var mContext: Context? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): WeatherForecastViewHolder {
        this.mContext = p0.context
        val layoutInflater = LayoutInflater.from(p0.context)
        val dataBinding = ForecastSingleItemBinding.inflate(layoutInflater, p0, false)
        return WeatherForecastViewHolder(dataBinding)
    }

    override fun onBindViewHolder(p0: WeatherForecastViewHolder, p1: Int) {
        val forecastDay = forecastDayList?.get(p1)
        p0.binding.forecastDayData = forecastDay
        p0.binding.forecastDayTextView.text = getDayName(forecastDay?.date!!)
        p0.binding.forecastTempTextView.text = String.format("%s %s", forecastDay.day.maxtemp_c.toString(), "\u2103")
        p0.binding.forecastDayTextView.typeface = getRobotoRegularTypeFace(this.mContext!!)
        p0.binding.forecastTempTextView.typeface = getRobotoRegularTypeFace(this.mContext!!)
    }

    override fun getItemCount(): Int {
        return forecastDayList?.size!!
    }

    class WeatherForecastViewHolder(var binding: ForecastSingleItemBinding) : RecyclerView.ViewHolder(binding.root)
}