package com.lenatopoleva.pictureoftheday.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.lenatopoleva.pictureoftheday.ui.App
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Singleton

@Module
class AppModule(val app: App) {

    val appName = "PictureOfTheDay"

    @Provides
    fun app(): App {
        return app
    }

    @Provides
    fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Singleton
    @Provides
    fun sharedPreferences(): SharedPreferences = app.getSharedPreferences(appName, Context.MODE_PRIVATE)


}