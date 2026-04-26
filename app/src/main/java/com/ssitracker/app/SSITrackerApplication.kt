package com.ssitracker.app

import android.app.Application
import com.ssitracker.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class SSITrackerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SSITrackerApplication)
            modules(appModule)
        }
    }
}
