package com.lenatopoleva.pictureoftheday.ui

import android.app.Application
import com.lenatopoleva.pictureoftheday.di.AppComponent
import com.lenatopoleva.pictureoftheday.di.DaggerAppComponent
import com.lenatopoleva.pictureoftheday.di.modules.AppModule

class App: Application() {

    companion object {
        lateinit var instance: App
    }
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent =  DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}