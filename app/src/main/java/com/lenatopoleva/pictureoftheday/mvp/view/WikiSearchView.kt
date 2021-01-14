package com.lenatopoleva.pictureoftheday.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface WikiSearchView: MvpView {
    fun init()
    fun showWikiPage(url: String)
}