package com.example.cyborg.weatherapp.repository

import android.arch.lifecycle.LiveData
import com.example.cyborg.weatherapp.network.ApiResponse
import com.example.cyborg.weatherapp.network.model.CurrentWeatherResponse
import com.example.cyborg.weatherapp.network.model.ForecastWeatherResponse

interface BaseWeatherRepository {
    fun getCurrentWeatherData(apiToken: String, location: String): LiveData<ApiResponse<CurrentWeatherResponse>>

    fun getForeCastWeatherData(apiToken: String, location: String): LiveData<ApiResponse<ForecastWeatherResponse>>
}