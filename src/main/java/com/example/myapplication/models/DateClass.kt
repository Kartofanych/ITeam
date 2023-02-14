package com.example.myapplication.models

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit

class DateClass(var day: Int = 0,
                var month:Int = 0,
                var hours: Int = 0,
                var minutes: Int = 0,
                var year: Int = 2023) {
    override fun toString(): String {
    return hours(hours) + ":" + minutes(minutes) + ", " + day + " " + getMonthName(month).substring(0, 3)
    }

    private fun getMonthName(num:Int) : String{
        when(num){
            0-> return "Январь"
            1-> return "Февраль"
            2-> return "Март"
            3-> return "Апрель"
            4-> return "Май"
            5-> return "Июнь"
            6-> return "Июль"
            7-> return "Август"
            8-> return "Сентябрь"
            9-> return "Октябрь"
            10-> return "Ноябрь"
            11-> return "Декабрь"
        }
        return "Январь"
    }

    private fun hours(h:Int):String{
        return if(h<10){
            "0$h"
        }else{
            "$h"
        }
    }
    private fun minutes(m:Int):String{
        return if(m<10){
            "0$m"
        }else{
            "$m"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun timeFromNow():String{

        val now = Calendar.getInstance()

        val current = Calendar.getInstance()
        current.set(Calendar.YEAR, year)
        current.set(Calendar.MONTH, month)
        current.set(Calendar.DAY_OF_MONTH, day)
        current.set(Calendar.HOUR_OF_DAY, hours)
        current.set(Calendar.MINUTE, minutes)


        //val month = ChronoUnit.MONTHS.between(current.toInstant(), now.toInstant()).toInt()

        val hours = ChronoUnit.HOURS.between(current.toInstant(), now.toInstant()).toInt()

        val days = ChronoUnit.DAYS.between(current.toInstant(), now.toInstant()).toInt()

        val minutes = ChronoUnit.MINUTES.between(current.toInstant(), now.toInstant()).toInt()

        Log.d("1", "$minutes $hours $days")
        Log.d("1", now.time.toString() + " "+ current.time.toString())


        if(days>32){
            return "больше месяца назад"
        }else if(days>0){
            return when (days) {
                1 -> {
                    "$days день назад"
                }
                in 2..4 -> {
                    "$days дня назад"
                }
                21->{
                    "$days день назад"

                }
                in 22..24->{
                    "$days дня назад"
                }
                31->{
                    "$days день назад"

                }
                else -> {
                    "$days дней назад"
                }
            }
        }else if(hours > 0){
            return when (hours) {
                1 -> {
                    "$hours час назад"
                }
                21->{
                    "$hours час назад"
                }
                in 2..4 -> {
                    "$hours часа назад"
                }
                in 22 until 24 -> {
                    "$hours часа назад"
                }
                else -> {
                    "$hours часов назад"
                }
            }
        }else if(minutes >= 5){
            return when (minutes) {
                21->{
                    "$minutes минуту назад"
                }
                31->{
                    "$minutes минуту назад"
                }
                41->{
                    "$minutes минуту назад"
                }
                51->{
                    "$minutes минуту назад"
                }
                in 22..24 -> {
                    "$minutes минуты назад"
                }
                in 32..34 -> {
                    "$minutes минуты назад"
                }
                in 42..44 -> {
                    "$minutes минуты назад"
                }
                in 52..54 -> {
                    "$minutes минуты назад"
                }
                else -> {
                    "$minutes минут назад"
                }
            }
        }else{
            return "только что"
        }


    }
}