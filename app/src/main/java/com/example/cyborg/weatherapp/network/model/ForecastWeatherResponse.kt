package com.example.cyborg.weatherapp.network.model

data class ForecastWeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)

data class Forecast(
    val forecastday: List<Forecastday>
)

data class Forecastday(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day
)

data class Astro(
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)

data class Day(
    val avghumidity: Int,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val avgvis_km: Double,
    val avgvis_miles: Int,
    val condition: Condition,
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val maxwind_kph: Double,
    val maxwind_mph: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val totalprecip_in: Int,
    val totalprecip_mm: Int,
    val uv: String
)