package com.xwc.coolweather.gson

/**
 * Description：
 * author：Smoker
 * 2019/1/30 10:06
 */
class DailyForecastBean {
    var date = ""
    var cond: CondBean? = null
    var tmp: TmpBean? = null

    override fun toString(): String {
        return "DailyForecastBean(date='$date', cond=$cond, tmp=$tmp)"
    }
}