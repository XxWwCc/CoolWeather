package com.xwc.coolweather.gson

/**
 * Description：
 * author：Smoker
 * 2019/1/30 09:58
 */
class NowBean {
    var tmp = ""
    var cond: CondBean? = null

    override fun toString(): String {
        return "NowBean(tmp='$tmp', cond=$cond)"
    }
}