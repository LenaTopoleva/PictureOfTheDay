package com.lenatopoleva.pictureoftheday.di.modules

import com.lenatopoleva.pictureoftheday.mvp.model.api.IEarthGallerySource
import com.lenatopoleva.pictureoftheday.mvp.model.repo.IEarthGalleryRepo
import com.lenatopoleva.pictureoftheday.mvp.model.repo.retrofit.RetrofitEarthGalleryRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class EarthGalleryModule {

    @Singleton
    @Provides
    fun earthGalleryRepo(api: IEarthGallerySource): IEarthGalleryRepo =
        RetrofitEarthGalleryRepo(api)
}