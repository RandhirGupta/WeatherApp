package com.example.cyborg.weatherapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getDayName(inputDate: String): String {
    val inFormat = SimpleDateFormat("yyyy-MM-dd")
    val date = inFormat.parse(inputDate)
    val outFormat = SimpleDateFormat("EEEE")
    return outFormat.format(date)
}