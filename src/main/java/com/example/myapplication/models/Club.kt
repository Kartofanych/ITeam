package com.example.myapplication.models

class Club {
    var name:String
    var curator: Teacher
    var type:String
    var room:Int
    var searchString:String


    constructor(name: String, curator: Teacher, type: String, room: Int) {
        this.name = name
        this.curator = curator
        this.type = type
        this.searchString = name + curator + type + room + curator.name
        this.room = room
    }
}