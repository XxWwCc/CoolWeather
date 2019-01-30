package com.xwc.coolweather.ui.weather.weight

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.xwc.coolweather.R
import com.xwc.coolweather.enums.LinkEnum
import com.xwc.coolweather.gson.WeatherBean
import com.xwc.coolweather.util.HttpUtil
import com.xwc.coolweather.util.Utility
import kotlinx.android.synthetic.main.activity_weather.*
import okhttp3.Call
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

/**
 * Description：天气
 * author：Smoker
 * 2019/1/30 14:21
 */
class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        initView()
    }

    private fun initView() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val weatherString = prefs.getString("weather", null)
        if (weatherString != null) {
            // 有缓存数据时直接解析天气数据
            val weather = Utility.handleWeatherResponse(weatherString)
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Timber.e("返回的weather为null")
            }
        } else {
            // 无缓存时去服务器查询天气
            val weatherId = intent.getStringExtra("weather_id")
//            nsv_weather.visibility = View.INVISIBLE
            requestWeather(weatherId)
        }
        val bingPic = prefs.getString("bing_pic", null)
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(iv_bing_pic)
        } else {
            loadBingPic()
        }
    }

    private fun loadBingPic() {
        HttpUtil.sendOkHttpRequest(LinkEnum.PICTURE_LINK, object : okhttp3.Callback{
            override fun onResponse(call: Call, response: Response) {
                val bingPic = response.body()?.string()
                val editor = PreferenceManager.getDefaultSharedPreferences(this@WeatherActivity).edit()
                editor.putString("bing_pic", bingPic)
                editor.apply()
                runOnUiThread {
                    kotlin.run {
                        Glide.with(this@WeatherActivity).load(bingPic).into(iv_bing_pic)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }

    /**
     * 根据天气id请求城市天气信息
     * */
    private fun requestWeather(weatherId: String) {
        val weatherUrl = "${LinkEnum.LINK_WEATHER}?location=$weatherId&key=${LinkEnum.LINK_KEY}"
        Timber.e("link:$weatherUrl")
        HttpUtil.sendOkHttpRequest(weatherUrl, object : okhttp3.Callback{
            override fun onResponse(call: Call, response: Response) {
                val responseText = response.body()?.string() ?: ""
                val weather = Utility.handleWeatherResponse(responseText)
                Timber.e("=====$weather")
                runOnUiThread{
                    kotlin.run {
                        if (weather != null && "ok" == weather.status) {
                            val editor = PreferenceManager.getDefaultSharedPreferences(this@WeatherActivity).edit()
                            editor.putString("weather", responseText)
                            editor.apply()
                            showWeatherInfo(weather)
                        } else {
                            Toast.makeText(this@WeatherActivity, "获取天气信息失败", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    kotlin.run {
                        Toast.makeText(this@WeatherActivity, "获取天气信息失败", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    /**
     * 处理并展示Weather实体类中的数据
     * */
    private fun showWeatherInfo(weather: WeatherBean) {
        Timber.e("weatherBean:$weather")
        val cityName = weather.basic?.city
        val updateTime = weather.basic?.update?.loc

    }

    companion object {
        fun openActivity(context: Context, weatherId: String) {
            val intent = Intent(context, WeatherActivity::class.java)
            intent.putExtra("weather_id", weatherId)
            context.startActivity(intent)
        }
    }
}