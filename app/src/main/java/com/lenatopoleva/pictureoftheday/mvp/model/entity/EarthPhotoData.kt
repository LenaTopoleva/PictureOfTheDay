package com.lenatopoleva.pictureoftheday.mvp.model.entity

sealed class EarthPhotoData {
        data class Success(val serverResponseData: List<EarthPhotoServerResponse>) : EarthPhotoData()
        data class Error(val error: Throwable) : EarthPhotoData()
        data class Loading(val progress: Int?) : EarthPhotoData()
    }
