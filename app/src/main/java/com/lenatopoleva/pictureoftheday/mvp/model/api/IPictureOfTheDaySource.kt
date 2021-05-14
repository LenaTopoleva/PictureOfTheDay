package com.lenatopoleva.pictureoftheday.mvp.model.api

import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayServerResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IPictureOfTheDaySource {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Single<PictureOfTheDayServerResponse>
}