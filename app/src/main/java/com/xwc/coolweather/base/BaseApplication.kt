package com.xwc.coolweather.base

import com.xwc.coolweather.enums.BaseEnum
import timber.log.Timber

/**
 * Description：
 * author：Smoker
 * 2019/1/29 10:47
 */
class BaseApplication : org.litepal.LitePalApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BaseEnum.IS_DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant()
        }
    }
}