package com.example.myapplication.adapters

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myapplication.EventProfileActivity
import com.example.myapplication.R
import com.example.myapplication.models.Event
import org.w3c.dom.Text

class EventSliderAdapter(private val eventList: ArrayList<Event>, private val viewPager2: ViewPager2)
    :RecyclerView.Adapter<EventSliderAdapter.EventSliderViewHolder>(){

    class EventSliderViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.event_image)
        val title: TextView = itemView.findViewById(R.id.event_title)
        val info: TextView = itemView.findViewById(R.id.event_info)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventSliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_event_card, parent, false)
        return EventSliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventSliderViewHolder, position: Int) {
        val event = eventList[position]
        holder.title.text = event.title
        holder.info.text = event.info

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intentToEventProfile = Intent(viewPager2.context, EventProfileActivity::class.java)
            intentToEventProfile.putExtra("event_name", event.title)
            intentToEventProfile.putExtra("event_info", event.info)
            intentToEventProfile.putExtra("event_imageUrl", event.imageUrl)

            viewPager2.context.startActivity(intentToEventProfile)
        })

        Glide.with(viewPager2.context)
            .load(event.imageUrl)
            .into(holder.imageView)
        if(position == eventList.size-1){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
       return eventList.size
    }

    private val runnable = Runnable{
        eventList.addAll(eventList)
        notifyDataSetChanged()
    }
}