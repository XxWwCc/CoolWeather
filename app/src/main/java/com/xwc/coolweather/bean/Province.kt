package com.xwc.coolweather.bean

import org.litepal.crud.DataSupport

/**
 * Description：省
 * author：Smoker
 * 2019/1/29 10:28
 */
class Province : DataSupport() {
    var id: Int = -1
    var provinceName: String = ""
    var provinceCode: Int = -1

    override fun toString(): String {
        return "Province(id=$id, provinceName=$provinceName, provinceCode=$provinceCode)"
    }
}