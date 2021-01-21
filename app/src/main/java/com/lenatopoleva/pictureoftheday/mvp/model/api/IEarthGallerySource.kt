package com.lenatopoleva.pictureoftheday.mvp.model.api

import com.lenatopoleva.pictureoftheday.mvp.model.entity.EarthPhotoServerResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IEarthGallerySource {
    @GET("EPIC/api/natural/images")
    fun getEarthPhotosList(@Query("api_key") apiKey: String): Single<List<EarthPhotoServerResponse>>

    @GET("EPIC/archive/natural/{date}/png/{image}.png")
    fun getEarthPhoto(@Path(value = "date", encoded = true) date: String, @Path(value = "image", encoded = true) image: String, @Query("api_key") apiKey: String): Call<ResponseBody>
}