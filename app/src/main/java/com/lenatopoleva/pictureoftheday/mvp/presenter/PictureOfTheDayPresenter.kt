package com.lenatopoleva.pictureoftheday.mvp.presenter

import com.lenatopoleva.pictureoftheday.mvp.model.entity.AstronomicalTerms
import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayServerResponse
import com.lenatopoleva.pictureoftheday.mvp.view.PictureOfTheDayView
import com.lenatopoleva.pictureoftheday.ui.App
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router


class PictureOfTheDayPresenter (
        val serverResponseData: PictureOfTheDayServerResponse?,
        val errorMessage: String?,
        val app: App,
        val uiScheduler: Scheduler,
        val router: Router
): MvpPresenter<PictureOfTheDayView>()  {
    private var show = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        renderData(serverResponseData, errorMessage)
    }

    fun renderData(data: PictureOfTheDayServerResponse?, errorMessage: String?){
        data?.let {
            val serverResponseData = data
            val url = serverResponseData.url
            val description = serverResponseData.explanation
            val title = serverResponseData.title
            if (url.isNullOrEmpty()) {
                //showError("Сообщение, что ссылка пустая")
                viewState.showError("Link is empty")
            } else {
                //showSuccess()
                if (serverResponseData.mediaType == "video") {
                    viewState.showWebView()
                    viewState.hideImageView()
                    viewState.showVideo(url)
                } else {
                    viewState.showImageView()
                    viewState.hideWebView()
                    viewState.showPicture(url)
                }
                viewState.showDescription(description, getTermsToDecorateList(description))
                viewState.showTitle(title)
            }
        } ?: viewState.showError(errorMessage)
    }

    private fun getTermsToDecorateList(text: String?):  List<TermToDecorate> {
        var termsToDecorate:  MutableList<TermToDecorate>? = mutableListOf()
        val terms = AstronomicalTerms.values().map { it.toString() }
        for (term: String in terms) {
            val pattern = """(?i)\b$term\b""".toRegex()
            val newTerms = text?.let {
                pattern.findAll(it).map { match ->
                    val indexStart = match.range.first
                    val indexEnd = match.range.last + 1
                    TermToDecorate(match.value, indexStart, indexEnd)
                }.toMutableList()
            }
           newTerms?.let { termsToDecorate?.addAll(it) }
        }
        println("LIST: $termsToDecorate")
        return termsToDecorate ?: listOf()
    }


    fun backClick(): Boolean {
        viewState.enableSplashThemeIfItIsTheLastFragmentInStack()
        router.exit()
        return true
    }

    fun onLayoutClicked() {
        if (show) hideComponents() else showComponents()
    }

    private fun showComponents() {
        show = true
        viewState.showComponents()
    }

    private fun hideComponents() {
        show = false
        viewState.hideComponents()
    }

    data class TermToDecorate(val term: String, val indexStart: Int, val indexEnd: Int)

}