package com.example.myapplication.models

import java.net.URL

class Post {
    var text:String
    var date:String
    var imgUrl: MutableList<String>



    constructor(text: String, date: String, imgUrl: MutableList<String>) {
        this.text = text
        this.date = date
        this.imgUrl = imgUrl
    }



}