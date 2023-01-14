package com.example.myapplication.models

class Teacher {
    var name: String
    var title: String
    var telegramID: String = ""
    var number: Long = 0
    var info: String
    var workDays: Array<Boolean>
    var room: Int

    constructor(
        name: String,
        title: String,
        telegramID: String,
        info: String,
        workDays: Array<Boolean>,
        room: Int
    ) {
        this.name = name
        this.title = title
        this.telegramID = telegramID
        this.info = info
        this.workDays = workDays
        this.room = room
    }

    constructor(name: String,
                title: String,
                number: Long,
                info: String,
                workDays: Array<Boolean>,
                room: Int) {
        this.name = name
        this.title = title
        this.number = number
        this.info = info
        this.workDays = workDays
        this.room = room
    }

    constructor(
        name: String,
        title: String,
        telegramID: String,
        number: Long,
        info: String,
        workDays: Array<Boolean>,
        room: Int
    ) {
        this.name = name
        this.title = title
        this.telegramID = telegramID
        this.number = number
        this.info = info
        this.workDays = workDays
        this.room = room
    }


}