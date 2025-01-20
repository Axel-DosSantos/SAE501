package com.example.myapplication.utils

import android.content.Context

fun Context.isFirstLaunch(): Boolean {
    val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val isFirstLaunch = sharedPreferences.getBoolean("is_first_launch", true)
    if (isFirstLaunch) {
        sharedPreferences.edit().putBoolean("is_first_launch", false).apply()
    }
    return isFirstLaunch
}
