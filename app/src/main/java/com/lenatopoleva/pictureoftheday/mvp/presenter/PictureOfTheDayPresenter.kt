package com.lenatopoleva.pictureoftheday.mvp.presenter

import com.lenatopoleva.pictureoftheday.mvp.model.entity.AstronomicalTerms
import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayData
import com.lenatopoleva.pictureoftheday.mvp.model.repo.IPictureOfTheDayRepo
import com.lenatopoleva.pictureoftheday.mvp.view.PictureOfTheDayView
import com.lenatopoleva.pictureoftheday.ui.App
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class PictureOfTheDayPresenter @Inject constructor(
    val app: App, val uiScheduler: Scheduler,
    val router: Router, val pictureOfTheDayRepo: IPictureOfTheDayRepo
): MvpPresenter<PictureOfTheDayView>()  {
    private var show = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadPictureOFTheDayData()
    }

    private fun loadPictureOFTheDayData() {
        pictureOfTheDayRepo.getPictureOfTheDay()
            .retry(3)
            .observeOn(uiScheduler)
            .subscribe(
                { result ->
                    when (result) {
                        is PictureOfTheDayData.Success -> renderData(result)
                        is PictureOfTheDayData.Error -> viewState.showError(result.error.message)
                        is PictureOfTheDayData.Loading -> {
                        }
                    }
                },
                { println("onError: ${it.message}") })
    }

    fun renderData(data: PictureOfTheDayData.Success){
        val serverResponseData = data.serverResponseData
        val url = serverResponseData.url
        val description = serverResponseData.explanation
        val title = serverResponseData.title
        if (url.isNullOrEmpty()) {
            //showError("Сообщение, что ссылка пустая")
            viewState.showError("Link is empty")
        } else {
            //showSuccess()
            if(serverResponseData.mediaType == "video") {
                viewState.showWebView()
                viewState.hideImageView()
                viewState.showVideo(url)
            }
            else {
                viewState.showImageView()
                viewState.hideWebView()
                viewState.showPicture(url)
            }
            viewState.showDescription(description, getTermsToDecorateList(description))
            viewState.showTitle(title)
            }
        }

    private fun getTermsToDecorateList(text: String?):  List<TermToDecorate>? {
        var termsToDecorate:  List<TermToDecorate>? = null
        val terms = AstronomicalTerms.values().map { it.toString() }
        for (term: String in terms) {
            val pattern = """(?i)\b$term\b""".toRegex()
            termsToDecorate = text?.let {
                pattern.findAll(it).map { match ->
                    val indexStart = match.range.first
                    val indexEnd = match.range.last + 1
                    TermToDecorate(match.value, indexStart, indexEnd)
                }.toList()
            }
        }
        println("LIST: $termsToDecorate")
        return termsToDecorate
    }


    fun backClick(): Boolean {
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