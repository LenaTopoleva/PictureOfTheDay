package com.lenatopoleva.pictureoftheday.di

import com.lenatopoleva.pictureoftheday.di.modules.ApiModule
import com.lenatopoleva.pictureoftheday.di.modules.AppModule
import com.lenatopoleva.pictureoftheday.di.modules.NavigationModule
import com.lenatopoleva.pictureoftheday.di.modules.PictureOfTheDayModule
import com.lenatopoleva.pictureoftheday.mvp.presenter.MainPresenter
import com.lenatopoleva.pictureoftheday.mvp.presenter.PictureOfTheDayPresenter
import com.lenatopoleva.pictureoftheday.mvp.presenter.WikiSearchPresenter
import com.lenatopoleva.pictureoftheday.ui.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ApiModule::class,
    PictureOfTheDayModule::class,
    NavigationModule::class
])

interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(pictureOfTheDayPresenter: PictureOfTheDayPresenter)
    fun inject(wikiSearchPresenter: WikiSearchPresenter)

}