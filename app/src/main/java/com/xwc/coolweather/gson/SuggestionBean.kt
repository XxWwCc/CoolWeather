package com.xwc.coolweather.gson

/**
 * Description：
 * author：Smoker
 * 2019/1/30 10:01
 */
class SuggestionBean {
    var comf: ComfBean? = null
    var cw: CwBean? = null
    var sport: SportBean? = null

    override fun toString(): String {
        return "SuggestionBean(comf=$comf, cw=$cw, sport=$sport)"
    }
}