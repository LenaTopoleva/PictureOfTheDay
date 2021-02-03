package com.lenatopoleva.pictureoftheday.ui.adapter.notes


data class Data(
    val id: String,
    var someText: String = "Text",
    var someDescription: String? = "Description",
    var creationDate: String,
)