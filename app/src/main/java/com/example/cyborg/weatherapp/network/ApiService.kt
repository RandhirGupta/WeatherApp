package com.example.cyborg.weatherapp.network

import android.arch.lifecycle.LiveData
import com.example.cyborg.weatherapp.network.model.CurrentWeatherResponse
import com.example.cyborg.weatherapp.network.model.ForecastWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("current.json?")
    @Headers("Content-Type: application/json")
    fun getCurrentWeatherData(@Query("key") apiToken: String, @Query("q") location: String): LiveData<ApiResponse<CurrentWeatherResponse>>

    @GET("forecast.json?")
    @Headers("Content-Type: application/json")
    fun getForecastWeatherData(@Query("key") apiToken: String, @Query("q") location: String?, @Query("days") noOfDays: Int): LiveData<ApiResponse<ForecastWeatherResponse>>
}
