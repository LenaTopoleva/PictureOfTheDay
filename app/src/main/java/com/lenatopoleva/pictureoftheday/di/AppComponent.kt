package com.lenatopoleva.pictureoftheday.di

import com.lenatopoleva.pictureoftheday.di.modules.*
import com.lenatopoleva.pictureoftheday.mvp.presenter.*
import com.lenatopoleva.pictureoftheday.ui.activity.MainActivity
import com.lenatopoleva.pictureoftheday.ui.fragment.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ApiModule::class,
    PictureOfTheDayModule::class,
    NavigationModule::class,
    EarthGalleryModule::class
])

interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(pictureOfTheDayPresenter: PictureOfTheDayPresenter)
    fun inject(wikiSearchPresenter: WikiSearchPresenter)
    fun inject(pictureOfTheDayFragment: PictureOfTheDayFragment)
    fun inject(wikiSearchFragment: WikiSearchFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(earthGalleryFragment: EarthGalleryFragment)
    fun inject(earthGalleryPresenter: EarthGalleryPresenter)
    fun inject(earthPhotoFragment: EarthPhotoFragment)
    fun inject(earthPhotoPresenter: EarthPhotoPresenter)

}