package com.tuandi.architecture.initializer

import android.content.Context
import androidx.startup.Initializer
import com.tuandi.architecture.BuildConfig
import com.tuandi.architecture.log.DebugTree
import timber.log.Timber

class MyLibInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            Timber.i("Timber initialized")
        }
        return
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
