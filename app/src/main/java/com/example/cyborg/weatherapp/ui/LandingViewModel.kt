package com.example.cyborg.weatherapp.ui

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
    private val loadData: SingleLiveEvent<Unit> = SingleLiveEvent()
    val showErrorView = MutableLiveData<Int>()
    val showLoader = MutableLiveData<Int>()
    val errorMessage = MutableLiveData<String>()

    fun getCurrentWeatherData(apiToken: String, location: String) {
        showErrorView.value = View.GONE
        currentWeatherResponse = Transformations.switchMap(loadData) {
            switchMapForApiResponse(weatherRepository.getCurrentWeatherData(apiToken, location), doOnSuccess = {
                return@switchMapForApiResponse it
            }, doOnSubscribe = {
                showLoader.value = View.VISIBLE
                showErrorView.value = View.GONE
            }, doOnError = {
                showLoader.value = View.GONE
                showErrorView.value = View.VISIBLE
                errorMessage.value = "Something went wrong.."
            })
        }
        loadData.call()
    }

    fun getForeCastWeatherData(apiToken: String, location: String) {
        showErrorView.value = View.GONE
        forecastWeatherResponse = Transformations.switchMap(loadData) {
            switchMapForApiResponse(weatherRepository.getForeCastWeatherData(apiToken, location), doOnSuccess = {
                return@switchMapForApiResponse it
            }, doOnSubscribe = {
                showLoader.value = View.VISIBLE
                showErrorView.value = View.GONE
            }, doOnError = {
                showLoader.value = View.GONE
                showErrorView.value = View.VISIBLE
                errorMessage.value = "Something went wrong.."
            })
        }
        loadData.call()
    }
}