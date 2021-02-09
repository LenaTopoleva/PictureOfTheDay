package com.lenatopoleva.pictureoftheday.ui

import android.app.Application
import com.lenatopoleva.pictureoftheday.di.AppComponent
import com.lenatopoleva.pictureoftheday.di.DaggerAppComponent
import com.lenatopoleva.pictureoftheday.di.modules.AppModule
import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayServerResponse

class App: Application() {

    var isSplashThemeEnabled = true
    var serverPODResponseData: PictureOfTheDayServerResponse? = null
    var errorPODMessage: String? = null

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