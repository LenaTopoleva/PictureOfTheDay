package com.lenatopoleva.pictureoftheday.mvp.presenter

import com.lenatopoleva.pictureoftheday.mvp.view.SplashView
import com.lenatopoleva.pictureoftheday.navigation.Screens
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SplashPresenter @Inject constructor(val router: Router): MvpPresenter<SplashView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    fun openFirstFragment() {
        router.replaceScreen(Screens.PictureOfTheDayScreen())
    }

    fun onSplashViewCreated() {
        viewState.hideSupportActionBar()
        viewState.hideBottomNavigation()
        viewState.animateSplashImage()
    }

    fun onAnimationEnd() {
        viewState.disableSplashTheme()
        openFirstFragment()
        viewState.recreateActivity()
    }
}