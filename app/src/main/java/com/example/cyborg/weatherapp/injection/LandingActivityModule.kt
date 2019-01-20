package com.example.cyborg.weatherapp.injection

import com.example.cyborg.weatherapp.ui.activity.LandingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LandingActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeLandingActivity(): LandingActivity
}