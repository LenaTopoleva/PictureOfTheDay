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
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var sharedPreferences: SharedPreferences

    val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.container){
        override fun applyCommands(commands: Array<out Command>) {
            presenter.checkCurrentBottomMenuItem(getCurrentScreenName(commands))
            super.applyCommands(commands)
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun provide() = presenter

    companion object {
        const val THEME = "Theme"
        const val THEME_MOON = "Theme.Moon"
        const val THEME_MARS = "Theme.Mars"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_photo_of_the_day -> presenter.photoOfTheDayMenuItemClicked().let { true }
                R.id.bottom_view_earth -> presenter.earthMenuItemClicked().let { true }
                R.id.bottom_view_notes -> presenter.notesMenuItemClicked().let { true }
                R.id.bottom_view_wiki -> presenter.wikiMenuItemClicked().let { true }
                R.id.bottom_view_settings -> presenter.settingsMenuItemClicked().let { true }
                else -> false
            }
        }
    }

    override fun getTheme(): Resources.Theme {
        val theme: Resources.Theme = super.getTheme()
        when(findSavedTheme()) {
            THEME_MOON -> theme.applyStyle(R.style.Theme_Moon, true)
            THEME_MARS -> theme.applyStyle(R.style.Theme_Mars, true)
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

    fun findSavedTheme() = sharedPreferences.getString(THEME, THEME_MOON) ?: ""

    fun getCurrentScreenName(commands: Array<out Command>): String {
        var currentScreenName = ""
        when (val lastCommand: Command = commands[commands.size - 1]){
            is Replace -> currentScreenName = lastCommand.screen.screenKey
            is Forward -> currentScreenName = lastCommand.screen.screenKey
            is Back -> currentScreenName = getPreviousFragmentName() ?: ""
        }
        return currentScreenName
    }

    private fun getPreviousFragmentName(): String? {
        return when (supportFragmentManager.backStackEntryCount){
            0 -> null
            1 -> presenter.primaryScreen.toString()
            else -> supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 2).name
        }
    }

    override fun setPODMenuItemChecked() { bottom_navigation_view.menu.findItem(R.id.bottom_photo_of_the_day).isChecked = true }

    override fun setWikiMenuItemChecked() { bottom_navigation_view.menu.findItem(R.id.bottom_view_wiki).isChecked = true }

    override fun setSettingsMenuItemChecked() { bottom_navigation_view.menu.findItem(R.id.bottom_view_settings).isChecked = true }

    override fun setEarthMenuItemChecked() { bottom_navigation_view.menu.findItem(R.id.bottom_view_earth).isChecked = true }

    override fun setNotesMenuItemChecked() { bottom_navigation_view.menu.findItem(R.id.bottom_view_notes).isChecked = true }

}