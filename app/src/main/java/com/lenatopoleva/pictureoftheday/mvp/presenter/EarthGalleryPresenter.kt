package com.lenatopoleva.pictureoftheday.mvp.presenter

import com.lenatopoleva.pictureoftheday.mvp.model.entity.EarthPhotoData
import com.lenatopoleva.pictureoftheday.mvp.model.entity.EarthPhotoServerResponse
import com.lenatopoleva.pictureoftheday.mvp.model.entity.PictureOfTheDayData
import com.lenatopoleva.pictureoftheday.mvp.model.repo.IEarthGalleryRepo
import com.lenatopoleva.pictureoftheday.mvp.view.EarthGalleryView
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class EarthGalleryPresenter @Inject constructor(val router: Router, val repo: IEarthGalleryRepo, val uiScheduler: Scheduler): MvpPresenter<EarthGalleryView>() {

    class ViewPagerPresenter{

        var earthPhotos = mutableListOf<EarthPhotoServerResponse>()

        fun getEarthPhotoServerResponseAt(position: Int): EarthPhotoServerResponse {
            return earthPhotos[position]
        }

        fun getCount(): Int {
            return earthPhotos.size
        }

        fun getPageTitle(): CharSequence? {
            return null
        }
    }

    val viewPagerPresenter = ViewPagerPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
    }

    private fun loadData() {
        repo.getLastEarthPictures()
            .retry(3)
            .observeOn(uiScheduler)
            .subscribe(
                { result ->
                    when(result){
                        is EarthPhotoData.Success -> {
                            viewPagerPresenter.earthPhotos = result.serverResponseData as MutableList<EarthPhotoServerResponse>
                            viewState.init()
                        }
                        is EarthPhotoData.Error -> viewState.showError(result.error.message)
                        is EarthPhotoData.Loading -> {  }
                    }
                },
                { println("onError: ${it.message}") })
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}