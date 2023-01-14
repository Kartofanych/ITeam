package com.example.myapplication.models

class Event {
    val imageUrl: String
    val title: String
    val info: String

    constructor(imageUrl: String, title: String, info: String) {
        this.imageUrl = imageUrl
        this.title = title
        this.info = info
    }
}