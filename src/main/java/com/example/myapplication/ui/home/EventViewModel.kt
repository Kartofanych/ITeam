package com.example.myapplication.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EventViewModel : ViewModel() {


    private val db = Firebase.database("https://iteam-e45a8-default-rtdb.europe-west1.firebasedatabase.app")
    private val eventRef: DatabaseReference = db.getReference("events")


    private var _events = MutableLiveData<ArrayList<Event>>()
    private lateinit var events: ArrayList<Event>

    fun getEvents(): LiveData<ArrayList<Event>> {
        loadEvents()
        return _events
    }

    private fun loadEvents() {

        events = ArrayList<Event>()

        eventRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(event in snapshot.children){
                    events.add(event.getValue(Event::class.java)!!)
                }
                _events.postValue(events)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("1", error.toString())
            }
        })
    }

}