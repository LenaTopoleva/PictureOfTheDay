package com.lenatopoleva.pictureoftheday.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface NoteDialogView: MvpView {
    fun init()
    fun closeDialog()
    fun sendData()
}