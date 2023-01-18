package com.example.myapplication.models

import java.time.Month

class DateClass(var day: Int, var month:String, var time:String) {
    override fun toString():String{
        return time + " " + day + ", " + month.substring(0, 3)
    }
}