package com.lenatopoleva.pictureoftheday.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip
import java.io.InputStream

@AddToEndSingle
interface EarthPhotoView: MvpView {
    fun init()
    fun showEarthImage(inputStream: InputStream?)
    fun showError(message: String?)
    @Skip
    fun showLoading()
    @Skip
    fun hideLoading()
}