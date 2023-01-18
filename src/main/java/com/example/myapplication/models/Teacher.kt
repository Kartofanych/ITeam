package com.example.myapplication.models

class Teacher {
    var name: String
    var title: String
    var telegramID: String = ""
    var number: Long = 0
    var info: String
    var workDays: Array<Boolean>
    var room: Int
    var searchString: String
    var lesson: String
    var imageUrl: String


    constructor(
        name: String,
        title: String,
        telegramID: String,
        number: Long,
        info: String,
        workDays: Array<Boolean>,
        room: Int,
        lesson: String,
        imageUrl: String
    ) {
        this.name = name
        this.title = title
        this.telegramID = telegramID
        this.number = number
        this.info = info
        this.workDays = workDays
        this.room = room
        this.lesson = lesson
        this.searchString = name+lesson+room
        this.imageUrl = imageUrl
    }



}