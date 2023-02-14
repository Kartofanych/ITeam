package com.example.myapplication.ui.notifications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.Event
import com.example.myapplication.models.News
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NotificationsViewModel : ViewModel() {

    private val db = Firebase.database("https://iteam-e45a8-default-rtdb.europe-west1.firebasedatabase.app")
    private val eventRef: DatabaseReference = db.getReference("news")


    private var _news = MutableLiveData<ArrayList<News>>()
    private lateinit var news: ArrayList<News>

    fun getNews(): LiveData<ArrayList<News>> {
        loadNews()
        return _news
    }

    private fun loadNews() {

        news = ArrayList<News>()

        eventRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(new in snapshot.children){
                    news.add(new.getValue(News::class.java)!!)
                }
                _news.postValue(news)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("1", error.toString())
            }
        })
    }
}