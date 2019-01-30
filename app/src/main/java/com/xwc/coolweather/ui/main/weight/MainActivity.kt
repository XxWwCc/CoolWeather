package com.xwc.coolweather.ui.main.weight

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.xwc.coolweather.R
import com.xwc.coolweather.ui.weather.weight.WeatherActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (prefs.getString("weather", null) != null) {
            WeatherActivity.openActivity(this, "")
            finish()
        }
    }
}
