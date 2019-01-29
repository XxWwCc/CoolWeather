package com.xwc.coolweather.base

<<<<<<< HEAD
import android.app.Application
import com.xwc.coolweather.enums.BaseEnum
import timber.log.Timber

=======
>>>>>>> 5079faa47d8dd8417503416f91d8c4fddc0f1913
/**
 * Description：
 * author：Smoker
 * 2019/1/29 10:47
 */
<<<<<<< HEAD
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BaseEnum.IS_DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant()
        }
    }
=======
class BaseApplication {
>>>>>>> 5079faa47d8dd8417503416f91d8c4fddc0f1913
}