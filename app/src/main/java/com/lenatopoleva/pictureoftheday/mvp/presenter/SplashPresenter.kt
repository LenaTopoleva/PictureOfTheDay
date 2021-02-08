package com.lenatopoleva.pictureoftheday.mvp.presenter

import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayData
import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayServerResponse
import com.lenatopoleva.pictureoftheday.mvp.model.repo.IPictureOfTheDayRepo
import com.lenatopoleva.pictureoftheday.mvp.view.SplashView
import com.lenatopoleva.pictureoftheday.navigation.Screens
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SplashPresenter @Inject constructor(val router: Router, val pictureOfTheDayRepo: IPictureOfTheDayRepo, val uiScheduler: Scheduler): MvpPresenter<SplashView>() {

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
                                is PictureOfTheDayData.Success -> {
                                    viewState.saveData(result.serverResponseData, null)
                                    openFirstFragment(result.serverResponseData, null)
                                }
                                is PictureOfTheDayData.Error -> {
                                    viewState.saveData(null, result.error.message)
                                    openFirstFragment(null, result.error.message)
                                }
                                is PictureOfTheDayData.Loading -> {
                                }
                            }
                            onDataResolved()
                        },
                        { println("onError: ${it.message}") })
    }


    fun backClick(): Boolean {
        router.exit()
        return true
    }

    fun openFirstFragment(serverResponseData: PictureOfTheDayServerResponse?, errorMessage: String?) {
        router.replaceScreen(Screens.PictureOfTheDayScreen(serverResponseData, errorMessage))
    }

    fun onSplashViewCreated() {
        viewState.hideSupportActionBar()
        viewState.hideBottomNavigation()
    }

    fun onDataResolved() {
        viewState.disableSplashTheme()
        viewState.recreateActivity()
    }
}