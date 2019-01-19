package com.example.cyborg.weatherapp.injection

import android.app.Application
import android.content.Context
import com.example.cyborg.weatherapp.network.ApiService
import com.example.cyborg.weatherapp.network.LiveDataCallAdapterFactory
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [(ViewModelModule::class), (LiveDataModule::class)])
internal class AppModule {

    @Provides
    @Singleton
    fun providesApiService(gson: Gson): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.apixu.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

}
