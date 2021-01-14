package com.lenatopoleva.pictureoftheday.navigation

import com.lenatopoleva.pictureoftheday.ui.fragment.PictureOfTheDayFragment
import com.lenatopoleva.pictureoftheday.ui.fragment.WikiSearchFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class PictureOfTheDayScreen() : SupportAppScreen() {
        override fun getFragment() = PictureOfTheDayFragment.newInstance()
    }

    class WikiSearchScreen() : SupportAppScreen() {
        override fun getFragment() = WikiSearchFragment.newInstance()
    }
}