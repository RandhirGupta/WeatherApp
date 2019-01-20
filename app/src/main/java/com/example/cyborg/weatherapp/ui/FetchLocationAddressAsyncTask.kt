package com.example.cyborg.weatherapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import android.text.TextUtils
import java.util.*

@SuppressLint("StaticFieldLeak")
class FetchLocationAddressAsyncTask constructor(
    private val context: Context,
    private val location: Location,
    private val onLocationAddressFetch: OnLocationAddressFetch
) : AsyncTask<Void, Void, String>() {


    override fun doInBackground(vararg params: Void?): String? {
        val latitude = location.latitude
        val longitude = location.longitude
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addressess = geocoder.getFromLocation(latitude, longitude, 1)
            if (addressess != null && addressess.size > 0) {
                return addressess.get(0).locality
            }
        } catch (e: Exception) {
            return null
        }
        return null
    }

    override fun onPostExecute(result: String?) {
        if (!TextUtils.isEmpty(result)) {
            onLocationAddressFetch.onSuccess(result)
        } else {
            onLocationAddressFetch.onFailure()
        }
    }

    public interface OnLocationAddressFetch {
        fun onSuccess(address: String?)
        fun onFailure()
    }
}