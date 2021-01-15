package com.lenatopoleva.pictureoftheday.mvp.model.repo.retrofit

import com.lenatopoleva.pictureoftheday.BuildConfig
import com.lenatopoleva.pictureoftheday.mvp.model.api.IPictureOfTheDaySource
import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayData
import com.lenatopoleva.pictureoftheday.mvp.model.repo.IPictureOfTheDayRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitPictureOfTheDayRepo(val api: IPictureOfTheDaySource): IPictureOfTheDayRepo {

    override fun getPictureOfTheDay(): Single<PictureOfTheDayData> {
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            return Single.just(PictureOfTheDayData.Error(Throwable("You need API key")))
        } else {
            return api.getPictureOfTheDay(apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { PictureOfTheDayData.Success(it) }
        }
    }
}