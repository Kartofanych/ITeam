package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.models.Event

class ViewPagerFutureEventsAdapter(var eventsList: ArrayList<Event>, var context: Context):RecyclerView.Adapter<ViewPagerFutureEventsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.event_image)
        val name: TextView = itemView.findViewById(R.id.event_name)
        val info: TextView = itemView.findViewById(R.id.event_info)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.element_carousel_future_events, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventsList[position]
        holder.name.text = event.title
        holder.info.text = event.date.toString()
        Glide.with(context)
            .load(event.imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }
}