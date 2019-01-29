package com.xwc.coolweather.util

import com.xwc.coolweather.bean.City
import com.xwc.coolweather.bean.County
import com.xwc.coolweather.bean.Province
import org.json.JSONArray
import org.json.JSONException

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
        if (StringUtil.isEmpty(response)) {
            try {
                val allProvinces = JSONArray(response)
                for (i in 0 until allProvinces.length()) {
                    val provinceObject = allProvinces.getJSONObject(i)
                    val province = Province()
                    province.provinceName = provinceObject.getString("name")
                    province.provinceCode = provinceObject.getInt("id")
                    province.save()
                }
                return true
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return false
    }

    /**
     * 解析和处理返回的市级数据
     * */
    fun handleCityResponse(response: String, provinceId: Int): Boolean {
        if (StringUtil.isEmpty(response)) {
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
        if (StringUtil.isEmpty(response)) {
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
}