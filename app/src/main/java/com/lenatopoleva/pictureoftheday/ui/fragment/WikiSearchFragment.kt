package com.lenatopoleva.pictureoftheday.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.lenatopoleva.pictureoftheday.R
import com.lenatopoleva.pictureoftheday.mvp.presenter.WikiSearchPresenter
import com.lenatopoleva.pictureoftheday.mvp.view.WikiSearchView
import com.lenatopoleva.pictureoftheday.ui.App
import com.lenatopoleva.pictureoftheday.ui.BackButtonListener
import kotlinx.android.synthetic.main.fragment_wiki_search.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class WikiSearchFragment: MvpAppCompatFragment(), WikiSearchView, BackButtonListener {

    init {
        App.instance.appComponent.inject(this)
    }

    companion object {
        fun newInstance() = WikiSearchFragment()
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: WikiSearchPresenter

    @ProvidePresenter
    fun provide() = presenter

    lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = View.inflate(context, R.layout.fragment_wiki_search, null)
        webView = rootView.findViewById<WebView>(R.id.wiki_web_view)
        webView.setBackgroundColor(Color.TRANSPARENT)
        return rootView
    }

    override fun init() {
        input_layout.setEndIconOnClickListener {
            presenter.searchInWiki(input_edit_text.text.toString())
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun showWikiPage(url: String) {
        println("SHOW WIKI PAGE fun starts")
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        webView.clearCache(true)
        webView.clearHistory()
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.visibility = View.VISIBLE
        println("WEB VIEW IS VISIBLE: ${webView.isVisible}")
        webView.loadUrl(url)
    }

    override fun backPressed() = presenter.backClick()
}
