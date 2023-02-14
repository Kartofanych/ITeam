package com.example.myapplication.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.models.News

class NewsSliderAdapter(private val newsList: ArrayList<News>, private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<NewsSliderAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsImage: ImageView = itemView.findViewById(R.id.news_preview_image)
        val dateText:TextView = itemView.findViewById(R.id.time_ago)
        val newsName:TextView = itemView.findViewById(R.id.news_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.element_news_preview, parent, false)
        return ImageViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.dateText.text = newsList[position].date.timeFromNow()
        holder.newsName.text = newsList[position].name
        Glide.with(viewPager2.context)
            .load(newsList[position].imageUrl)
            .into(holder.newsImage)

    }

    override fun getItemCount(): Int {
        return newsList.size
    }


}