package com.lenatopoleva.pictureoftheday.mvp.presenter

import com.lenatopoleva.pictureoftheday.mvp.model.entity.EarthPhotoServerResponse
import com.lenatopoleva.pictureoftheday.mvp.model.repo.IEarthGalleryRepo
import com.lenatopoleva.pictureoftheday.mvp.view.EarthPhotoView
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.terrakok.cicerone.Router


class EarthPhotoPresenter(
    val earthPhotoServerResponse: EarthPhotoServerResponse,
    val router: Router,
    val repo: IEarthGalleryRepo,
    val uiScheduler: Scheduler, ): MvpPresenter<EarthPhotoView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun loadData() {
        println("LOAD DATA for photo: ${earthPhotoServerResponse.date}")
        viewState.showLoading()
        repo.getEarthPhoto(earthPhotoServerResponse).enqueue(
            object :Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                viewState.hideLoading()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // display the image data in a ImageView or save it
                        viewState.showEarthImage(response.body()?.byteStream())
                    } else {
                        viewState.showError("Can't find image")
                    }
                } else {
                    viewState.showError("Network error")
                }
            }

            override fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {
                viewState.showError(t?.message)
            }
        })

    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}