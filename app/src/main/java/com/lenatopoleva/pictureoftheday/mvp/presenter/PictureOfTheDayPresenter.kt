package com.lenatopoleva.pictureoftheday.mvp.presenter

import com.lenatopoleva.pictureoftheday.ui.App
import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayData
import com.lenatopoleva.pictureoftheday.mvp.model.repo.IPictureOfTheDayRepo
import com.lenatopoleva.pictureoftheday.mvp.view.PictureOfTheDayView
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class PictureOfTheDayPresenter @Inject constructor (val app: App, val uiScheduler: Scheduler,
                                                    val router: Router, val pictureOfTheDayRepo: IPictureOfTheDayRepo): MvpPresenter<PictureOfTheDayView>()  {

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
                    when(result){
                        is PictureOfTheDayData.Success -> renderData(result)
                        is PictureOfTheDayData.Error -> viewState.showError(result.error.message)
                        is PictureOfTheDayData.Loading -> {  }
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
            viewState.showDescription(description)
            viewState.showTitle(title)
            }
        }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

}