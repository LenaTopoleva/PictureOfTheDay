package com.lenatopoleva.pictureoftheday.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isGone
import coil.api.load
import com.lenatopoleva.pictureoftheday.ui.App
import com.lenatopoleva.pictureoftheday.R
import com.lenatopoleva.pictureoftheday.mvp.presenter.PictureOfTheDayPresenter
import com.lenatopoleva.pictureoftheday.mvp.view.PictureOfTheDayView
import com.lenatopoleva.pictureoftheday.ui.BackButtonListener
import com.lenatopoleva.pictureoftheday.ui.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_picture_of_the_day.*
import kotlinx.android.synthetic.main.fragment_wiki_search.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class PictureOfTheDayFragment: MvpAppCompatFragment(), PictureOfTheDayView, BackButtonListener {
    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: PictureOfTheDayPresenter

    @ProvidePresenter
    fun provide() = presenter

    init {
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val view = View.inflate(context, R.layout.fragment_picture_of_the_day, null)
        return view
    }

    override fun init() {}

    override fun showPicture(url: String) {
        image_view.load(url) {
            lifecycle(this@PictureOfTheDayFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }
    }

    override fun showError(message: String?) {
      toast(message)
    }

    override fun showDescription(description: String?) {
        pod_description.setText(description)
    }

    override fun showTitle(title: String?) {
        pod_description_header.setText(title)
    }

    override fun showVideo(url: String) {
        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        web_view.clearCache(true)
        web_view.clearHistory()
        web_view.settings.setJavaScriptEnabled(true)
        web_view.settings.javaScriptCanOpenWindowsAutomatically = true
        web_view.loadUrl(url)
    }

    override fun showWebView() {web_view.isGone = false}

    override fun hideImageView() {image_view.isGone = true}

    override fun showImageView() {image_view.isGone = false}

    override fun hideWebView() {web_view.isGone = true}

    override fun backPressed() = presenter.backClick()

}