package com.lenatopoleva.pictureoftheday.mvp.presenter

import com.lenatopoleva.pictureoftheday.ui.App
import com.lenatopoleva.pictureoftheday.mvp.view.MainView
import com.lenatopoleva.pictureoftheday.navigation.Screens
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter @Inject constructor (val app: App, val router: Router): MvpPresenter<MainView>() {

    companion object{
        const val PICTURE_OF_THE_DAY_SCREEN = "PictureOfTheDayScreen"
        const val WIKI_SEARCH_SCREEN = "WikiSearchScreen"
        const val SETTINGS_SCREEN = "SettingsScreen"
        const val EARTH_SCREEN = "EarthScreen"
    }

    val primaryScreen = Screens.PictureOfTheDayScreen()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(primaryScreen)
    }

    fun backClick() {
        router.exit()
    }

    fun wikiMenuItemClicked() {
        router.navigateTo(Screens.WikiSearchScreen())
    }

    fun settingsMenuItemClicked() {
        router.navigateTo(Screens.SettingsScreen())
    }

    fun photoOfTheDayMenuItemClicked() {
        router.navigateTo(Screens.PictureOfTheDayScreen())
    }

    fun earthMenuItemClicked() {
        router.navigateTo(Screens.EarthGalleryScreen())
    }

    fun checkCurrentBottomMenuItem(currentScreenName: String) {
        if (currentScreenName.contains(PICTURE_OF_THE_DAY_SCREEN)) viewState.setPODMenuItemChecked()
        if (currentScreenName.contains(WIKI_SEARCH_SCREEN)) viewState.setWikiMenuItemChecked()
        if (currentScreenName.contains(SETTINGS_SCREEN)) viewState.setSettingsMenuItemChecked()
        if (currentScreenName.contains(EARTH_SCREEN)) viewState.setEarthMenuItemChecked()
    }

}