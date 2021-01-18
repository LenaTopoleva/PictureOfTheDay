package com.lenatopoleva.pictureoftheday.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.lenatopoleva.pictureoftheday.R
import com.lenatopoleva.pictureoftheday.mvp.presenter.SettingsPresenter
import com.lenatopoleva.pictureoftheday.mvp.view.SettingsView
import com.lenatopoleva.pictureoftheday.ui.App
import com.lenatopoleva.pictureoftheday.ui.BackButtonListener
import kotlinx.android.synthetic.main.fragment_settings.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class SettingsFragment : MvpAppCompatFragment(), SettingsView, BackButtonListener {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    @Inject lateinit var sharedPreferences: SharedPreferences
    var currentTheme: String? = ""

    @Inject
    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    @ProvidePresenter
    fun provide() = presenter

    init {
        App.instance.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) = View.inflate(context, R.layout.fragment_settings, null)

    override fun init() {
        when(currentTheme){
            "Theme.Moon" -> moon_chip.isChecked = true
            "Theme.Mars" -> mars_chip.isChecked = true
        }
        moon_chip.setOnCheckedChangeListener{ compoundButton: CompoundButton, isChecked: Boolean -> presenter.themeChipCheked(isChecked, "Theme.Moon")}
        mars_chip.setOnCheckedChangeListener {compoundButton: CompoundButton, isChecked: Boolean -> presenter.themeChipCheked(isChecked,"Theme.Mars")}
    }

    override fun saveTheme(themeName: String) {
        sharedPreferences.edit()?.putString("Theme", themeName)?.commit()
    }

    override fun updateTheme() {
        activity?.recreate()
    }

    override fun checkTheme() {
        currentTheme = sharedPreferences.getString("Theme", "Theme.Moon")
    }

    override fun backPressed(): Boolean = presenter.backClick()
}