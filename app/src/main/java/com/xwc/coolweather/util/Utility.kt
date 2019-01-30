package com.xwc.coolweather.util

import com.google.gson.Gson
import com.xwc.coolweather.bean.City
import com.xwc.coolweather.bean.County
import com.xwc.coolweather.bean.Province
import com.xwc.coolweather.gson.WeatherBean
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

/**
 * Description：处理返回json数据
 * author：Smoker
 * 2019/1/29 11:07
 */
object Utility {
    /**
     * 解析和处理返回的省级数据
     * */
    fun handleProvinceResponse(response: String): Boolean {
        if (StringUtil.isNotEmpty(response)) {
            val allProvinces = JSONArray(response)
            for (i in 0 until allProvinces.length()) {
                val provinceObject = allProvinces.getJSONObject(i)
                val province = Province()
                province.provinceName = provinceObject.getString("name")
                province.provinceCode = provinceObject.getInt("id")
                province.save()
            }
            return true
        }
        return false
    }

    /**
     * 解析和处理返回的市级数据
     * */
    fun handleCityResponse(response: String, provinceId: Int): Boolean {
        if (StringUtil.isNotEmpty(response)) {
            try {
                val allCities = JSONArray(response)
                for (i in 0 until allCities.length()) {
                    val cityObject = allCities.getJSONObject(i)
                    val city = City()
                    city.cityName = cityObject.getString("name")
                    city.cityCode = cityObject.getInt("id")
                    city.provinceId = provinceId
                    city.save()
                }
                return true
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return false
    }

    /**
     * 解析和处理返回的县级数据
     * */
    fun handleCountyResponse(response: String, cityId: Int): Boolean {
        if (StringUtil.isNotEmpty(response)) {
            try {
                val allCounties = JSONArray(response)
                for (i in 0 until allCounties.length()) {
                    val countyObject = allCounties.getJSONObject(i)
                    val county = County()
                    county.countyName = countyObject.getString("name")
                    county.weatherId = countyObject.getString("weather_id")
                    county.cityId = cityId
                    county.save()
                }
                return true
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return false
    }

    /**
     * 将返回的JSON数据解析成 Weather 实体类
     * */
    fun handleWeatherResponse(response: String): WeatherBean? {
        try {
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("HeWeather")
            val weatherContent = jsonArray.getJSONObject(0).toString()
            return Gson().fromJson(weatherContent, WeatherBean::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}