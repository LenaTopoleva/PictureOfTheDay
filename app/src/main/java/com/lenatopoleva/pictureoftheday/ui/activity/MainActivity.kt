package com.lenatopoleva.pictureoftheday.ui.activity

import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.lenatopoleva.pictureoftheday.R
import com.lenatopoleva.pictureoftheday.mvp.presenter.MainPresenter
import com.lenatopoleva.pictureoftheday.mvp.view.MainView
import com.lenatopoleva.pictureoftheday.ui.App
import com.lenatopoleva.pictureoftheday.ui.BackButtonListener
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var sharedPreferences: SharedPreferences

    val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.container){}

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun provide() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun getTheme(): Resources.Theme {
        val theme: Resources.Theme = super.getTheme()
        when(findSavedTheme()) {
            "Theme.Moon" -> theme.applyStyle(R.style.Theme_Moon, true)
            "Theme.Mars" -> theme.applyStyle(R.style.Theme_Mars, true)
        }
        return theme
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MenuInflater(this).inflate(
        R.menu.main,
        menu
    ).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.wiki_menu_item -> presenter.wikiMenuItemClicked().let { true }
            R.id.settings_menu_item -> presenter.settingsMenuItemClicked().let { true }
            else -> false
        }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClick()
    }

    fun findSavedTheme() = sharedPreferences.getString("Theme", "Theme.Moon") ?: ""

}