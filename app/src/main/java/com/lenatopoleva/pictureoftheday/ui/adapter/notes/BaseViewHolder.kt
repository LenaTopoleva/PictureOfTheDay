package com.lenatopoleva.pictureoftheday.ui.adapter.notes

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(dataItem: Pair<Data, Boolean>)
}
