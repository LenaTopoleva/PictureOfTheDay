package com.lenatopoleva.pictureoftheday.mvp.presenter

import com.lenatopoleva.pictureoftheday.mvp.model.entity.EarthPhotoServerResponse
import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayServerResponse
import com.lenatopoleva.pictureoftheday.mvp.model.repo.IEarthGalleryRepo
import com.lenatopoleva.pictureoftheday.ui.App
import io.reactivex.rxjava3.core.Scheduler
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class PresenterFactory @Inject constructor(private val router: Router,
                                           private val repo: IEarthGalleryRepo,
                                           private val uiScheduler: Scheduler,
                                           val app: App) {

    fun createEarthPhotoPresenter (earthPhotoServerResponse: EarthPhotoServerResponse) = EarthPhotoPresenter(earthPhotoServerResponse, router, repo, uiScheduler)

    fun createPODPresenter (serverResponseData: PictureOfTheDayServerResponse?, errorMessage: String?) = PictureOfTheDayPresenter(serverResponseData, errorMessage, app, uiScheduler, router )
}
