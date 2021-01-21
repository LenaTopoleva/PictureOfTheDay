package com.lenatopoleva.pictureoftheday.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EarthPhotoServerResponse (
    @Expose val identifier: String?,
    @Expose val caption: String?,
    @Expose val image: String,
    @Expose val date: String
): Parcelable