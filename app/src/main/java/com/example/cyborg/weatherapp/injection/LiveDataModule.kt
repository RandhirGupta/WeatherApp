package com.example.cyborg.weatherapp.injection

import android.arch.lifecycle.LiveData
import android.location.Location
import com.example.cyborg.weatherapp.viewmodel.LocationLiveData
import dagger.Binds
import dagger.Module

@Module
internal abstract class LiveDataModule {

    @Binds
    internal abstract fun bindLiveDataLocation(locationLiveData: LocationLiveData): LiveData<Location>
}
