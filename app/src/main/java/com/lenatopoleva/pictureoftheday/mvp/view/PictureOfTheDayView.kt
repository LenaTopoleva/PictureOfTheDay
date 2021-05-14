package com.lenatopoleva.pictureoftheday.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface PictureOfTheDayView: MvpView {
    fun init()
    fun showPicture(url: String)

    @Skip
    fun showError(message: String?)

    fun showDescription(description: String?)
    fun showTitle(title: String?)
    fun showVideo(url: String)

    fun showWebView()
    fun hideImageView()
    fun showImageView()
    fun hideWebView()
}