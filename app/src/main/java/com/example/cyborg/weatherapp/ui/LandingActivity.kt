package com.example.cyborg.weatherapp.ui

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.cyborg.weatherapp.R
import com.example.cyborg.weatherapp.injection.Injectable
import javax.inject.Inject


class LandingActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var mFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mLandingViewModel: LandingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        mLandingViewModel = ViewModelProviders.of(this, mFactory)
            .get(LandingViewModel::class.java)

        if (!isPermissionGranted && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE)
        } else {
            startLocationUpdates()
        }
    }

    private fun startLocationUpdates() {
        mLandingViewModel.location.observe(this, Observer<Location> {
            it?.let {
                this.getDataFromLocation(it)
            }
        })
    }

    private fun getDataFromLocation(location: Location) {

        mLandingViewModel.getCurrentWeatherData(getString(R.string.api_token), "bangalore")
        mLandingViewModel.getForeCastWeatherData(getString(R.string.api_token), "bangalore")

        mLandingViewModel.currentWeatherResponse.observe(this, Observer {
            if (it != null) {
                Log.d(TAG, it.current.temp_c.toString())
                Log.d(TAG, it.location.country)
            }
        })

        mLandingViewModel.forecastWeatherResponse.observe(this, Observer {
            if (it != null) {
                Log.d(TAG, it.forecast.forecastday[0].date)
                Log.d(TAG, it.location.country)
            }
        })
    }

    private val isPermissionGranted: Boolean
        get() = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    companion object {

        private val PERMISSION_REQUEST_CODE = 1420
        private val PERMISSIONS =
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

        private val TAG = "LandingActivity"
    }
}
