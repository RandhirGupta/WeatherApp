package com.example.cyborg.weatherapp.ui.activity

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.view.View
import android.view.animation.AnimationUtils
import com.example.cyborg.weatherapp.BuildConfig
import com.example.cyborg.weatherapp.R
import com.example.cyborg.weatherapp.databinding.ActivityLandingBinding
import com.example.cyborg.weatherapp.injection.Injectable
import com.example.cyborg.weatherapp.ui.FetchLocationAddressAsyncTask
import com.example.cyborg.weatherapp.ui.adapter.WeatherForecastAdapter
import com.example.cyborg.weatherapp.util.getRobotoBlackTypeFace
import com.example.cyborg.weatherapp.util.getRobotoThinTypeFace
import kotlinx.android.synthetic.main.activity_landing.*
import javax.inject.Inject


class LandingActivity : AppCompatActivity(), Injectable, FetchLocationAddressAsyncTask.OnLocationAddressFetch {

    @Inject
    lateinit var mFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mLandingViewModel: LandingViewModel

    private lateinit var mBinding: ActivityLandingBinding
    private lateinit var mForecastAdapter: WeatherForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_landing)
        mBinding.setLifecycleOwner(this)

        mLandingViewModel = ViewModelProviders.of(this, mFactory)
            .get(LandingViewModel::class.java)

        mBinding.viewModel = mLandingViewModel
        initView()

        if (!isPermissionGranted && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requestPermissions(
                PERMISSIONS,
                PERMISSION_REQUEST_CODE
            )
        } else {
            startLocationUpdates()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates()
        }
    }

    override fun onSuccess(address: String?) {
        this.getDataFromLocation(address)
        mBinding.errorLayout.visibility = View.GONE
    }

    override fun onFailure() {
        mBinding.errorLayout.visibility = View.VISIBLE
    }

    private fun startLocationUpdates() {
        mLandingViewModel.location.observe(this, Observer<Location> {
            it?.let {
                mBinding.gpsMessage.visibility = View.GONE
                FetchLocationAddressAsyncTask(this, it, this).execute()
            }
        })
    }

    private fun getDataFromLocation(location: String?) {
        mLandingViewModel.getForeCastWeatherData(BuildConfig.WEATHER_API_TOKEN, location, 5)

        mLandingViewModel.forecastWeatherResponse.observe(this, Observer {
            if (it != null) {

                mBinding.forecastDayData = it.forecast.forecastday[0]
                mBinding.forecastLocationTextView.text = it.location.name
                mBinding.forecastTempTextView.text =
                        String.format("%s %s", it.forecast.forecastday[0].day.maxtemp_c.toString(), "\u2103")
                mBinding.successLayout.visibility = View.VISIBLE
                mBinding.errorLayout.visibility = View.GONE

                val forecastDayList = it.forecast.forecastday.toMutableList()
                forecastDayList.removeAt(0)
                mForecastAdapter = WeatherForecastAdapter(forecastDayList)
                forecastRecyclerView.adapter = mForecastAdapter
            }
        })
    }


    private fun initView() {
        startRotatingImage()
        mBinding.forecastTempTextView.typeface = getRobotoBlackTypeFace(this)
        mBinding.forecastLocationTextView.typeface = getRobotoThinTypeFace(this)
        mBinding.errorMessageTextView.typeface = getRobotoThinTypeFace(this)
        mBinding.gpsMessage.visibility = View.VISIBLE
        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(this, R.drawable.line_divider)?.let { itemDecorator.setDrawable(it) }
        mBinding.forecastRecyclerView.addItemDecoration(itemDecorator)
        mBinding.retryButton.setOnClickListener {
            startLocationUpdates()
        }
    }

    private fun startRotatingImage() {
        val startRotateAnimation = AnimationUtils.loadAnimation(this, R.anim.linear_interpolator)
        mBinding.loadingViewPb.startAnimation(startRotateAnimation)
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
