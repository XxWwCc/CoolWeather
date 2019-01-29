package com.xwc.coolweather.bean

import org.litepal.crud.DataSupport

/**
 * Description：省
 * author：Smoker
 * 2019/1/29 10:28
 */
class Province : DataSupport() {
    var id: Int? = null
    var provinceName: String? = null
    var provinceCode: Int? = null

    override fun toString(): String {
        return "Province(id=$id, provinceName=$provinceName, provinceCode=$provinceCode)"
    }
}