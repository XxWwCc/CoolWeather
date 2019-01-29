package com.xwc.coolweather.bean

import org.litepal.crud.DataSupport

/**
 * Description：市
 * author：Smoker
 * 2019/1/29 10:30
 */
class City : DataSupport() {
    var id: Int? = null
    var cityName: String? = null
    var cityCode: Int? = null
    var provinceId: Int? = null

    override fun toString(): String {
        return "City(id=$id, cityName=$cityName, cityCode=$cityCode, provinceId=$provinceId)"
    }
}