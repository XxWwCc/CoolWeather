package com.xwc.coolweather.bean

import org.litepal.crud.DataSupport

/**
 * Description：市
 * author：Smoker
 * 2019/1/29 10:30
 */
class City : DataSupport() {
    var id: Int = 0
    var cityName: String = ""
    var cityCode: Int = 0
    var provinceId: Int = 0

    override fun toString(): String {
        return "City(id=$id, cityName=$cityName, cityCode=$cityCode, provinceId=$provinceId)"
    }
}