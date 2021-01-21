package com.lenatopoleva.pictureoftheday.navigation

import com.lenatopoleva.pictureoftheday.ui.fragment.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class PictureOfTheDayScreen() : SupportAppScreen() {
        override fun getFragment() = PictureOfTheDayFragment.newInstance()
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
}