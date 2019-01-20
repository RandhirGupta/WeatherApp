package com.example.cyborg.weatherapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cyborg.weatherapp.network.ApiService
import com.example.cyborg.weatherapp.network.ApiSuccessResponse
import com.example.cyborg.weatherapp.network.LiveDataCallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ApiServiceTest {

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    private var apiService: ApiService? = null

    private var mockWebServer: MockWebServer? = null

    @Before
    fun createApiService() {
        mockWebServer = MockWebServer()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer!!.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(ApiService::class.java)
    }

    @After
    @Throws(IOException::class)
    fun stopService() {
        mockWebServer?.shutdown()
    }

    @Test
    @Throws(IOException::class, InterruptedException::class, ClassCastException::class)
    public fun getForecastWeatherData() {
        enqueueResponse("forecast_forest_data.json")

        val location = "bangalore"
        val apiToken = BuildConfig.WEATHER_API_TOKEN

        val path = String.format(Locale.ENGLISH, "forecast.json?key=%s&q=%s&days=%d", apiToken, location, 5)

        val forecastWeatherData = (LiveDataTestUtil.getValue(
            apiService?.getForecastWeatherData(
                apiToken,
                location,
                5
            )!!
        ) as ApiSuccessResponse).body

        val request = mockWebServer?.takeRequest()
        assertThat(request?.path, `is`(path))
        assertThat(forecastWeatherData, notNullValue())
        assertThat(forecastWeatherData.location.name, `is`("bangalore"))
        assertThat(forecastWeatherData.forecast.forecastday[0].day.maxtemp_c, `is`(27.3))
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, Collections.emptyMap())
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val response = MockResponse()
        for ((key, value) in headers) {
            response.addHeader(key, value)
        }

        mockWebServer?.enqueue(response.setBody(source.readString(StandardCharsets.UTF_8)))
    }
}