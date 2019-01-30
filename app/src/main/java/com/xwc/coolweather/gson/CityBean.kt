package com.xwc.coolweather.gson

/**
 * Description：
 * author：Smoker
 * 2019/1/30 09:56
 */
class CityBean {
    var aqi = ""
    var pm25 = ""

    override fun toString(): String {
        return "CityBean(aqi='$aqi', pm25='$pm25')"
    }
}