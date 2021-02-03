package com.lenatopoleva.pictureoftheday.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.lenatopoleva.pictureoftheday.R
import com.lenatopoleva.pictureoftheday.mvp.presenter.NotesPresenter
import com.lenatopoleva.pictureoftheday.mvp.view.NotesView
import com.lenatopoleva.pictureoftheday.ui.App
import com.lenatopoleva.pictureoftheday.ui.BackButtonListener
import com.lenatopoleva.pictureoftheday.ui.adapter.notes.Data
import com.lenatopoleva.pictureoftheday.ui.adapter.notes.ItemTouchHelperCallback
import com.lenatopoleva.pictureoftheday.ui.adapter.notes.NotesRVAdapter
import com.lenatopoleva.pictureoftheday.ui.adapter.notes.OnStartDragListener
import kotlinx.android.synthetic.main.fragment_notes.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.ArrayList
import javax.inject.Inject

class NotesFragment: MvpAppCompatFragment(), NotesView, BackButtonListener, DataTransmitter {

    companion object {
        const val DIALOG_FRAGMENT = 111
        fun newInstance() = NotesFragment()
    }

    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var adapter: NotesRVAdapter

    @Inject
    @InjectPresenter
    lateinit var presenter: NotesPresenter

    @ProvidePresenter
    fun provide() = presenter

    init {
        App.instance.appComponent.inject(this)
    }

    override fun init() {
        adapter = NotesRVAdapter(
            presenter,
            object : OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )
        recyclerView.adapter = adapter

        recyclerPlusFAB.setOnClickListener { presenter.plusFabClicked() }
        recyclerDiffUtilFAB.setOnClickListener { presenter.shuffleFabClicked() }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = View.inflate(context, R.layout.fragment_notes, null)

    override fun backPressed(): Boolean = presenter.backClick()

    override fun shuffleNotes(oldData: ArrayList<Pair<Data, Boolean>>, newData: ArrayList<Pair<Data, Boolean>>) {
        adapter.updateList(oldData, newData)
    }

    override fun notifyItemChanged(notePosition: Int) {
        adapter.notifyItemChanged(notePosition)
    }

    override fun notifyItemRemoved(layoutPosition: Int) {
        adapter.notifyItemRemoved(layoutPosition)
    }

    override fun notifyItemMoved(fromPosition: Int, toPosition: Int) {
        adapter.notifyItemMoved(fromPosition,toPosition)
    }

    override fun getData(notePosition: Int, title: String, body: String) {
        presenter.getData(notePosition, title, body)
    }

    override fun createNote(notePosition: Int, title: String, body: String) {
        openDialogFragment(notePosition, title, body)
    }

    override fun openDialogFragment(layoutPosition: Int, title: String, body: String?) {
        val dialogFragment = NoteDialogFragment.newInstance(layoutPosition, title, body)
        dialogFragment.setTargetFragment(this, DIALOG_FRAGMENT);
        fragmentManager?.let { dialogFragment.show(it, "NoteDialogFragment") }
    }

}

