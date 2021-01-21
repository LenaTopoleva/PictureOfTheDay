package com.lenatopoleva.pictureoftheday.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lenatopoleva.pictureoftheday.R
import com.lenatopoleva.pictureoftheday.mvp.presenter.EarthGalleryPresenter
import com.lenatopoleva.pictureoftheday.mvp.view.EarthGalleryView
import com.lenatopoleva.pictureoftheday.ui.App
import com.lenatopoleva.pictureoftheday.ui.BackButtonListener
import com.lenatopoleva.pictureoftheday.ui.adapter.ViewPagerAdapter
import com.lenatopoleva.pictureoftheday.ui.utils.toast
import kotlinx.android.synthetic.main.fragment_earth_gallery.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class EarthGalleryFragment: MvpAppCompatFragment(), EarthGalleryView, BackButtonListener {

    companion object {
        fun newInstance() = EarthGalleryFragment()
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: EarthGalleryPresenter

    @ProvidePresenter
    fun provide() = presenter

    init {
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = View.inflate(context, R.layout.fragment_earth_gallery, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun init() {
        view_pager.adapter = ViewPagerAdapter(presenter.viewPagerPresenter, childFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun showError(message: String?) {
        toast(message)
    }

    override fun backPressed(): Boolean = presenter.backClick()

}
