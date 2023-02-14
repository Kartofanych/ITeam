package com.example.myapplication.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.EventProfileActivity
import com.example.myapplication.R
import com.example.myapplication.models.Event
import com.example.myapplication.models.Post
import com.google.gson.Gson
import me.relex.circleindicator.CircleIndicator

class RecyclerEventListAdapter(eventList: ArrayList<Event>, var context: Context)
    : RecyclerView.Adapter<RecyclerEventListAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.event_image)
        val title: TextView = itemView.findViewById(R.id.event_title)
        val date: TextView = itemView.findViewById(R.id.event_date)
        val peopleNumber: TextView = itemView.findViewById(R.id.event_people)
    }

    var arrayNewsList = eventList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.element_event_for_recycler, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event : Event = arrayNewsList[position]
        holder.title.text = event.title

        Glide.with(context)
            .load(event.imageUrl)
            .into(holder.imageView)

        holder.date.text = event.date.toString()

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intentToEventProfile = Intent(context, EventProfileActivity::class.java)

            val gson = Gson()
            val jsonString = gson.toJson(event)
            intentToEventProfile.putExtra("event", jsonString)

            val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                context as Activity?,
                Pair.create(holder.itemView, "event_transition")
            )
            context.startActivity(intentToEventProfile, options.toBundle())
        })

    }

    override fun getItemCount(): Int {
        return arrayNewsList.size
    }



}