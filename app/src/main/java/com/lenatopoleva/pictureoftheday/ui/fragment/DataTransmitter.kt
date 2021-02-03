package com.lenatopoleva.pictureoftheday.ui.fragment

interface DataTransmitter {
    fun getData(notePosition: Int, title: String, body: String)
}