package com.lenatopoleva.pictureoftheday.mvp.presenter

import com.lenatopoleva.pictureoftheday.mvp.view.NoteDialogView
import moxy.MvpPresenter

class NoteDialogPresenter: MvpPresenter<NoteDialogView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun btnYesClicked() {
        viewState.sendData()
        viewState.closeDialog()
    }

    fun btnCancelClicked() {
        viewState.closeDialog()
    }

}