package com.lenatopoleva.pictureoftheday.mvp.view

import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayServerResponse
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip


@AddToEndSingle
interface SplashView: MvpView {
    fun init()
    fun hideSupportActionBar()
    fun hideBottomNavigation()
    fun disableSplashTheme()

    @Skip
    fun recreateActivity()
    fun saveData(serverResponseData: PictureOfTheDayServerResponse?, errorMessage: String?)
}