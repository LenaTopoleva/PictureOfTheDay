package com.lenatopoleva.pictureoftheday.mvp.view

import com.lenatopoleva.pictureoftheday.ui.adapter.notes.Data
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip
import java.util.ArrayList

@AddToEndSingle
interface NotesView : MvpView{
    fun init()

    @Skip
    fun createNote(notePosition: Int, title: String, body: String)

    fun shuffleNotes(oldData: ArrayList<Pair<Data, Boolean>>, newData: ArrayList<Pair<Data, Boolean>>)

    fun notifyItemChanged(notePosition: Int)
    fun notifyItemRemoved(layoutPosition: Int)
    fun notifyItemMoved(fromPosition: Int, toPosition: Int)

    @Skip
    fun openDialogFragment(layoutPosition: Int, title: String, body: String?)
}