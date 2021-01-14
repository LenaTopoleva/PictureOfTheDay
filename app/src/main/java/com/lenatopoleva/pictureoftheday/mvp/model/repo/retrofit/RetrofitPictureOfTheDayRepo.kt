package com.lenatopoleva.pictureoftheday.mvp.model.repo.retrofit

import com.lenatopoleva.pictureoftheday.BuildConfig
import com.lenatopoleva.pictureoftheday.mvp.model.api.IPictureOfTheDaySource
import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayData
import com.lenatopoleva.pictureoftheday.mvp.model.repo.IPictureOfTheDayRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitPictureOfTheDayRepo(val api: IPictureOfTheDaySource): IPictureOfTheDayRepo {

    override fun getPictureOfTheDay(): Single<PictureOfTheDayData> {
        var data: PictureOfTheDayData? = null
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            data = PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            api.getPictureOfTheDay(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .blockingSubscribe({result -> data = PictureOfTheDayData.Success(result) },
                    {error -> data = PictureOfTheDayData.Error(error) })
        }
       return Single.just(data)
    }
}