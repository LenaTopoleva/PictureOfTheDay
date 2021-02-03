package com.lenatopoleva.pictureoftheday.ui.adapter.notes

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lenatopoleva.pictureoftheday.R
import com.lenatopoleva.pictureoftheday.mvp.presenter.NotesPresenter
import kotlinx.android.synthetic.main.fragment_notes_recycler_item.view.*
import java.util.ArrayList


class NotesRVAdapter (
    val presenter: NotesPresenter,
    private val dragListener: OnStartDragListener,
)
    : RecyclerView.Adapter<BaseViewHolder>(),  ItemTouchHelperAdapter {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_NOTE = 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_NOTE -> NoteViewHolder(
                inflater.inflate(R.layout.fragment_notes_recycler_item, parent, false) as View
            )
            else -> HeaderViewHolder(
                inflater.inflate(R.layout.fragment_notes_recycler_header, parent, false) as View
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(presenter.getNotes()[position])
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            payloads.find { it is Bundle }?.let {
                val bundle = it as Bundle
                for (key: String in bundle.keySet()) {
                    when (key) {
                        "title" -> holder.itemView.noteTextView.text = bundle.getString(key)
                        "body" -> holder.itemView.noteDescriptionTextView.text = bundle.getString(key)
                        "date" -> holder.itemView.timeCreatedTextView.text = bundle.getString(key)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return presenter.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            else -> TYPE_NOTE
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        presenter.onItemMove(fromPosition, toPosition)
    }

    override fun onItemRemoved(position: Int) {
        presenter.onItemRemoved(position)
    }

    fun updateList(oldItems: List<Pair<Data, Boolean>>, newData: ArrayList<Pair<Data, Boolean>>) {
        val result = DiffUtil.calculateDiff(DiffUtilCallback(oldItems, newData))
        result.dispatchUpdatesTo(this)
    }

    private fun openDialog(layoutPosition: Int, title: String, body: String?) {
        presenter.openDialogFragment(layoutPosition, title, body)
    }

    inner class NoteViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {

        @SuppressLint("ClickableViewAccessibility")
        override fun bind(dataItem: Pair<Data, Boolean>) {
            itemView.noteTextView.text = dataItem.first.someText
            itemView.noteDescriptionTextView.text = dataItem.first.someDescription
            itemView.timeCreatedTextView.text = dataItem.first.creationDate

            itemView.removeItemImageView.setOnClickListener { presenter.removeItem(layoutPosition) }
            itemView.editImageView.setOnClickListener { openDialog(layoutPosition, dataItem.first.someText, dataItem.first.someDescription)}
            itemView.noteDescriptionGroup.visibility =
                if (dataItem.second) View.VISIBLE else View.GONE
            itemView.noteTextView.setOnClickListener { presenter.toggleText(layoutPosition) }
            itemView.dragHandleImageView.setOnTouchListener { _, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(dataItem: Pair<Data, Boolean>) {}
    }

}