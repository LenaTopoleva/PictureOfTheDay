package com.lenatopoleva.pictureoftheday.mvp.presenter

import com.lenatopoleva.pictureoftheday.ui.App
import com.lenatopoleva.pictureoftheday.mvp.view.WikiSearchView
import kotlinx.android.synthetic.main.fragment_wiki_search.*
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class WikiSearchPresenter @Inject constructor(val app: App, val router: Router): MvpPresenter<WikiSearchView>()  {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    fun searchInWiki(term: String) {
        viewState.showWikiPage("https://en.wikipedia.org/wiki/$term")
    }

    fun onFragmentViewCreated() {
        viewState.startAnimation()
    }

}