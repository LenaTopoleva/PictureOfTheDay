package com.lenatopoleva.pictureoftheday.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface SettingsView: MvpView {
    fun init()
    fun saveTheme(themeName: String)

    @Skip
    fun updateTheme()

    fun checkTheme()
}