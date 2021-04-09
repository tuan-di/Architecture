package com.tuandi.architecture.util.initializer

import android.content.Context
import androidx.startup.Initializer
import com.tuandi.architecture.BuildConfig
import com.tuandi.architecture.util.initializer.log.CrashlyticsTree
import com.tuandi.architecture.util.initializer.log.DebugTree
import timber.log.Timber

class MyLibInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            Timber.i("Timber initialized")
        } else {
            Timber.plant(CrashlyticsTree())
        }
        return
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
