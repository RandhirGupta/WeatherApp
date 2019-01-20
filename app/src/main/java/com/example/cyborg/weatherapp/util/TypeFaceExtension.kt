package com.example.cyborg.weatherapp.util

import android.content.Context
import android.graphics.Typeface

fun getRobotoBlackTypeFace(context: Context): Typeface {
    return Typeface.createFromAsset(context.assets, "fonts/Roboto-Black.ttf")
}

fun getRobotoRegularTypeFace(context: Context): Typeface {
    return Typeface.createFromAsset(context.assets, "fonts/Roboto-Regular.ttf")
}

fun getRobotoThinTypeFace(context: Context): Typeface {
    return Typeface.createFromAsset(context.assets, "fonts/Roboto-Thin.ttf")
}