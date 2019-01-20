package com.example.cyborg.weatherapp.ui.activity

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.location.Location
import android.view.View
import com.example.cyborg.weatherapp.network.model.CurrentWeatherResponse
import com.example.cyborg.weatherapp.network.model.ForecastWeatherResponse
import com.example.cyborg.weatherapp.repository.WeatherRepository
import com.example.cyborg.weatherapp.util.SingleLiveEvent
import com.example.cyborg.weatherapp.util.switchMapForApiResponse
import javax.inject.Inject

class LandingViewModel @Inject constructor(application: Application, private val weatherRepository: WeatherRepository) :
    AndroidViewModel(application) {

    @Inject
    lateinit var location: LiveData<Location>

    lateinit var currentWeatherResponse: LiveData<CurrentWeatherResponse?>

    lateinit var forecastWeatherResponse: LiveData<ForecastWeatherResponse?>

    private val loadCurrentWeatherData: SingleLiveEvent<Unit> = SingleLiveEvent()
    private val loadForecastWeatherData: SingleLiveEvent<Unit> = SingleLiveEvent()
    val showLoader = MutableLiveData<Int>()
    val showErrorView = MutableLiveData<Int>()

    init {
        showLoader.value = View.GONE
        showErrorView.value = View.GONE
    }

//    fun getCurrentWeatherData(apiToken: String, location: String) {
//        currentWeatherResponse = Transformations.switchMap(loadCurrentWeatherData) {
//            switchMapForApiResponse(weatherRepository.getCurrentWeatherData(apiToken, location), doOnSuccess = {
//                showLoader.value = View.GONE
//                showErrorView.value = View.GONE
//                return@switchMapForApiResponse it
//            }, doOnSubscribe = {
//                showLoader.value = View.VISIBLE
//                showErrorView.value = View.GONE
//            }, doOnError = {
//                showLoader.value = View.GONE
//                showErrorView.value = View.VISIBLE
//            })
//        }
//        loadCurrentWeatherData.call()
//    }

    fun getForeCastWeatherData(apiToken: String, location: String?, noOfDays: Int) {
        forecastWeatherResponse = Transformations.switchMap(loadForecastWeatherData) {
            switchMapForApiResponse(
                weatherRepository.getForeCastWeatherData(apiToken, location, noOfDays),
                doOnSuccess = {
                    showLoader.value = View.GONE
                    return@switchMapForApiResponse it
                },
                doOnSubscribe = {
                    showLoader.value = View.VISIBLE
                    showErrorView.value = View.GONE
                },
                doOnError = {
                    showLoader.value = View.GONE
                    showErrorView.value = View.VISIBLE
                })
        }
        loadForecastWeatherData.call()
    }
}