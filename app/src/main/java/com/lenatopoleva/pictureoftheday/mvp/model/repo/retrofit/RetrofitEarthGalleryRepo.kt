package com.lenatopoleva.pictureoftheday.mvp.model.repo.retrofit

import com.lenatopoleva.pictureoftheday.BuildConfig
import com.lenatopoleva.pictureoftheday.mvp.model.api.IEarthGallerySource
import com.lenatopoleva.pictureoftheday.mvp.model.entity.EarthPhotoData
import com.lenatopoleva.pictureoftheday.mvp.model.entity.EarthPhotoServerResponse
import com.lenatopoleva.pictureoftheday.mvp.model.repo.IEarthGalleryRepo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call

class RetrofitEarthGalleryRepo(val api: IEarthGallerySource): IEarthGalleryRepo {

    private lateinit var apiKey: String

    override fun getLastEarthPictures(): Single<EarthPhotoData> {
        apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            return Single.just(EarthPhotoData.Error(Throwable("You need API key")))
        } else {
            return api.getEarthPhotosList(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map { EarthPhotoData.Success(it) }
        }
    }

    override fun getEarthPhoto(earthPhotoServerResponse: EarthPhotoServerResponse): Call<ResponseBody> {
        val date: String = earthPhotoServerResponse.date.substring(0,10).replace("-", "/")
        val image = earthPhotoServerResponse.image

        return api.getEarthPhoto(date, image, apiKey)

    }

}