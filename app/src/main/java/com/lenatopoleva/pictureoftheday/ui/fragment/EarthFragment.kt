package com.lenatopoleva.pictureoftheday.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lenatopoleva.pictureoftheday.R
import com.lenatopoleva.pictureoftheday.mvp.presenter.EarthPresenter
import com.lenatopoleva.pictureoftheday.mvp.view.EarthView
import com.lenatopoleva.pictureoftheday.ui.App
import com.lenatopoleva.pictureoftheday.ui.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class EarthFragment: MvpAppCompatFragment(), EarthView, BackButtonListener {

    companion object {
        fun newInstance() = EarthFragment()
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: EarthPresenter

    @ProvidePresenter
    fun provide() = presenter

    init {
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(context, R.layout.fragment_earth, null)

    override fun init() {}

    override fun backPressed(): Boolean = presenter.backClick()

}
