package com.lenatopoleva.pictureoftheday.di.modules

import com.lenatopoleva.pictureoftheday.mvp.model.api.IPictureOfTheDaySource
import com.lenatopoleva.pictureoftheday.mvp.model.repo.IPictureOfTheDayRepo
import com.lenatopoleva.pictureoftheday.mvp.model.repo.retrofit.RetrofitPictureOfTheDayRepo
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class PictureOfTheDayModule {

    @Singleton
    @Provides
    fun pictureOfTheDayRepo(api: IPictureOfTheDaySource): IPictureOfTheDayRepo =
        RetrofitPictureOfTheDayRepo(api)
}