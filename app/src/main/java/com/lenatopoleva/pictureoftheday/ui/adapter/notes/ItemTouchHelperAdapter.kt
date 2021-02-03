package com.lenatopoleva.pictureoftheday.ui.adapter.notes

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemRemoved(position: Int)
}