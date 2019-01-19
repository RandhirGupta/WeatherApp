package com.example.cyborg.weatherapp.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.support.v4.app.ActivityCompat
import com.google.android.gms.location.*
import javax.inject.Inject

class LocationLiveData @Inject
constructor(private val mContext: Context) : LiveData<Location>() {

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation
            value = location

            removeCallback()
        }


        override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
            super.onLocationAvailability(locationAvailability)

            if (locationAvailability?.isLocationAvailable != null) {
                getLastKnownLocation()
            }
        }
    }

    private val isPermissionGranted: Boolean
        get() = ActivityCompat.checkSelfPermission(
            mContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            mContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private val fusedLocationProviderClient: FusedLocationProviderClient
        get() {
            if (mFusedLocationProviderClient == null) {
                mFusedLocationProviderClient = LocationServices
                    .getFusedLocationProviderClient(mContext)
            }

            return mFusedLocationProviderClient as FusedLocationProviderClient
        }


    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        if (!isPermissionGranted) {
            return
        }

        val locationProviderClient = fusedLocationProviderClient
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val looper = Looper.myLooper()

        locationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, looper)

    }


    override fun onInactive() {
        super.onInactive()
        removeCallback()
    }

    private fun removeCallback() {
        val locationProviderClient = fusedLocationProviderClient
        locationProviderClient.removeLocationUpdates(mLocationCallback)
    }


    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        fusedLocationProviderClient.lastLocation
            .addOnCompleteListener { task ->

                task.result?.let {
                    value = it
                }
                removeCallback()
            }
    }
}
