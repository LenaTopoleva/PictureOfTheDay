package com.lenatopoleva.pictureoftheday.ui.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.api.load
import com.lenatopoleva.pictureoftheday.R
import com.lenatopoleva.pictureoftheday.mvp.model.entity.EarthPhotoServerResponse
import com.lenatopoleva.pictureoftheday.mvp.presenter.EarthPhotoPresenter
import com.lenatopoleva.pictureoftheday.mvp.view.EarthPhotoView
import com.lenatopoleva.pictureoftheday.ui.App
import com.lenatopoleva.pictureoftheday.ui.BackButtonListener
import kotlinx.android.synthetic.main.fragment_earth_photo.*
import kotlinx.android.synthetic.main.fragment_picture_of_the_day.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.io.InputStream


class EarthPhotoFragment  : MvpAppCompatFragment(), EarthPhotoView, BackButtonListener {

    companion object {
        fun newInstance(earthPhotoServerResponse: EarthPhotoServerResponse) = EarthPhotoFragment().apply {
            arguments = Bundle().apply {
                putParcelable("earthPhoto", earthPhotoServerResponse)
            }
        }
    }

//    var isFragmentVisible: Boolean = false

    val presenter by moxyPresenter {
        EarthPhotoPresenter(
            this.arguments?.getParcelable<EarthPhotoServerResponse>("earthPhoto") as EarthPhotoServerResponse
        ).apply { App.instance.appComponent.inject(this) }
    }

    init {
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(context, R.layout.fragment_earth_photo, null)

    override fun init() {
        println("INIT FRAGMENT: ${this.toString()}.")
        presenter.loadData()
    }

    override fun showEarthImage(inputStream: InputStream?) {
        val bmp = BitmapFactory.decodeStream(inputStream)
        println("SHOWING EARTH")
        earth_image_view.load(bmp) {
            lifecycle(this@EarthPhotoFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }
    }

    override fun showError(message: String?) {
       toast(message)
    }

//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        isFragmentVisible = isVisibleToUser
//    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        println("SHOW LOADING ${(this.arguments?.getParcelable<EarthPhotoServerResponse>("earthPhoto") as EarthPhotoServerResponse).date}")
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        println("HIDE LOADING ${(this.arguments?.getParcelable<EarthPhotoServerResponse>("earthPhoto") as EarthPhotoServerResponse).date}")
    }

    override fun backPressed(): Boolean = presenter.backClick()

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }
}