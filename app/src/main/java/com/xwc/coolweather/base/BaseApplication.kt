package com.xwc.coolweather.base

import android.app.Application
import com.xwc.coolweather.enums.BaseEnum
import timber.log.Timber

/**
 * Description：
 * author：Smoker
 * 2019/1/29 10:47
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BaseEnum.IS_DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant()
        }
    }
}