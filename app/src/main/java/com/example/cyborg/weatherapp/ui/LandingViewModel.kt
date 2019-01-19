package com.example.cyborg.weatherapp.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.cyborg.weatherapp.repository.WeatherRepository
import javax.inject.Inject

class LandingViewModel @Inject constructor(application: Application, private val weatherRepository: WeatherRepository) :
    AndroidViewModel(application) {

}