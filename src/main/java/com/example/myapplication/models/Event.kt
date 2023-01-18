package com.example.myapplication.models

import java.util.Date

class Event {
    val imageUrl: String
    val title: String
    val info: String
    val date: DateClass

    constructor(imageUrl: String, title: String, info: String, date: DateClass) {
        this.imageUrl = imageUrl
        this.title = title
        this.info = info
        this.date = date
    }
}