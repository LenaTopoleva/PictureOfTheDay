package com.lenatopoleva.pictureoftheday.mvp.model.repo

import com.lenatopoleva.pictureoftheday.mvp.model.entity.EarthPhotoData
import com.lenatopoleva.pictureoftheday.mvp.model.entity.EarthPhotoServerResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Call

interface IEarthGalleryRepo {
    fun getLastEarthPictures(): Single<EarthPhotoData>
    fun getEarthPhoto(earthPhotoServerResponse: EarthPhotoServerResponse): Call<ResponseBody>
}