package com.lenatopoleva.pictureoftheday.mvp.view

import com.lenatopoleva.pictureoftheday.mvp.presenter.PictureOfTheDayPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface PictureOfTheDayView: MvpView {
    fun init()
    fun showPicture(url: String)

    @Skip
    fun showError(message: String?)

    fun showDescription(
        description: CharSequence?,
        termsToDecorateList: List<PictureOfTheDayPresenter.TermToDecorate>
    )
    fun showTitle(title: String?)
    fun showVideo(url: String)

    fun showWebView()
    fun hideImageView()
    fun showImageView()
    fun hideWebView()

    fun showComponents()
    fun hideComponents()

    @Skip
    fun recreateActivity()

    fun enableSplashThemeIfItIsTheLastFragmentInStack()

}