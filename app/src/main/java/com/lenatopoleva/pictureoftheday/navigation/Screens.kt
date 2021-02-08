package com.lenatopoleva.pictureoftheday.navigation

import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayServerResponse
import com.lenatopoleva.pictureoftheday.ui.fragment.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class PictureOfTheDayScreen(val serverResponseData: PictureOfTheDayServerResponse?, val errorMessage: String?) : SupportAppScreen() {
        override fun getFragment() =
                PictureOfTheDayFragment.newInstance(serverResponseData, errorMessage)
    }

    class WikiSearchScreen() : SupportAppScreen() {
        override fun getFragment() = WikiSearchFragment.newInstance()
    }

    class SettingsScreen() : SupportAppScreen() {
        override fun getFragment() = SettingsFragment.newInstance()
    }

    class EarthGalleryScreen() : SupportAppScreen() {
        override fun getFragment() = EarthGalleryFragment.newInstance()
    }

    class NotesScreen() : SupportAppScreen() {
        override fun getFragment() = NotesFragment.newInstance()
    }

    class SplashScreen() : SupportAppScreen() {
        override fun getFragment() = SplashFragment.newInstance()
    }
}