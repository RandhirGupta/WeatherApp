package com.example.cyborg.weatherapp.repository

import android.arch.lifecycle.LiveData
import com.example.cyborg.weatherapp.network.ApiResponse
import com.example.cyborg.weatherapp.network.ApiService
import com.example.cyborg.weatherapp.network.model.CurrentWeatherResponse
import com.example.cyborg.weatherapp.network.model.ForecastWeatherResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject internal constructor(private val mApiService: ApiService) : BaseWeatherRepository {

    override fun getForeCastWeatherData(
        apiToken: String,
        location: String?,
        noOfDays: Int
    ): LiveData<ApiResponse<ForecastWeatherResponse>> = mApiService.getForecastWeatherData(apiToken, location, noOfDays)

    override fun getCurrentWeatherData(
        apiToken: String,
        location: String
    ): LiveData<ApiResponse<CurrentWeatherResponse>> = mApiService.getCurrentWeatherData(apiToken, location)
}
