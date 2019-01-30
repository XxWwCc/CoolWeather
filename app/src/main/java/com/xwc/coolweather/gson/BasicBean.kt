package com.xwc.coolweather.gson

/**
 * Description：
 * author：Smoker
 * 2019/1/30 09:54
 */
class BasicBean {
    var city = ""
    var id = ""
    var update: UpdateBean? = null

    override fun toString(): String {
        return "BasicBean(city='$city', id='$id', update=$update)"
    }
}