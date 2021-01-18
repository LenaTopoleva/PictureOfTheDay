package com.lenatopoleva.pictureoftheday.mvp.presenter

import com.lenatopoleva.pictureoftheday.mvp.view.SettingsView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SettingsPresenter @Inject constructor(val router: Router): MvpPresenter<SettingsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.checkTheme()
        viewState.init()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    fun themeChipCheked(isChecked: Boolean, themeName: String) {
        if (isChecked) {
            println("Chip checked, new theme name is $themeName.")
            viewState.saveTheme(themeName)
            viewState.updateTheme()
        }
    }

}