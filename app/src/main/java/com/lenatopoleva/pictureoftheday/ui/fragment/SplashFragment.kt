package com.lenatopoleva.pictureoftheday.ui.fragment

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lenatopoleva.pictureoftheday.R
import com.lenatopoleva.pictureoftheday.mvp.presenter.SplashPresenter
import com.lenatopoleva.pictureoftheday.mvp.view.SplashView
import com.lenatopoleva.pictureoftheday.ui.App
import com.lenatopoleva.pictureoftheday.ui.BackButtonListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_splash.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class SplashFragment: MvpAppCompatFragment(), SplashView, BackButtonListener {

    companion object {
        fun newInstance() = SplashFragment()
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: SplashPresenter

    @ProvidePresenter
    fun provide() = presenter

    init {
        App.instance.appComponent.inject(this)
    }


    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View  {
        val view = View.inflate(context, R.layout.fragment_splash, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onSplashViewCreated()
    }

        override fun init() {}

    override fun hideSupportActionBar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun hideBottomNavigation() {
        (activity as AppCompatActivity).bottom_navigation_view?.visibility = View.GONE
    }

    override fun animateSplashImage() {
        splash_image_view.animate().rotationBy(360f)
            .setInterpolator(AccelerateDecelerateInterpolator()).setDuration(3000)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator?) {
                    presenter.onAnimationEnd()
                }
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
    }

    override fun disableSplashTheme() {
        App.instance.isSplashThemeEnabled = false
    }

    override fun recreateActivity() {
        activity?.recreate()
    }

    override fun backPressed(): Boolean = presenter.backClick()

}