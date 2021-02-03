package com.lenatopoleva.pictureoftheday.mvp.presenter

import com.lenatopoleva.pictureoftheday.mvp.view.NotesView
import com.lenatopoleva.pictureoftheday.ui.adapter.notes.Data
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import java.text.DateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class NotesPresenter @Inject constructor(val router: Router): MvpPresenter<NotesView>() {

    lateinit var data: ArrayList<Pair<Data, Boolean>>
    val header = Pair(Data(generateId(), "Header", "", getCurrentTime()), false)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
        viewState.init()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    fun loadData(){
        data = arrayListOf(Pair(Data(generateId(), "Купить скафандр", "Очень важно изолироваться от внешней среды!", getCurrentTime()), false))
        data.add(0, header )
    }

    fun getNotes(): ArrayList<Pair<Data, Boolean>> = data

    fun getCurrentTime() = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(
        Date().time)

    private fun generateId() = UUID.randomUUID().toString()

    fun plusFabClicked() {
        viewState.createNote(data.size, "", "")
    }

    fun shuffleFabClicked() {
        val oldData: ArrayList<Pair<Data, Boolean>> = ArrayList()
        oldData.addAll(data)
        data.removeAt(0)
        data.shuffle()
        data.add(0, header )
        viewState.shuffleNotes(oldData, data)
    }

    fun getItemCount() = data.size

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        viewState.notifyItemMoved(fromPosition, toPosition)

    }

    fun onItemRemoved(position: Int) {
        data.removeAt(position)
        viewState.notifyItemRemoved(position)
    }

    // Добавляем новю заметку в конец списка
    fun createItemIfNecessary(notePosition: Int){
        if (notePosition == data.size) data.add(generateItem())
    }

    private fun generateItem() = Pair(Data(generateId(), "", "", ""), false)

    fun editItem(notePosition: Int, title: String, body: String) {
        data[notePosition].first.someText = title
        data[notePosition].first.someDescription = body
        data[notePosition].first.creationDate = getCurrentTime()
        viewState.notifyItemChanged(notePosition)
    }

    fun removeItem(layoutPosition: Int) {
        data.removeAt(layoutPosition)
        viewState.notifyItemRemoved(layoutPosition)
    }

    fun toggleText(layoutPosition: Int) {
        data[layoutPosition] = data[layoutPosition].let {
            it.first to !it.second
        }
        viewState.notifyItemChanged(layoutPosition)
    }

    fun openDialogFragment(layoutPosition: Int, title: String, body: String?) {
        viewState.openDialogFragment(layoutPosition, title, body)
    }

    fun getData(notePosition: Int, title: String, body: String) {
        createItemIfNecessary(notePosition)
        editItem(notePosition, title, body)
    }

}