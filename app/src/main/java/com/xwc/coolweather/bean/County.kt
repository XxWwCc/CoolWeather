package com.xwc.coolweather.bean

import org.litepal.crud.DataSupport

/**
 * Description：县
 * author：Smoker
 * 2019/1/29 10:32
 */
class County : DataSupport() {
    var id: Int? = null
    var countyName: String? = null
    var weatherId: String? = null
    var cityId: Int? = null

    override fun toString(): String {
        return "County(id=$id, countyName=$countyName, weatherId=$weatherId, cityId=$cityId)"
    }
}