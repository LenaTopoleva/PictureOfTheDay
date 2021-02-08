package com.lenatopoleva.pictureoftheday.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.RelativeSizeSpan
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isGone
import coil.api.load
import com.lenatopoleva.pictureoftheday.R
import com.lenatopoleva.pictureoftheday.mvp.model.entity.EarthPhotoServerResponse
import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayData
import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayServerResponse
import com.lenatopoleva.pictureoftheday.mvp.presenter.PictureOfTheDayPresenter
import com.lenatopoleva.pictureoftheday.mvp.presenter.PresenterFactory
import com.lenatopoleva.pictureoftheday.mvp.view.PictureOfTheDayView
import com.lenatopoleva.pictureoftheday.ui.App
import com.lenatopoleva.pictureoftheday.ui.BackButtonListener
import com.lenatopoleva.pictureoftheday.ui.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_picture_of_the_day_start.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class PictureOfTheDayFragment: MvpAppCompatFragment(), PictureOfTheDayView, BackButtonListener {
    companion object {
        fun newInstance(serverResponseData: PictureOfTheDayServerResponse?, errorMessage: String?) = PictureOfTheDayFragment().apply {
            arguments = Bundle().apply {
                putParcelable(POD_SERVER_RESPONSE, serverResponseData)
                putString(ERROR_MESSAGE, errorMessage)
            }
        }
        const val POD_SERVER_RESPONSE = "serverResponseData"
        const val ERROR_MESSAGE = "errorMessage"
    }

    @Inject
    lateinit var presenterFactory: PresenterFactory

    val presenter by moxyPresenter {
        presenterFactory.createPODPresenter(this.arguments?.getParcelable<PictureOfTheDayServerResponse?>(POD_SERVER_RESPONSE) as PictureOfTheDayServerResponse,
                this.arguments?.getString(ERROR_MESSAGE)
        )
    }

    init {
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = View.inflate(context, R.layout.fragment_picture_of_the_day_start, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).bottom_navigation_view?.visibility = View.VISIBLE

    }

    override fun init() {
        tap.setOnClickListener { presenter.onLayoutClicked()}
        pod_description.setOnClickListener { presenter.onLayoutClicked()}
        pod_description_header.setOnClickListener { presenter.onLayoutClicked()}
        pod_layout.setOnClickListener { presenter.onLayoutClicked()}
        //Added the ability to follow links in textView
        pod_description.setMovementMethod(LinkMovementMethod.getInstance());
    }

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

    override fun showDescription(
        description: CharSequence?,
        termsToDecorateList: List<PictureOfTheDayPresenter.TermToDecorate>?
    ) {
        val spannable = SpannableString(description)
        createDropCap(spannable)
        val decoratedDescription = underlineTerms(spannable, termsToDecorateList)
        pod_description.text = decoratedDescription
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

    override fun backPressed(): Boolean = presenter.backClick()

    override fun showComponents() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.fragment_picture_of_the_day_end)
        val transition = ChangeBounds()
        transition.interpolator = AccelerateInterpolator(1.0f)
        transition.duration = 400
        TransitionManager.beginDelayedTransition(pod_layout, transition)
        constraintSet.applyTo(pod_layout)
    }

    override fun hideComponents() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.fragment_picture_of_the_day_start)
        val transition = ChangeBounds()
        transition.interpolator = LinearInterpolator()
        transition.duration = 600
        TransitionManager.beginDelayedTransition(pod_layout, transition)
        constraintSet.applyTo(pod_layout)
    }

    override fun recreateActivity() {
        activity?.recreate()
    }

    private fun isItTheLastFragmentInStack(): Boolean {
        return (fragmentManager?.fragments?.size == 1)
    }

    override fun enableSplashThemeIfItIsTheLastFragmentInStack() {
       if ( isItTheLastFragmentInStack() ) App.instance.isSplashThemeEnabled = true
    }

    fun underlineTerms(spannable: SpannableString, termsToDecorateList: List<PictureOfTheDayPresenter.TermToDecorate>?): SpannableString {
        if (termsToDecorateList != null) {
            for(term: PictureOfTheDayPresenter.TermToDecorate in termsToDecorateList){
                spannable.setSpan(
                    CustomClickableSpan(term.term, requireActivity()), term.indexStart, term.indexEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
        }
        return spannable
    }

    fun createDropCap(spannable: SpannableString) {
        spannable.setSpan(RelativeSizeSpan(2f), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }

}

class CustomClickableSpan(val term: String, val context: Context): ClickableSpan() {
    override fun onClick(widget: View) {
        openWiki()
    }
    private fun openWiki(){
        val urlIntent = Intent(Intent.ACTION_VIEW)
        urlIntent.data = Uri.parse("https://en.wikipedia.org/wiki/$term")
        startActivity(context, urlIntent, null)
    }
}


