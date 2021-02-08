package com.lenatopoleva.pictureoftheday.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip


@AddToEndSingle
interface SplashView: MvpView {
    fun init()
    fun hideSupportActionBar()
    fun hideBottomNavigation()
    fun animateSplashImage()
    fun disableSplashTheme()

    @Skip
    fun recreateActivity()
}