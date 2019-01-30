package com.xwc.coolweather.gson

/**
 * Description：
 * author：Smoker
 * 2019/1/30 10:07
 */
class WeatherBean {
    var status = ""
    var basic: BasicBean? = null
    var aqi: AqiBean? = null
    var now: NowBean? = null
    var suggestion: SuggestionBean? = null
    var daily_forecast: List<DailyForecastBean>? = null

    override fun toString(): String {
        return "WeatherBean(status='$status', basic=$basic, aqi=$aqi, now=$now, suggestion=$suggestion, daily_forecast=$daily_forecast)"
    }
}