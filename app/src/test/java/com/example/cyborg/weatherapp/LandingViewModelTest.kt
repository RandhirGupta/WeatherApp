package com.example.cyborg.weatherapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.example.cyborg.weatherapp.repository.WeatherRepository
import com.example.cyborg.weatherapp.ui.activity.LandingViewModel
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class LandingViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mock(WeatherRepository::class.java)
    private var landingViewModel = LandingViewModel(repository)

    @Test
    fun testNull() {
        assertThat(landingViewModel.forecastWeatherResponse, notNullValue())
        assertThat(landingViewModel.location, notNullValue())
        verify(repository, never()).getForeCastWeatherData(anyString(), anyString(), ArgumentMatchers.anyInt())
    }
}