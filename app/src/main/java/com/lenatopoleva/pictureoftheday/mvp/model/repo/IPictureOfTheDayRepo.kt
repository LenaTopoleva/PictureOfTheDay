package com.lenatopoleva.pictureoftheday.mvp.model.repo

import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayData
import io.reactivex.rxjava3.core.Single

interface IPictureOfTheDayRepo {
    fun getPictureOfTheDay(): Single<PictureOfTheDayData>
}