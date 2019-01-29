package com.xwc.coolweather.util

import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Description：http请求
 * author：Smoker
 * 2019/1/29 11:01
 */
object HttpUtil {
    fun sendOkHttpRequest(address: String, callback: okhttp3.Callback) {
        val client = OkHttpClient()
        val request = Request.Builder().url(address).build()
        client.newCall(request).enqueue(callback)
    }
}